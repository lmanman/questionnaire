package com.visionet.letsdesk.app.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.visionet.letsdesk.app.base.entity.JsonDateSerializer;
import com.visionet.letsdesk.app.base.vo.BaseVo;

import java.util.Date;

public class OrganizationVo extends BaseVo{

    private String domain;
    private String shortName;
    private String fullName;
    private String logoUrl;
    private String remark;

    private Integer isLock;
    private String email;
    private Date createDate;


    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
