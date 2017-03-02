package com.visionet.letsdesk.app.exhibition.entity;

import com.visionet.letsdesk.app.base.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "s_exhibition_survey_other_option")
public class ExhibitionSurveyOtherOption extends IdEntity{

    private Long surveyId;
    private String surveyField;
    private String otherOption;

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

    public String getOtherOption() {
        return otherOption;
    }

    public void setOtherOption(String otherOption) {
        this.otherOption = otherOption;
    }
}
