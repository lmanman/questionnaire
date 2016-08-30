package com.visionet.letsdesk.app.foundation.entity;


import com.visionet.letsdesk.app.base.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "c_mobile_client")
public class MobileClient extends IdEntity {

    private String clientName;
    private String clientVersion;
    private String clientSize;
    private String clientType;
    private String clientDesc;
    private Date createDate;

    public String getClientName() {
        return clientName;
    }
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    public String getClientVersion() {
        return clientVersion;
    }
    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }
    public String getClientSize() {
        return clientSize;
    }
    public void setClientSize(String clientSize) {
        this.clientSize = clientSize;
    }
    public String getClientType() {
        return clientType;
    }
    public void setClientType(String clientType) {
        this.clientType = clientType;
    }
    public String getClientDesc() {
        return clientDesc;
    }
    public void setClientDesc(String clientDesc) {
        this.clientDesc = clientDesc;
    }

    @Column(insertable=false,updatable=false)
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }





}
