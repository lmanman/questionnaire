package com.visionet.letsdesk.app.dictionary.entity;


import com.visionet.letsdesk.app.base.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 字典杂项
 *
 * @author xt
 */
@Entity
@Table(name = "d_sundry")
public class Sundry extends IdEntity{

    private String code;
    private String name;
    private String type;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
