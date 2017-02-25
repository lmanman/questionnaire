package com.visionet.letsdesk.app.common.constant;

public interface SysConstants {
	
	/*---------------- Login ---------------------*/
	public static final String CLIENT_FLAG = "client_flag";
	public static final String CLIENT_WEB = "web";
	public static final String CLIENT_IOS = "ios";
	public static final String CLIENT_ANDROID = "android";
	
	public static final String SESSION_FORCE_LOGOUT_KEY = "session.force.logout";
	
	/*---------------- Common ---------------------*/
    public static final String DOMAIN = "letsdesk.com";

	public static final int DELETE_FLAG_YES=1;
	public static final int DELETE_FLAG_NO=0;
	
	public static final String TMP_DIR = "java.io.tmpdir";
	
	public static final String SORT_TYPE_AUTO = "auto";
	public static final String JSP_SEARCH_FILTER = "search_";
	
	public static final String PRIVACY_FILTER = "confidentiality";
	
	
	/*---------------- Console ---------------------*/
	public static final String CONSOLE_FLAG="consoleFlag";
	
	/*---------------- User ---------------------*/
	public static final Long USER_MAIL = 8L;
	
	public static final int USER_ACTIVITY_ENABLED = 0;
	public static final int USER_ACTIVITY_DISABLED = 1;
	
	/*---------------- Role ---------------------*/
	public static final String PERMISSIONS = "view,edit";
	public static final String ADMIN_ROLE_TYPE = "A";
	public static final String COMMON_ROLE_TYPE = "C";
	

    //超级管理员
	public static final String ADMINISTRATOR = "admin";
    //公司管理员
	public static final String SUBADMIN = "subadmin";
    //经理
    public static final String MANAGER = "manager";
    //组长
//	public static final String TEAM_LEADER = "teamLeader";
    //调查员
    public static final String CUSTOMER_SERVICE = "customService";
    //手机客服
	public static final String MOBILE_SERVICE = "mobileService";
    //工程师
    public static final String ENGINEER = "engineer";
	
	
	/*---------------- Organization ---------------------*/
	public static final Long COMMON_ORG_ID = 0L;

	/*---------------- SystemPlugin ---------------------*/
	public static final String SYS_TYPE_SUBSYSTEM = "S";
	public static final String SYS_TYPE_PUBLICMEDIA = "M";
	
	/*-------------------管理员id--------------------*/
	public static final Long ADMIN_ID = 1l;
	
	/*---------------- 短信 ---------------------*/
	public static final Integer SMS_UNSENT = 0;
	public static final Integer SMS_SENT = 1;
	public static final Integer SMS_DONOT_SEND = -1;
	
}
