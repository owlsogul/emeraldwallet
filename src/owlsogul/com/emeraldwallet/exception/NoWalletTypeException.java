package owlsogul.com.emeraldwallet.exception;

public class NoWalletTypeException extends Exception{

	private static final long serialVersionUID = 1L;

	public NoWalletTypeException() {
		super("No Wallet Item Type");
	}
}
