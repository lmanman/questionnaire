package com.visionet.letsdesk.app.dictionary.entity;

import com.visionet.letsdesk.app.base.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 城市
 *
 * @author xt
 */
@Entity
@Table(name = "d_city")
public class City extends IdEntity{

    private String cityName;
    private String telephoneAreaCode;
    private Long provinceId;
    private String provinceName;
    private Integer delFlag;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTelephoneAreaCode() {
        return telephoneAreaCode;
    }

    public void setTelephoneAreaCode(String telephoneAreaCode) {
        this.telephoneAreaCode = telephoneAreaCode;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
