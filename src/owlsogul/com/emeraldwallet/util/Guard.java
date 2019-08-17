package owlsogul.com.emeraldwallet.util;

/**
 *	스위프트에만 존재하는 guard 개념을 살려보고자 만든 클래스입니다.
 *	정적함수만 존재하므로 그냥 Guard.guard 를 쓰시면 됩니다.
 * @author mingyu
 *
 */
public class Guard {

	/**
	 * booleanStatement를 역으로 구해주는 역할뿐이지만 코드를 좀 더 깔끔하게 해줍니다.
	 * 스위프트의 guard 개념을 적용하였습니다.
	 * @param booleanStatement
	 * @return
	 */
	public static boolean guard(boolean booleanStatement) {
		return !booleanStatement;
	}
	
}
