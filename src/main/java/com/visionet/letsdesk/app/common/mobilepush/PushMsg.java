package com.visionet.letsdesk.app.common.mobilepush;

import java.util.Map;

public class PushMsg {
	
	private int sendNo;
	private String alias;
	private String tag;
	private String title;
	private String content;
	private Map<String,Object> extra;
	
	public int getSendNo() {
		return sendNo;
	}
	public void setSendNo(int sendNo) {
		this.sendNo = sendNo;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Map<String, Object> getExtra() {
		return extra;
	}
	public void setExtra(Map<String, Object> extra) {
		this.extra = extra;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	
}
