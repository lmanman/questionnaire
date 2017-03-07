package com.visionet.letsdesk.app.attachment.vo;

public class PhotoVo {

    private Long id;
    private String realName;
    private String fileUrl;
    private String minUrl;      //缩略图url
    private Long refId;
    private String refType;     //s_exhibition_survey_field.field_name

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getMinUrl() {
        return minUrl;
    }

    public void setMinUrl(String minUrl) {
        this.minUrl = minUrl;
    }

    public Long getRefId() {
        return refId;
    }

    public void setRefId(Long refId) {
        this.refId = refId;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }
}
