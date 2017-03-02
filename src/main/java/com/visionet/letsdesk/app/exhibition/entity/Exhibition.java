package com.visionet.letsdesk.app.exhibition.entity;


import com.visionet.letsdesk.app.base.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 门店,展厅
 *
 * @author xt
 */
@Entity
@Table(name = "s_exhibition")
public class Exhibition extends IdEntity {

    private String name;
    private String address;
    private Long dealerId;      //经销商
    private Long brandId;       //品牌
//    private Long marketId;      //商场ID
    private String marketName;      //商场
    private Long cityId;        //城市
    private Date createDate;
    private Date updateDate;
    private Integer delFlag;

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

    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}

