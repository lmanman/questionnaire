package com.visionet.letsdesk.app.common.modules.string;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author visionet
 *
 */
public class UnicodeFormatter {
	private static Logger _log = LoggerFactory.getLogger(UnicodeFormatter.class);
	
	public static String byteToHex(byte b) {
		char[] array = {_HEX_DIGITS[(b >> 4) & 0x0f], _HEX_DIGITS[b & 0x0f]};

		return new String(array);
	}

	public static String charToHex(char c) {
		byte hi = (byte)(c >>> 8);
		byte lo = (byte)(c & 0xff);

		char[] array = {
			_HEX_DIGITS[(hi >> 4) & 0x0f], _HEX_DIGITS[hi & 0x0f],
			_HEX_DIGITS[(lo >> 4) & 0x0f], _HEX_DIGITS[lo & 0x0f]
		};

		return new String(array);
	}

	public static String parseString(String hexString) {
		StringBuilder sb = new StringBuilder();

		char[] array = hexString.toCharArray();

		if ((array.length % 6) != 0) {
			_log.error("String is not in hex format");

			return hexString;
		}

		for (int i = 2; i < hexString.length(); i = i + 6) {
			String s = hexString.substring(i, i + 4);

			try {
				char c = (char)Integer.parseInt(s, 16);

				sb.append(c);
			}
			catch (Exception e) {
				_log.error(e.toString());

				return hexString;
			}
		}

		return sb.toString();
	}

	public static String toString(char[] array) {
		StringBuilder sb = new StringBuilder(array.length * 6);

		char[] hexes = new char[4];

		for (int i = 0; i < array.length; i++) {
			sb.append(_UNICODE_PREFIX);
			sb.append(_charToHex(array[i], hexes));
		}

		return sb.toString();
	}

	public static String toString(String s) {
		if (s == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder(s.length() * 6);

		char[] hexes = new char[4];

		for (int i = 0; i < s.length(); i++) {
			sb.append(_UNICODE_PREFIX);
			sb.append(_charToHex(s.charAt(i), hexes));
		}

		return sb.toString();
	}

	private static char[] _charToHex(char c, char[] hexes) {
		byte hi = (byte)(c >>> 8);
		byte lo = (byte)(c & 0xff);

		hexes[0] = _HEX_DIGITS[(hi >> 4) & 0x0f];
		hexes[1] = _HEX_DIGITS[hi & 0x0f];
		hexes[2] = _HEX_DIGITS[(lo >> 4) & 0x0f];
		hexes[3] = _HEX_DIGITS[lo & 0x0f];

		return hexes;
	}

	private static final char[] _HEX_DIGITS = {
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
		'e', 'f'
	};

	private static final String _UNICODE_PREFIX = "\\u";

}
