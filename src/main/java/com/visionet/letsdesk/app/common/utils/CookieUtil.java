package com.visionet.letsdesk.app.common.utils;

import com.visionet.letsdesk.app.base.controller.BaseController;
import com.visionet.letsdesk.app.common.modules.validate.Validator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

public class CookieUtil {
//	private static String[] cookieKeys = new String[]{"loginUserId","userId","userName","loginUserName","roleName","sid","userImgUrl","lastLoginName","loginBgUrl","logoUrl","nsid","nuid","nuname","nuprofilePhoto","webBgUrl"};
	
	public static void saveUserInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String userName = BaseController.getCurrentUserName();
		String loginUserName = BaseController.getLoginUserName();
		Long userId = BaseController.getCurrentUserId();
		Long loginUserId = BaseController.getLoginUserId();
		String roleName = BaseController.getCurrentRole();
		String sessionId = request.getSession().getId();
		String userImgUrl = BaseController.getUserImgUrl();
		
		saveCookie("loginUserId",loginUserId.toString(),response,request);
		saveCookie("userId", Validator.isNull(userId) ? loginUserId.toString() :userId.toString(),response,request);
		saveCookie("userName", Validator.isNull(userName)? "" : EscapeUnescape.escape(userName),response,request);
		saveCookie("loginUserName", Validator.isNull(loginUserName) ? "" : EscapeUnescape.escape(loginUserName),response,request);
		saveCookie("roleName", Validator.isNull(roleName) ? "" : EscapeUnescape.escape(roleName),response,request);
		saveCookie("sid", sessionId, response,request);
		saveCookie("userImgUrl", Validator.isNull(userImgUrl) ? "" : URLEncoder.encode(userImgUrl,"utf-8"),response,request);
	}
	
	public static void saveCookie(String key,String value,HttpServletResponse response,HttpServletRequest request){
		Cookie[] cookies= request.getCookies();
		Cookie cookie = null;
		
		if(cookies != null){
			for(Cookie c: cookies){
				if(key.equals(c.getName())){
					cookie = c;
				}
			}
		}
		
		if(cookie == null){
			cookie = new Cookie(key, value);
		}
		
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	public static void clearCookie(HttpServletResponse resp,HttpServletRequest request){
		Cookie[] cookies= request.getCookies();
		
		if(cookies != null){
			for(Cookie cookie: cookies){
				if(cookie.getName() != null && cookie.getName().length() > 0 && !"lastLoginName".equals(cookie.getName())){
					cookie.setMaxAge(0);
					cookie.setPath("/");
					resp.addCookie(cookie);
				}
			}
		}
	}
	
}
