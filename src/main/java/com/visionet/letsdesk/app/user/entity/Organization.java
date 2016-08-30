package com.visionet.letsdesk.app.user.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.visionet.letsdesk.app.base.entity.IdEntity;
import com.visionet.letsdesk.app.base.entity.JsonDateSerializer;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 公司表
 * @author lxj
 *
 */
@Entity
@Table(name="c_org")
public class Organization extends IdEntity {
    private static final long serialVersionUID = 20099985831532979L;

    private String domain;
    private String shortName;
    private String fullName;
    private String logoUrl;
    private String remark;

    //公司是否锁定
    private Integer isLock;
    //电子邮件
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
