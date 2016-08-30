package com.visionet.letsdesk.app.exhibition.vo;

import com.visionet.letsdesk.app.base.vo.BaseVo;

public class DealerVo extends BaseVo{

    private String name;
    private String linkman;
    private String telephone;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
