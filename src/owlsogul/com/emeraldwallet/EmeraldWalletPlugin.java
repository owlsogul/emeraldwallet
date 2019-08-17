package owlsogul.com.emeraldwallet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import owlsogul.com.emeraldwallet.listener.PlayerEventListener;
import owlsogul.com.emeraldwallet.option.EmeraldWalletOption;

public class EmeraldWalletPlugin extends JavaPlugin{
	
	public static EmeraldWalletPlugin plugin;
	public EmeraldWalletController walletController;
	public PlayerEventListener playerListener;
	
	@Override
	public void onEnable() {
		plugin = this;

		walletController =  new EmeraldWalletController();
		registerRecipe();
		
		File dataFolder = this.getDataFolder();
		if (!dataFolder.exists()) {
			System.out.println("create data folder");
			dataFolder.mkdirs();
		}
		
		File saveFile = new File(dataFolder, "wallets.json");
		if (!saveFile.exists()) {
			try {
				saveFile.createNewFile();
				FileWriter fw = new FileWriter(saveFile, Charset.forName("UTF-8"));
				fw.append("{}\n").flush();
				fw.close();
				System.out.println("create data file");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		walletController.load(saveFile);
		System.out.println("save file load");

		playerListener = new PlayerEventListener(walletController);
		this.getServer().getPluginManager().registerEvents(playerListener, this);
		super.onEnable();
	}
	
	@Override
	public void onDisable() {

		File dataFolder = this.getDataFolder();
		if (!dataFolder.exists()) {
			System.out.println("create data folder");
			dataFolder.mkdirs();
		}
		
		File saveFile = new File(dataFolder, "wallets.json");
		if (!saveFile.exists()) {
			try {
				saveFile.createNewFile();
				System.out.println("create data file");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		walletController.save(saveFile);
		
		super.onDisable();
	}
	
	private void registerRecipe() { 
		
		EmeraldWalletOption option = EmeraldWalletOption.getInstance();
		ItemStack[] results = new ItemStack[option.walletItemType.length];
		ShapedRecipe[] recipes = new ShapedRecipe[results.length];
		
		for (int i = 0; i < results.length; i++) {
			// setting item effect
			ItemStack item = new ItemStack(Material.valueOf(option.walletItemType[i]));
			item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, i + 1);
			// setting item display
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(option.walletItemName);
			List<String> lore = new ArrayList<>();
			lore.add("#WalletTag");
			lore.add("LV " + i);
			meta.setLore(lore);
			item.setItemMeta(meta);
			// add result
			results[i] = item;
			// recipe
			ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "RecipeLv"+ i), results[i]);
			if (i == 0) {
				recipe.shape("LLL", "LEL", "LLL")
					.setIngredient('L', Material.LEATHER)
					.setIngredient('E', Material.EMERALD);
			}
			else {
				recipe.shape("LLL", "WEW", "LLL")
					.setIngredient('L', Material.LEATHER)
					.setIngredient('W', results[i-1].getType())
					.setIngredient('E', Material.EMERALD);
			}
			recipes[i] = recipe;
			this.getServer().addRecipe(recipe);
		}
	}
	
}
