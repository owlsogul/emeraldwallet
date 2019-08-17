package owlsogul.com.emeraldwallet;

public class EmeraldWallet {

	public static int MAX_SIZE = 9*6;
	public static int MIN_SIZE = 9;
	
	private String tag;
	private String desc;
	private int size;
	private int container[];
	
	public String getTag() { return tag; }
	public void setTag(String tag) { this.tag = tag; }
	public String getDesc() {return desc; }
	public void setDesc(String desc) { this.desc = desc; }
	public int getSize() { return size; }
	public void setSize(int size) { this.size = size; }
	public int[] getContainer() { return container; }
	public void setContainer(int container[]) { this.container = validateContainer(container); }
	
	private static int[] validateContainer(int[] container) {
		int originSize = container.length;
		int[] copiedArr = null;
		if (originSize < MIN_SIZE) {
			copiedArr = new int[MIN_SIZE];
			System.arraycopy(container, 0, copiedArr, 0, originSize);
			for (int i = originSize; i < copiedArr.length; i++) copiedArr[i] = 0;
		}
		else if (originSize > MAX_SIZE) {
			copiedArr = new int[MAX_SIZE];
			System.arraycopy(container, 0, copiedArr, 0, copiedArr.length);
		}
		else {
			copiedArr = new int[originSize];
			System.arraycopy(container, 0, copiedArr, 0, container.length);
		}
		return copiedArr;
	}
	
	@Override
	public String toString() {
		return String.format("\"EmeraldWallet\" : { \"tag\": \"%s\", \"desc\":\"%s\", \"size\":%d }", this.tag, this.desc, this.size);
	}
	
	
	
	public static EmeraldWallet createEmeraldWallet(String tag, String desc, int[] container) {
		EmeraldWallet wallet = new EmeraldWallet();
		int copiedContainer[] = validateContainer(container);
		int size = copiedContainer.length;
		wallet.setTag(tag); wallet.setDesc(desc); wallet.setSize(size); wallet.setContainer(container);
		return wallet;
	}
	
	public static EmeraldWallet createEmeraldWallet(String tag, String desc, int containerSize) {
		int[] container = new int[containerSize];
		for (int i = 0;i < containerSize; i++) container[i] = 0;
		return createEmeraldWallet(tag, desc, container);
	}
	
}
