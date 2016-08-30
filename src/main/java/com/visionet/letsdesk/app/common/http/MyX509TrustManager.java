package com.visionet.letsdesk.app.common.http;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 证书信任管理器（用于https请求）
 * 		这个证书管理器的作用就是让它信任我们指定的证书，
 * 		下面的代码意味着信任所有证书，不管是否权威机构颁发。
 * @author 李晓健
 * @date 2014年7月12日 下午12:03:17
 */
public class MyX509TrustManager implements X509TrustManager{

	@Override
	public void checkClientTrusted(X509Certificate[] arg0, String arg1)
			throws CertificateException {
	}

	@Override
	public void checkServerTrusted(X509Certificate[] arg0, String arg1)
			throws CertificateException {
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}

}
