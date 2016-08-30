package com.visionet.letsdesk.app.exhibition.entity;


import com.visionet.letsdesk.app.base.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 展厅问卷多选值
 *
 * @author xt
 */
@Entity
@Table(name = "s_exhibition_survey_multiselect")
public class ExhibitionSurveyMultiselect extends IdEntity{

    private Long surveyId;
    private String surveyField;
    private Integer sundryId;

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public String getSurveyField() {
        return surveyField;
    }

    public void setSurveyField(String surveyField) {
        this.surveyField = surveyField;
    }

    public Integer getSundryId() {
        return sundryId;
    }

    public void setSundryId(Integer sundryId) {
        this.sundryId = sundryId;
    }
}
