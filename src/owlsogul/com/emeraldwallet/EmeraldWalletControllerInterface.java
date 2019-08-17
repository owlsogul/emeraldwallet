package owlsogul.com.emeraldwallet;

import java.io.File;
import java.util.List;

public interface EmeraldWalletControllerInterface {

	public EmeraldWallet addNewEmeraldWallet(String itemType);
	public boolean removeEmeraldWallet(String tag);
	public EmeraldWallet getEmeraldWallet(String key);
	public EmeraldWallet parseEmeraldWallet(String itemName, String itemType, List<String> lore);
	public void save(File saveFile);
	public void load(File saveFile);
}
