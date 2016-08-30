package com.visionet.letsdesk.app.common.utils;

import com.visionet.letsdesk.app.common.modules.props.PropsUtil;

/**
 * @author add by WangBo 2014-1-24
 */

public class TencentUtil {
	
	private final static String app_key = "801473845";
	private final static String app_secret = "ae8bfb736013968ae07b81caa672542d";
	private final static String redirect_url = PropsUtil.getProperty("local.test.url") + "/mobile/tencent/callback";
	
	public final static String dataFormat = "json";
	
//	public final static String QQ_AUTHORIZE_URL = "https://graph.qq.com/oauth2.0/authorize?response_type=token&client_id=1101800495&redirect_uri="+URLEncoder.encode(PropsUtil.getProperty("service.domain") +"qqLogin", "utf-8");
	
	public static String getQQAuthorizeURL() throws Exception {
		String qqAuthorizeUrl = PropsUtil.getProperty("service.domain") + "api/toTencentPage";
		return qqAuthorizeUrl;
	}
	
	public static void main(String[] args)  throws Exception{
	}

}

