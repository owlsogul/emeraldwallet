package test;

public class TimeKey {

	public static void main(String[] args) {
		System.out.println(randomKey());
		String key = "덶삵뎆뎃샄사뎉겖뇩뇸상삭눀샂겞삺겔겓삮뎀맡겒덹뇯겜맘맛겡삿망뇬덾삽덻샆덼겛겐덷뎈맟삼덺뇪덳샃삯덿겚뇳맙눁삶덴겟삱맠겗삷덵뇲맣겘산뇰뇵삸뎇삳겦맞살뇻겥뎊뎅삻삹샀덱겧덲덽뎂뇭겠삲뇶겝뎄겏뇺맚맢뇮겢델뎁맜겑";
		String encodedStr = makeTimeEnc(key);
		System.out.println(encodedStr);
		decode(key, encodedStr);
		
	}
	
	// 덶삵뎆뎃샄사뎉겖뇩뇸상삭눀샂겞삺겔겓삮뎀맡겒덹뇯겜맘맛겡삿망뇬덾삽덻샆덼겛겐덷뎈맟삼덺뇪덳샃삯덿겚뇳맙눁삶덴겟삱맠겗삷덵뇲맣겘산뇰뇵삸뎇삳겦맞살뇻겥뎊뎅삻삹샀덱겧덲덽뎂뇭겠삲뇶겝뎄겏뇺맚맢뇮겢델뎁맜겑
	private static String randomKey() {
		String keyStr = "겏겐겑겒겓겔겖겗겘겚겛겜겝겞겟겠겡겢겥겦겧뇩뇪뇬뇭뇮뇯뇰뇲뇳뇵뇶뇸뇺뇻눀눁덱덲덳덴덵덶덷델덹덺덻덼덽덾덿뎀뎁뎂뎃뎄뎅뎆뎇뎈뎉뎊맘맙맚맛맜망맞맟맠맡맢맣사삭삮삯산삱삲삳살삵삶삷삸삹삺삻삼삽삿샀상샂샃샄샆";
		char[] keyChar = keyStr.toCharArray();
		System.out.println(keyStr.length());
		int loopCount = (int)(Math.random()*100000)+1000;
		for (int i = 0; i < loopCount; i++) {
			int start = (int)(Math.random()*keyChar.length);
			int end = (int)(Math.random()*keyChar.length);
			char a = keyChar[start];
			keyChar[start] = keyChar[end];
			keyChar[end] = a;
		}
		System.out.println(loopCount);
		return String.valueOf(keyChar);
	}
	
	private static String makeTimeEnc(String key) {
		long currentTime = System.currentTimeMillis();
		int indicator = (int)Math.log10(currentTime) + 1;
		int randomNumbers[] = new int[indicator];
		for (int i = 0; i < indicator; i++) {
			randomNumbers[i] = (int)(Math.random()*10);
			System.out.print(randomNumbers[i]);
		}
		System.out.println();
		String timeStr = String.valueOf(currentTime);
		System.out.println(timeStr);
		StringBuilder madeStr = new StringBuilder();
		StringBuilder encoder = new StringBuilder();
		for (int i = 0; i < indicator; i++) {
			StringBuilder tempStr = new StringBuilder();
			tempStr.append(randomNumbers[i]);
			tempStr.append(timeStr.charAt(i));
			madeStr.append(tempStr);
			encoder.append(key.charAt(Integer.valueOf(tempStr.toString())));
		}
		System.out.println(madeStr.toString());
		return encoder.toString();
	}
	
	private static String decode(String key, String encodedStr) {
		char[] encodedChar = encodedStr.toCharArray();
		StringBuilder beforeCharEncode = new StringBuilder();
		StringBuilder beforeMix = new StringBuilder();
		for (int i = 0; i < encodedChar.length; i++) {
			String temp = String.format("%02d", find(key.toCharArray(), encodedChar[i]));
			beforeCharEncode.append(temp);
			beforeMix.append(temp.charAt(1));
		}
		System.out.println("f " + beforeCharEncode);
		System.out.println("g " + beforeMix);
		return "";
	}
	private static int find(char[] arr, char c) {
		for(int i = 0; i < arr.length; i++) {
			if (arr[i] == c) return i;
		}
		return -1;
	}
}
