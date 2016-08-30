package com.visionet.letsdesk.app.dictionary.vo;

import com.visionet.letsdesk.app.base.vo.BaseVo;
import com.visionet.letsdesk.app.dictionary.entity.City;
import com.visionet.letsdesk.app.exhibition.entity.Dealer;
import com.visionet.letsdesk.app.market.entity.Market;

import java.util.Date;

public class ExhibitionVo extends BaseVo{

    private String name;
    private String address;
    private Dealer dealer;      //经销商
    private BrandVo brandVo;        //品牌
    private Market market;      //商场
    private City city;          //城市
    private Date createDate;

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

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public BrandVo getBrandVo() {
        return brandVo;
    }

    public void setBrandVo(BrandVo brandVo) {
        this.brandVo = brandVo;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
