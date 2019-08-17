package owlsogul.com.emeraldwallet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import owlsogul.com.emeraldwallet.option.EmeraldWalletOption;
import owlsogul.com.emeraldwallet.util.Guard;
import owlsogul.com.emeraldwallet.util.MingyuDecoder;
import owlsogul.com.emeraldwallet.util.MingyuEncoder;
import owlsogul.com.emeraldwallet.util.MingyuFormatException;

public class EmeraldWalletController implements EmeraldWalletControllerInterface{

	private HashMap<String, EmeraldWallet> wallets;
	public EmeraldWalletController() {
		wallets = new HashMap<>();
	}
	public HashMap<String, EmeraldWallet> getWallets() {return this.wallets;}
	
	@Override
	public EmeraldWallet addNewEmeraldWallet(String itemType) {
		EmeraldWalletOption option = EmeraldWalletOption.getInstance();
		int level = contain(option.walletItemType, itemType);
		if (Guard.guard(level >= 0)) return null;
		String key = MingyuEncoder.encodeMingyu(option.walletKey, System.currentTimeMillis());
		EmeraldWallet wallet = EmeraldWallet.createEmeraldWallet(key, "Emerald Wallet Lv"+level, 9*level);
		wallets.put(key, wallet);
		System.out.println("wallet create. " + wallet.toString());
		return wallet;
	}
	
	@Override
	public EmeraldWallet getEmeraldWallet(String key) {
		return wallets.get(key);
	}
	
	@Override
	public EmeraldWallet parseEmeraldWallet(String itemName, String itemType, List<String> lore){
		EmeraldWalletOption option = EmeraldWalletOption.getInstance();
		/* item type check */ if (Guard.guard(contain(option.walletItemType, itemType) >= 0)) return null;
		/* item name check */ if (Guard.guard(option.walletItemName.equals(itemName))) return null;
		/* lore check */ 
		String tagRow = lore.get(option.walletTagRow);
		/* wallet tag prefix check */ if (Guard.guard(tagRow.startsWith(option.walletPrefix))) return null;
		String tagStr = tagRow.replaceAll("#", "");
		/* wallet tag decode test */
		try {
			Date date = new Date(MingyuDecoder.decodeMingyu(option.walletKey, tagStr));
			SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd hh:mm:ss");
			System.out.println(sdf.format(date) + "에 만들어진 지갑을 파싱하였습니다");
		} catch (MingyuFormatException e) {
			return null;
		}
		return wallets.get(tagStr);
	}
	
	private int contain(String[] arr, String str) {
		for (int i = 0; i < arr.length; i++) if (arr[i].equals(str)) return i;
		return -1;
	}
	
	@Override
	public void save(File saveFile) {
		try {
			Gson gson = new Gson();
			FileWriter fw = new FileWriter(saveFile, Charset.forName("UTF-8"));
			BufferedWriter bw = new BufferedWriter(fw);
			JsonWriter writer = new JsonWriter(bw);
			gson.toJson(gson.toJsonTree(this.wallets), writer);
			writer.close();
			bw.close();
			fw.close();
			System.out.println(wallets.keySet().size() + " wallet saves");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void load(File saveFile) {
		Gson gson = new Gson();
		try {
			FileReader fr = new FileReader(saveFile, Charset.forName("UTF-8"));
			BufferedReader br = new BufferedReader(fr);
			JsonReader reader = new JsonReader(br);
			@SuppressWarnings("serial")
			Type type = new TypeToken<HashMap<String, EmeraldWallet>>() {}.getType();
			wallets = gson.fromJson(reader, type);
			reader.close();
			br.close();
			fr.close();
			System.out.println(wallets.keySet().size() + " wallet loads");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean removeEmeraldWallet(String tag) {
		EmeraldWallet wallet = wallets.remove(tag);
		System.out.println("wallet remove. " + wallet);
		return wallet != null;
	}
	
	
	
}
