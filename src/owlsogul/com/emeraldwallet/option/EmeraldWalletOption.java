package owlsogul.com.emeraldwallet.option;

import org.bukkit.Material;

public final class EmeraldWalletOption {
	
	public String[] walletItemType = { 
												Material.RED_WOOL.toString(), 
												Material.ORANGE_WOOL.toString(),
												Material.YELLOW_WOOL.toString(),
												Material.GREEN_WOOL.toString(),
												Material.BLUE_WOOL.toString(),
												Material.LIGHT_GRAY_WOOL.toString(),
												Material.PURPLE_WOOL.toString(),
												Material.BLACK_WOOL.toString()
											};
	public String walletTitle = "[Emerald Wallet]";
	public String walletItemName = "Emerald Wallet";
	public int walletTagRow = 0;
	public String walletPrefix = "#";
	public String walletKey = "겏겐겑겒겓겔겖겗겘겚겛겜겝겞겟겠겡겢겥겦겧뇩뇪뇬뇭뇮뇯뇰뇲뇳뇵뇶뇸뇺뇻눀눁덱덲덳덴덵덶덷델덹덺덻덼덽덾덿뎀뎁뎂뎃뎄뎅뎆뎇뎈뎉뎊맘맙맚맛맜망맞맟맠맡맢맣사삭삮삯산삱삲삳살삵삶삷삸삹삺삻삼삽삿샀상샂샃샄샆";
	public int walletDescRow = 1;
	
	
	public static int contain(String str) {
		for (int i = 0; i < instance.walletItemType.length; i++) if (instance.walletItemType[i].equals(str)) return i;
		return -1;
	}
	
	// MARK: 싱글톤 파
	private static EmeraldWalletOption instance = null;
	public static EmeraldWalletOption getInstance() {
		if (instance == null) { instance = new EmeraldWalletOption(); }
		return instance;
	}
	private EmeraldWalletOption() {}
	
	
}
