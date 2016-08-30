package com.visionet.letsdesk.app.exhibition.entity;

import com.visionet.letsdesk.app.base.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 公区摆展
 *
 * @author xt
 */
@Entity
@Table(name = "s_exhibition_survey_public_show")
public class ExhibitionSurveyPublicShow extends IdEntity{

    private Long surveyId;
    private Integer publicExhibitionPriceTag;
    private Integer publicExhibitionArea;
    private Integer publicExhibitionFloor;
    private Integer publicExhibitionPlace;

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public Integer getPublicExhibitionPriceTag() {
        return publicExhibitionPriceTag;
    }

    public void setPublicExhibitionPriceTag(Integer publicExhibitionPriceTag) {
        this.publicExhibitionPriceTag = publicExhibitionPriceTag;
    }

    public Integer getPublicExhibitionArea() {
        return publicExhibitionArea;
    }

    public void setPublicExhibitionArea(Integer publicExhibitionArea) {
        this.publicExhibitionArea = publicExhibitionArea;
    }

    public Integer getPublicExhibitionFloor() {
        return publicExhibitionFloor;
    }

    public void setPublicExhibitionFloor(Integer publicExhibitionFloor) {
        this.publicExhibitionFloor = publicExhibitionFloor;
    }

    public Integer getPublicExhibitionPlace() {
        return publicExhibitionPlace;
    }

    public void setPublicExhibitionPlace(Integer publicExhibitionPlace) {
        this.publicExhibitionPlace = publicExhibitionPlace;
    }
}
