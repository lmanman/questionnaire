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
    private String address;
    private Long cityId;

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
}
