package com.visionet.letsdesk.app.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;
import com.visionet.letsdesk.app.base.entity.IdEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * 角色.
 * 
 * @author xt
 */
@Entity
@Table(name = "c_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role extends IdEntity {

	private String name;
	private String roleDesc;
	private String type;
	private String permissions;
	private String defaultUrl;
	
	public Role(){
	}
	public Role(Long id,String name){
		this.id=id;
		this.name=name;
	}
	

	public Role(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}
	
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getRoleDesc() {
		return roleDesc;
	}


	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	
	


	public String getDefaultUrl() {
		return defaultUrl;
	}


	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}


	@Transient
	@JsonIgnore
	public List<String> getPermissionList() {
		if(permissions == null){
			return null;
		}
		return ImmutableList.copyOf(StringUtils.split(permissions, ","));
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
