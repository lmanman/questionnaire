package com.visionet.letsdesk.app.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Sets;
import com.visionet.letsdesk.app.base.entity.IdEntity;
import com.visionet.letsdesk.app.base.entity.JsonDateSerializer;
import com.visionet.letsdesk.app.common.modules.utils.Collections3;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * 用户.
 * 
 * @author xt
 */
@Entity
@Table(name = "c_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends IdEntity {
	
	public User(){
	}

	public User(Long id) {
		this.id = id;
	}
	
	private String loginName;       //登录名
	private String aliasName;       //昵称
    private String trueName;        //真实姓名
	private String password;
	private String plainPassword;
	private String passwordSalt;

    private String userType;        //经理模式:M; 客服模式:K
    private String avatar;          //头像url
    private String locale;          //语言
    private String firstLetter;     //昵称首字母
    private String phoneNumber;
    private String email;
    private String remark;

	private Long orgId;             //公司ID

	private Integer isLock;         //是否注销  1:是；0:否
	private Date lastLogin;         //上次登录时间
	
	private Set<Role> roleSet = Sets.newHashSet();

	@Transient
	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	
	@NotBlank
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    @JsonIgnore
    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getIsLock() {
		return isLock;
	}

	public void setIsLock(Integer isLock) {
		this.isLock = isLock;
	}

	@Column(insertable=false)
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @JsonIgnore
    public String getPasswordSalt() {
		return passwordSalt;
	}

	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

	// 多对多定义
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "c_user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	// Fecth策略定义
	@Fetch(FetchMode.SUBSELECT)
	// 集合按id排序
	@OrderBy("id ASC")
	// 缓存策略
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//	@JsonIgnore
	public Set<Role> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}
	



	@Transient
	public String getRoleNames() {
		if(roleSet == null) return null;
		return Collections3.extractToString(roleSet, "name", ", ");
	}
	


	@Column(updatable=false)
	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	


    @Override
    public int hashCode(){
        return this.getId()==null?1:this.getId().hashCode();
    }
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		if( null== obj||!( obj instanceof User)){
			return false;
		}
		User user = (User)obj;
		if(user.getId()==null){
			return false;
		}
		return user.getId().equals(this.id);
	}






	
}