package com.visionet.letsdesk.app.foundation.entity;


import com.visionet.letsdesk.app.base.entity.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "c_system_dict")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SystemDict extends IdEntity {

    private String systemCode;
    private String systemName;
    private String systemType;
    private String systemTab;
    private String systemDesc;


    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public String getSystemTab() {
        return systemTab;
    }

    public void setSystemTab(String systemTab) {
        this.systemTab = systemTab;
    }

    public String getSystemDesc() {
        return systemDesc;
    }

    public void setSystemDesc(String systemDesc) {
        this.systemDesc = systemDesc;
    }
}
