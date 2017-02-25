package com.visionet.letsdesk.app.user.vo;

import com.visionet.letsdesk.app.base.vo.BaseVo;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 角色.
 * 
 * @author xt
 */
public class RoleVo extends BaseVo {

	private String name;
	private String roleDesc;
	private String type;
	private String permissions;
	private String defaultUrl;
	private Boolean hasRole;


	public RoleVo(){
	}
	

	public RoleVo(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getRoleDesc() {
		return roleDesc;
	}


	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getPermissions() {
		return permissions;
	}


	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}


	public String getDefaultUrl() {
		return defaultUrl;
	}


	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}

	public Boolean getHasRole() {
		return hasRole;
	}

	public void setHasRole(Boolean hasRole) {
		this.hasRole = hasRole;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
