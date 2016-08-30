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
@Table(name = "d_province")
public class Province extends IdEntity{

    private String provinceName;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
}
