package com.visionet.letsdesk.app.dictionary.vo;

import com.visionet.letsdesk.app.base.vo.BaseVo;

public class ManufacturerVo extends BaseVo{

    private String name;
    private String address;
    private String telephone;
    private String linkman;

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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }
}
