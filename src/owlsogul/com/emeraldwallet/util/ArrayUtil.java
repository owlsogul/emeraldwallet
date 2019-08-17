package owlsogul.com.emeraldwallet.util;

import java.util.Iterator;

public class ArrayUtil {

	public static boolean contain(Iterator<Object> iter, Object obj) {
		while (iter.hasNext()) {
			if (iter.next().equals(obj)) {
				return true;
			}
		}
		return false;
	}
	
}
