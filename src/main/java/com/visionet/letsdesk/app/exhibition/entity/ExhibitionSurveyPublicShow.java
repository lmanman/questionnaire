package com.visionet.letsdesk.app.exhibition.entity;

import com.google.common.collect.Lists;
import com.visionet.letsdesk.app.base.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * 公区摆展
 *
 * @author xt
 */
@Entity
@Table(name = "s_exhibition_survey_public_show")
public class ExhibitionSurveyPublicShow extends IdEntity{

    private Long surveyId;
    private Integer publicExhibitionPriceTag;                               //公区摆展是否有有价签
    private Integer publicExhibitionArea;                                   //公区摆展面积
    private Integer publicExhibitionFloor;                                  //公区摆展楼层
    private Integer publicExhibitionPlace;
    private List<Integer> publicAdType = Lists.newArrayList();             //公区广告类型
    private List<Integer> brandSponsorType = Lists.newArrayList();         //品牌赞助类型

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


    @Transient
    public List<Integer> getPublicAdType() {
        return publicAdType;
    }

    public void setPublicAdType(List<Integer> publicAdType) {
        this.publicAdType = publicAdType;
    }

    @Transient
    public List<Integer> getBrandSponsorType() {
        return brandSponsorType;
    }

    public void setBrandSponsorType(List<Integer> brandSponsorType) {
        this.brandSponsorType = brandSponsorType;
    }

}
