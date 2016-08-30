package com.visionet.letsdesk.app.user.entity;

import com.visionet.letsdesk.app.base.entity.IdEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 菜单.
 *
 * @author xt
 */
@Entity
@Table(name = "c_resource")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Resource extends IdEntity {

    private String name;
    private String hyperlink;
    private Integer type;
    private Float position;
    private Date createDate;
    private String permission;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHyperlink() {
        return hyperlink;
    }

    public void setHyperlink(String hyperlink) {
        this.hyperlink = hyperlink;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Float getPosition() {
        return position;
    }

    public void setPosition(Float position) {
        this.position = position;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }






    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
