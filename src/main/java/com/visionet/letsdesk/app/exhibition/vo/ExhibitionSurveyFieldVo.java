package com.visionet.letsdesk.app.exhibition.vo;

import com.visionet.letsdesk.app.dictionary.entity.Sundry;

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
    private List<Sundry> optionList;

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

    public List<Sundry> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<Sundry> optionList) {
        this.optionList = optionList;
    }
}
