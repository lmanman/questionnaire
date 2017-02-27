package com.visionet.letsdesk.app.exhibition.vo;

import com.visionet.letsdesk.app.base.vo.BaseVo;
import com.visionet.letsdesk.app.common.cache.UserCache;

import java.util.Date;

public class ExhibitionSurveyListVo extends BaseVo {

    private Long surveyId;
    private String exhibitionName;
    private String address;
    private String dealerName;
    private String categoryName;
    private String brandName;
    private String cityName;
    private int[] schedule;
    private Long createBy;                  //创建人
    private Date updateDate;


    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public String getExhibitionName() {
        return exhibitionName;
    }

    public void setExhibitionName(String exhibitionName) {
        this.exhibitionName = exhibitionName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int[] getSchedule() {
        return schedule;
    }

    public void setSchedule(int[] schedule) {
        this.schedule = schedule;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public String getUserName() {
        return UserCache.getUserName(this.createBy);
    }

}
