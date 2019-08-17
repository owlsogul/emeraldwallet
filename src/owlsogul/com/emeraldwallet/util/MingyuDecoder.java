package owlsogul.com.emeraldwallet.util;

public class MingyuDecoder {

	public static long decodeMingyu(String key, String encodedStr) throws MingyuFormatException{
		
		char[] encodedChar = encodedStr.toCharArray();
		StringBuilder beforeMix = new StringBuilder();
		for (int i = 0; i < encodedChar.length; i++) {
			int index = find(key.toCharArray(), encodedChar[i]);
			if (index == -1) throw new MingyuFormatException("Not-Encoded-By-Key");
			String temp = String.format("%02d", index);
			beforeMix.append(temp.charAt(1));
		}
		return Long.valueOf(beforeMix.toString());
		
	}
	
	private static int find(char[] arr, char c) {
		for(int i = 0; i < arr.length; i++) {
			if (arr[i] == c) return i;
		}
		return -1;
	}
	
}
