package com.visionet.letsdesk.app.common.utils;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class InstanceUtil {

	private InstanceUtil(){}
	
	private static WebApplicationContext webAppContext;
	
	public static <T> T getInstanceByType(Class<T> type) {
		return getWebApplicationContextInstance().getBean(type);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getInstanceByType(String className, Class<T> returnType) throws ClassNotFoundException {
		Class<?> onwClass = Class.forName(className);
		return (T)getWebApplicationContextInstance().getBean(onwClass);
	}


	public synchronized static WebApplicationContext getWebApplicationContextInstance(){
		if(webAppContext == null){
            webAppContext = ContextLoader.getCurrentWebApplicationContext();
		}
		return webAppContext;
	}
	
}
