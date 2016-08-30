package com.visionet.letsdesk.app.common.security.password;

import com.visionet.letsdesk.app.common.modules.string.StringPool;

/**
 * 
 * @author visionet
 *
 */
public interface Digester {

	public static final String ENCODING = StringPool.UTF8;

	public static final String DIGEST_ALGORITHM = "SHA";

	public String digest(String text);

	public String digest(String algorithm, String text);

}
