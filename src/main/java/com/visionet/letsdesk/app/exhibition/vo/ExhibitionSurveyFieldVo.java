package com.visionet.letsdesk.app.exhibition.vo;

import com.visionet.letsdesk.app.dictionary.vo.SundryVo;

import java.util.List;

/**
 * 展厅字段说明
 *
 * @author xt
 */
public class ExhibitionSurveyFieldVo {

    private String fieldName;
    private String fieldDesc;
    private String fieldFormat;		//radio,checkbox,textarea
    private String indicator;       //指标
    private String relationData;
    private Long formId;
    private Float orderId;
    private List<SundryVo> optionList;

//    private static class Option {
//        Long id;
//        String name;
//
//        Option(Long id,String name) {
//            this.id = id;
//            this.name = name;
//        }
//    }


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

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public String getRelationData() {
        return relationData;
    }

    public void setRelationData(String relationData) {
        this.relationData = relationData;
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

    public List<SundryVo> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<SundryVo> optionList) {
        this.optionList = optionList;
    }
}
