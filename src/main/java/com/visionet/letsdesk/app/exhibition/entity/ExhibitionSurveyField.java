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
    private String tableName;
    private String indicator;       //指标

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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }
}
