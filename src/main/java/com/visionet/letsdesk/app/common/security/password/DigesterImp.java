package com.visionet.letsdesk.app.common.security.password;

import com.visionet.letsdesk.app.common.modules.props.PropsKeys;
import com.visionet.letsdesk.app.common.modules.props.PropsUtil;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author visionet
 *
 */
public class DigesterImp implements Digester {
	
	private static Logger _log = LoggerFactory.getLogger(DigesterImp.class);

	
	public String digest(String text) {
		// TODO Auto-generated method stub
		return digest(Digester.DIGEST_ALGORITHM, text);
	}

	
	public String digest(String algorithm, String text) {
		// TODO Auto-generated method stub

		MessageDigest digester = null;

		try{
			digester = MessageDigest.getInstance(algorithm);

			digester.update(text.getBytes(Digester.ENCODING));

            byte[] bytes = digester.digest();

            if (_BASE_64) {
                return Base64.encode(bytes);
            } else {
                return new String(Hex.encodeHex(bytes));
            }
		} catch (NoSuchAlgorithmException nsae) {
			_log.error("digest:",nsae);
		} catch (UnsupportedEncodingException uee) {
			_log.error("digest:",uee);
		}
        return null;

	}
	
	private static final boolean _BASE_64 =	PropsUtil.getProperty(PropsKeys.PASSWORDS_DIGEST_ENCODING).equals("base64");

	
}
