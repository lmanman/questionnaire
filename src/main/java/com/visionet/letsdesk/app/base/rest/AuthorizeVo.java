package com.visionet.letsdesk.app.base.rest;


public class AuthorizeVo {
	
	
	private String client_id;		//申请应用时分配的AppKey
	private String redirect_uri;	//授权回调地址
	private String scope;			//申请scope权限所需参数，可一次申请多个scope权限，用逗号分隔
	private String state;			//用于保持请求和回调的状态
	private String display;			//授权页面的终端类型
	private String forcelogin;		//是否强制用户重新登录，true：是，false：否
	private String language;		//授权页语言，缺省为中文简体版，en为英文版
	private String grant_type;		//请求的类型，填写authorization_code
	
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getRedirect_uri() {
		return redirect_uri;
	}
	public void setRedirect_uri(String redirect_uri) {
		this.redirect_uri = redirect_uri;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public String getForcelogin() {
		return forcelogin;
	}
	public void setForcelogin(String forcelogin) {
		this.forcelogin = forcelogin;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getGrant_type() {
		return grant_type;
	}
	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}


}
