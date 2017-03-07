package com.visionet.letsdesk.app.attachment.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.visionet.letsdesk.app.common.modules.props.PropsKeys;
import com.visionet.letsdesk.app.common.modules.props.PropsUtil;
import com.visionet.letsdesk.app.common.modules.string.StringPool;

public class AttachmentVo {

    private String fullPath;
    private String relativePath;
    private String uuidName;
    private String realName;
    private String sign;
    private String state = StringPool.SUCCESS;

    @JsonIgnore
    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    @JsonIgnore
    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getUuidName() {
        return uuidName;
    }

    public void setUuidName(String uuidName) {
        this.uuidName = uuidName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrl(){
        return PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_DOWNLOAD_PATH)+this.relativePath;
    }
}
