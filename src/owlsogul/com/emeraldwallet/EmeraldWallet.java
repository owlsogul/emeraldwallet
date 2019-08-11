package owlsogul.com.emeraldwallet;

public class EmeraldWallet {

	private String tag;
	private String desc;
	private int size;
	private int amount[];
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int[] getAmount() {
		return amount;
	}
	public void setAmount(int amount[]) {
		this.amount = amount;
	}
	
}
