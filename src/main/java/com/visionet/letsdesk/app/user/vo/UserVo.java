package com.visionet.letsdesk.app.user.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.visionet.letsdesk.app.base.entity.JsonDateSerializer;
import com.visionet.letsdesk.app.base.vo.BaseVo;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

public class UserVo extends BaseVo{

    private String loginName;
    private String aliasName;       //昵称
    private String trueName;        //真实姓名
    private String password;

    private String plainPassword;
    private String passwordSalt;

    private String userType;        //经理模式:M; 客服模式:K
    private String avatar;
    private String locale;
    private String firstLetter;
    private String phoneNumber;
    private String email;
    private String remark;

    private Long orgId;

    private Integer isLock;
    private Date lastLogin;


    /*---------VO------------*/
    private String domain;
    private String orgName;
    private String queryName;
    private List<String> roleNameList;
    private Integer talkingNum;
    private List<RoleVo> roleList;
    private String roleDescs;


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
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Size(min=6, max=16)
    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
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

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Size(min=4, max=40)
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public List<String> getRoleNameList() {
        return roleNameList;
    }

    public void setRoleNameList(List<String> roleNameList) {
        this.roleNameList = roleNameList;
    }

    public Integer getTalkingNum() {
        return talkingNum;
    }

    public void setTalkingNum(Integer talkingNum) {
        this.talkingNum = talkingNum;
    }

    public List<RoleVo> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleVo> roleList) {
        this.roleList = roleList;
    }

    public String getRoleDescs() {
        return roleDescs;
    }

    public void setRoleDescs(String roleDescs) {
        this.roleDescs = roleDescs;
    }
}
