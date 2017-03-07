package com.visionet.letsdesk.app.exhibition.vo;

import com.google.common.collect.Maps;
import com.visionet.letsdesk.app.attachment.vo.PhotoVo;
import com.visionet.letsdesk.app.dictionary.vo.SundryVo;

import java.util.List;
import java.util.Map;

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
    private Integer shortFlag;
    private boolean hideFlag = false;
    private List<SundryVo> optionList;
    private String fieldVal;
    private Map<Long,Boolean> fieldArr = Maps.newHashMap();
    private Map<String,String> otherOptionVo = Maps.newHashMap();       //其它填写
    private Map<String,List<PhotoVo>> photoListVo= Maps.newHashMap();   //每项图片数量


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

    public Integer getShortFlag() {
        return shortFlag;
    }

    public void setShortFlag(Integer shortFlag) {
        this.shortFlag = shortFlag;
    }

    public boolean isHideFlag() {
        return hideFlag;
    }

    public void setHideFlag(boolean hideFlag) {
        this.hideFlag = hideFlag;
    }

    public List<SundryVo> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<SundryVo> optionList) {
        this.optionList = optionList;
    }

    public String getFieldVal() {
        return fieldVal;
    }

    public void setFieldVal(String fieldVal) {
        this.fieldVal = fieldVal;
    }

    public Map<Long, Boolean> getFieldArr() {
        return fieldArr;
    }

    public void setFieldArr(Map<Long, Boolean> fieldArr) {
        this.fieldArr = fieldArr;
    }

    public Map<String, String> getOtherOptionVo() {
        return otherOptionVo;
    }

    public void setOtherOptionVo(Map<String, String> otherOptionVo) {
        this.otherOptionVo = otherOptionVo;
    }

    public Map<String, List<PhotoVo>> getPhotoListVo() {
        return photoListVo;
    }

    public void setPhotoListVo(Map<String, List<PhotoVo>> photoListVo) {
        this.photoListVo = photoListVo;
    }
}
