package com.visionet.letsdesk.app.common.security.password;

/**
 * 
 * @author visionet
 *
 */
public class DigesterUtil {

	public static String digest(String text) {
		return getDigester().digest(text);
	}

	public static String digest(String algorithm, String text) {
		return getDigester().digest(algorithm, text);
	}

	public static Digester getDigester() {
		return _digester;
	}

	private static Digester _digester = new DigesterImp();
}
