package com.visionet.letsdesk.app.exhibition.entity;

import com.visionet.letsdesk.app.base.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 展厅字段
 *
 * @author xt
 */
@Entity
@Table(name = "s_exhibition_survey_field")
public class ExhibitionSurveyField extends IdEntity{

    private String fieldName;
    private String fieldDesc;
    private String fieldFormat;		//radio,checkbox,textarea
    private String relationData;
    private String indicator;       //指标
    private Integer delFlag;
    private Long formId;
    private Float orderId;
    private Integer shortFlag;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldDesc() {
        return fieldDesc;
    }

    public void setFieldDesc(String fieldDesc) {
        this.fieldDesc = fieldDesc;
    }

    public String getFieldFormat() {
        return fieldFormat;
    }

    public void setFieldFormat(String fieldFormat) {
        this.fieldFormat = fieldFormat;
    }

    public String getRelationData() {
        return relationData;
    }

    public void setRelationData(String relationData) {
        this.relationData = relationData;
    }

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public Float getOrderId() {
        return orderId;
    }

    public void setOrderId(Float orderId) {
        this.orderId = orderId;
    }

    public Integer getShortFlag() {
        return shortFlag;
    }

    public void setShortFlag(Integer shortFlag) {
        this.shortFlag = shortFlag;
    }
}
