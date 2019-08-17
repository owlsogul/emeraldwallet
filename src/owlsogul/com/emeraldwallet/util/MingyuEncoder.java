package owlsogul.com.emeraldwallet.util;

public class MingyuEncoder {

	public static String encodeMingyu(String key, long time) {
		int indicator = (int)Math.log10(time) + 1;
		int randomNumbers[] = new int[indicator];
		for (int i = 0; i < indicator; i++) {
			randomNumbers[i] = (int)(Math.random()*10);
		}
		String timeStr = String.valueOf(time);
		StringBuilder encoder = new StringBuilder();
		for (int i = 0; i < indicator; i++) {
			StringBuilder tempStr = new StringBuilder();
			tempStr.append(randomNumbers[i]);
			tempStr.append(timeStr.charAt(i));
			encoder.append(key.charAt(Integer.valueOf(tempStr.toString())));
		}
		return encoder.toString();
	}
	
}
