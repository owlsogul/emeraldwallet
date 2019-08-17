package test;

import java.lang.reflect.Type;
import java.util.HashMap;

import org.bukkit.Material;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import owlsogul.com.emeraldwallet.EmeraldWallet;
import owlsogul.com.emeraldwallet.EmeraldWalletController;

public class TestGson {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EmeraldWalletController walletController = new EmeraldWalletController();
		walletController.addNewEmeraldWallet(Material.RED_WOOL.toString());
		walletController.addNewEmeraldWallet(Material.RED_WOOL.toString());
		walletController.addNewEmeraldWallet(Material.RED_WOOL.toString());
		walletController.getWallets();
		
		Gson gson = new Gson();
		gson.toJson(gson.toJsonTree(walletController.getWallets()), System.out);
		
		System.out.println("go");
		// {"맠겠뎄뎃덳삮삼삹맛겘뎅삯뇬":{"tag":"맠겠뎄뎃덳삮삼삹맛겘뎅삯뇬","desc":"Emerald Wallet Lv0","size":9,"container":[0,0,0,0,0,0,0,0,0]},"뇶겠겖상삺뇰겐겘뇯덼뎅뇲델":{"tag":"뇶겠겖상삺뇰겐겘뇯덼뎅뇲델","desc":"Emerald Wallet Lv0","size":9,"container":[0,0,0,0,0,0,0,0,0]},"뎉겔삷겔뎇뇰뎉망덺뎆삮맜삳":{"tag":"뎉겔삷겔뎇뇰뎉망덺뎆삮맜삳","desc":"Emerald Wallet Lv0","size":9,"container":[0,0,0,0,0,0,0,0,0]}}
		@SuppressWarnings("serial")
		Type type = new TypeToken<HashMap<String, EmeraldWallet>>() {}.getType();
		HashMap<String, EmeraldWallet> wallets  = gson.fromJson("{\"맠겠뎄뎃덳삮삼삹맛겘뎅삯뇬\":{\"tag\":\"맠겠뎄뎃덳삮삼삹맛겘뎅삯뇬\",\"desc\":\"Emerald Wallet Lv0\",\"size\":9,\"container\":[0,0,0,0,0,0,0,0,0]},\"뇶겠겖상삺뇰겐겘뇯덼뎅뇲델\":{\"tag\":\"뇶겠겖상삺뇰겐겘뇯덼뎅뇲델\",\"desc\":\"Emerald Wallet Lv0\",\"size\":9,\"container\":[0,0,0,0,0,0,0,0,0]},\"뎉겔삷겔뎇뇰뎉망덺뎆삮맜삳\":{\"tag\":\"뎉겔삷겔뎇뇰뎉망덺뎆삮맜삳\",\"desc\":\"Emerald Wallet Lv0\",\"size\":9,\"container\":[0,0,0,0,0,0,0,0,0]}}", type);
		gson.toJson(gson.toJsonTree(wallets), System.out);
	}

}
