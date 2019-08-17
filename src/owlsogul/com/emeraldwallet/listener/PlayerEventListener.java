package owlsogul.com.emeraldwallet.listener;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import owlsogul.com.emeraldwallet.EmeraldWallet;
import owlsogul.com.emeraldwallet.EmeraldWalletControllerInterface;
import owlsogul.com.emeraldwallet.EmeraldWalletPlugin;
import owlsogul.com.emeraldwallet.event.EmeraldWalletOpenEvent;
import owlsogul.com.emeraldwallet.option.EmeraldWalletOption;
import owlsogul.com.emeraldwallet.util.Guard;

public class PlayerEventListener implements Listener {
	
	private EmeraldWalletControllerInterface walletController;
	private EmeraldWalletPlugin plugin;
	public PlayerEventListener(EmeraldWalletControllerInterface walletController) {
		this.walletController = walletController;
		this.plugin = EmeraldWalletPlugin.plugin;
	}

	@EventHandler
	public void onPlayerRightClickWallet(InventoryClickEvent event) {
		
		if (Guard.guard(event.getWhoClicked() instanceof Player)) return;
		if (Guard.guard(event.getClick() == ClickType.RIGHT)) return;
		
		ItemStack clickedItem = event.getCurrentItem();
		if (Guard.guard(clickedItem.getItemMeta() != null)) return;
		String itemName = clickedItem.getItemMeta().getDisplayName();
		List<String> lore = clickedItem.getItemMeta().getLore();
		
		EmeraldWallet wallet = walletController.parseEmeraldWallet(itemName, clickedItem.getType().toString(), lore);
		if (Guard.guard(wallet != null)) return;
		EmeraldWalletOption option = EmeraldWalletOption.getInstance();
		Player player = (Player) event.getWhoClicked();
		
		EmeraldWalletOpenEvent emeraldWalletOpenEvent = new EmeraldWalletOpenEvent(player, wallet);
		Bukkit.getPluginManager().callEvent(emeraldWalletOpenEvent);
		if (!emeraldWalletOpenEvent.isCancelled()) {
			Inventory walletInv = Bukkit.createInventory(player, wallet.getSize(), option.walletTitle + "#"+wallet.getTag());
			for (int i = 0; i < wallet.getSize(); i++) {
				int amount = wallet.getContainer()[i];
				if (amount > 0) walletInv.setItem(i, new ItemStack(Material.EMERALD, amount));
				else walletInv.setItem(i, new ItemStack(Material.AIR));
			}
			player.closeInventory();
			player.openInventory(walletInv);
			System.out.println("wallet inventory open. " + wallet.toString());
		}
	}
	
	@EventHandler
	public void onPlayerCloseWallet(InventoryCloseEvent event) {
		
		EmeraldWalletOption option = EmeraldWalletOption.getInstance();
		/* wallet inv check */ if (Guard.guard(event.getView().getTitle().startsWith(option.walletTitle+"#"))) return;
		
		Inventory walletInv = event.getInventory();
		String tag = event.getView().getTitle().split("#")[1];
		EmeraldWallet wallet = walletController.getEmeraldWallet(tag);
		/* wallet tag existence check */ if (Guard.guard(wallet != null)) return;
		
		int[] container = new int[walletInv.getSize()];
		for (int i = 0; i < container.length; i++) {
			ItemStack item = walletInv.getItem(i);
			if (item == null || item.getAmount() == 0 || !item.getType().equals(Material.EMERALD)) continue;
			if (item.getType().equals(Material.EMERALD)) {
				container[i] = item.getAmount();
			}
		}
		wallet.setContainer(container);
		
	}
	
	@EventHandler
	public void onPlayerPrepareCraftWallet(PrepareItemCraftEvent event) {
		
		if (Guard.guard(event.getRecipe() instanceof ShapedRecipe)) return;
		ShapedRecipe recipe = (ShapedRecipe) event.getRecipe();
		String namespace = this.plugin.getName().toLowerCase(Locale.ROOT);
		if (Guard.guard(namespace.equals(recipe.getKey().getNamespace()))) return;
		CraftingInventory craftingInv = event.getInventory();
		ItemStack[] ingredients = craftingInv.getMatrix();
		for (ItemStack item : ingredients) {
			if (EmeraldWalletOption.contain(item.getType().toString()) >= 0) {
				EmeraldWallet wallet = walletController.parseEmeraldWallet(item.getItemMeta().getDisplayName(), item.getType().toString(), item.getItemMeta().getLore());
				if (wallet == null) {craftingInv.setResult(null); return;}
			}
		}
	}
	
	@EventHandler
	public void onPlayerCraftWallet(CraftItemEvent event) {
		if (Guard.guard(event.getRecipe() instanceof ShapedRecipe)) return;
		ShapedRecipe recipe = (ShapedRecipe) event.getRecipe();
		String namespace = this.plugin.getName().toLowerCase(Locale.ROOT);
		if (Guard.guard(namespace.equals(recipe.getKey().getNamespace()))) return;
		CraftingInventory craftingInv = event.getInventory();
		ItemStack[] ingredients = craftingInv.getMatrix();
		LinkedList<String> deletedWallets = new LinkedList<>();
		for (ItemStack item : ingredients) {
			if (EmeraldWalletOption.contain(item.getType().toString()) >= 0) {
				EmeraldWallet wallet = walletController.parseEmeraldWallet(item.getItemMeta().getDisplayName(), item.getType().toString(), item.getItemMeta().getLore());
				if (wallet == null) { event.setCancelled(true);}
				else {
					deletedWallets.add(wallet.getTag());
				}
			}
		}
		if (!event.isCancelled()) {
			for (String tag : deletedWallets) walletController.removeEmeraldWallet(tag);
			ItemStack result = craftingInv.getResult();
			EmeraldWallet resultWallet = walletController.addNewEmeraldWallet(result.getType().toString());
			ItemMeta resultMeta = result.getItemMeta();
			LinkedList<String> lore = new LinkedList<>();
			lore.add("#"+resultWallet.getTag());
			lore.add(resultMeta.getLore().get(1));
			resultMeta.setLore(lore);
			result.setItemMeta(resultMeta);
		}
	}
	
	@EventHandler
	public void onPlayerPlaceWallet(BlockPlaceEvent event) {
		ItemStack item = event.getItemInHand();
		if (Guard.guard(item != null && EmeraldWalletOption.contain(item.getType().toString()) >= 0)) return;
		if (Guard.guard(item.getItemMeta() != null)) return;
		ItemMeta meta = item.getItemMeta();
		EmeraldWallet wallet = walletController.parseEmeraldWallet(meta.getDisplayName(), item.getType().toString(), meta.getLore());
		if (Guard.guard(wallet != null)) return;
		event.setCancelled(true);
		event.getPlayer().sendMessage("do not place emerald wallet! you are wasting resources!");
	}
	
}
