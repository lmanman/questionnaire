package com.visionet.letsdesk.app.attachment.entity;

import com.visionet.letsdesk.app.base.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 照片
 *
 * @author xt
 */
@Entity
@Table(name = "s_photo")
public class Photo extends IdEntity{

    private String realName;
    private String fileType;
    private String fileUrl;
    private String minUrl;      //缩略图url
    private Integer width;      //图片宽度
    private Integer heigth;     //图片高度
    private Long refId;
    private String refType;     //s_exhibition_survey_field.field_name
    private Long size;          //文件大小
    private Date createDate;
    private String remark;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
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

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeigth() {
        return heigth;
    }

    public void setHeigth(Integer heigth) {
        this.heigth = heigth;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
