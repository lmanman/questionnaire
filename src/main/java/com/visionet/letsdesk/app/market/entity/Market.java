package com.visionet.letsdesk.app.market.entity;

import com.visionet.letsdesk.app.base.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 商场
 *
 * @author xt
 */
@Entity
@Table(name = "s_market")
public class Market extends IdEntity{

    private String name;
    private Long provinceId;
    private Long cityId;
    private String address;
    private String businessHours;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours;
    }
}
