package com.visionet.letsdesk.app.exhibition.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;
import com.visionet.letsdesk.app.base.entity.JsonDateSerializer;
import com.visionet.letsdesk.app.base.vo.BaseVo;
import com.visionet.letsdesk.app.common.cache.UserCache;
import com.visionet.letsdesk.app.exhibition.entity.ExhibitionSurveyPublicShow;

import java.util.Date;
import java.util.List;

public class ExhibitionSurveyVo extends BaseVo{

    private Long brand;                             //品牌
    private Integer floor;                          //楼层
    private Integer floorPosition;                  //楼层位置
    private List<Integer> peripheryFacility = Lists.newArrayList();        //周边设施（10米以内）
    private Integer exhibitionArea;                 //展厅面积
    private List<ExhibitionSurveyPublicShow> publicShowList = Lists.newArrayList();    //公区摆展
    private List<Integer> publicAdType = Lists.newArrayList();             //公区广告类型
    private List<Integer> brandSponsorType = Lists.newArrayList();         //品牌赞助类型
    private List<Integer> hygiene = Lists.newArrayList();                  //卫生
    private Integer lighting;                       //灯光
    private Integer music;                          //音乐
    private Integer smell;                          //气味
    private List<Integer> workbenchHygiene = Lists.newArrayList();         //工作台卫生
    private Integer workbenchImage;                 //工作台形象
    private List<Integer> discussionAreas = Lists.newArrayList();          //洽谈区
    private List<Integer> backgroundWallHygiene = Lists.newArrayList();    //背景墙卫生
    private List<Integer> designAreaHygiene = Lists.newArrayList();        //设计区卫生
    private Integer designAreaImage;                //设计区形象
    private List<Integer> brandImagePlace = Lists.newArrayList();          //品牌形象位置
    private List<Integer> salesPromotionMaterials = Lists.newArrayList();  //促销物料
    private Integer shopEmployeesNumber;            //营业员数量
    private Integer designer;                       //有无设计师
    private List<Integer> shopEmployeesImage = Lists.newArrayList();       //人员形象
    private Integer welcomeGuest;                   //迎送宾客
    private Integer productionIntroduce;            //产品介绍
    private Integer smileHello;                     //微笑问好
    private List<Integer> violations = Lists.newArrayList();               //违规行为
    private Integer gender;                         //性别
    private Integer age;                            //年龄
    private Integer exhibitVacant;                  //展品空置
    private Integer newProduction;                  //是否有新品
    private Integer guestInOut;                     //顾客进店情况
    private List<Integer> guestSnack = Lists.newArrayList();               //顾客零食
    private List<Integer> guestDrink = Lists.newArrayList();               //茶水
    private List<Integer> promotionType = Lists.newArrayList();            //促销
    private List<Integer> promotionStyle = Lists.newArrayList();           //促销形式
    private Integer specialOffer;                   //特价款

    private Long exhibitionId;              //展厅ID
    private Long createBy;                  //创建人
    private Long updateBy;                  //修改人
    private Date createDate;                //创建时间
    private Date updateDate;                //修改时间

    /*----------VO------------*/
    private String queryBeginDate;
    private String queryEndDate;

    public Long getBrand() {
        return brand;
    }

    public void setBrand(Long brand) {
        this.brand = brand;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getFloorPosition() {
        return floorPosition;
    }

    public void setFloorPosition(Integer floorPosition) {
        this.floorPosition = floorPosition;
    }

    public List<Integer> getPeripheryFacility() {
        return peripheryFacility;
    }

    public void setPeripheryFacility(List<Integer> peripheryFacility) {
        this.peripheryFacility = peripheryFacility;
    }

    public Integer getExhibitionArea() {
        return exhibitionArea;
    }

    public void setExhibitionArea(Integer exhibitionArea) {
        this.exhibitionArea = exhibitionArea;
    }

    public List<ExhibitionSurveyPublicShow> getPublicShowList() {
        return publicShowList;
    }

    public void setPublicShowList(List<ExhibitionSurveyPublicShow> publicShowList) {
        this.publicShowList = publicShowList;
    }

    public List<Integer> getPublicAdType() {
        return publicAdType;
    }

    public void setPublicAdType(List<Integer> publicAdType) {
        this.publicAdType = publicAdType;
    }

    public List<Integer> getBrandSponsorType() {
        return brandSponsorType;
    }

    public void setBrandSponsorType(List<Integer> brandSponsorType) {
        this.brandSponsorType = brandSponsorType;
    }

    public List<Integer> getHygiene() {
        return hygiene;
    }

    public void setHygiene(List<Integer> hygiene) {
        this.hygiene = hygiene;
    }

    public Integer getLighting() {
        return lighting;
    }

    public void setLighting(Integer lighting) {
        this.lighting = lighting;
    }

    public Integer getMusic() {
        return music;
    }

    public void setMusic(Integer music) {
        this.music = music;
    }

    public Integer getSmell() {
        return smell;
    }

    public void setSmell(Integer smell) {
        this.smell = smell;
    }

    public List<Integer> getWorkbenchHygiene() {
        return workbenchHygiene;
    }

    public void setWorkbenchHygiene(List<Integer> workbenchHygiene) {
        this.workbenchHygiene = workbenchHygiene;
    }

    public Integer getWorkbenchImage() {
        return workbenchImage;
    }

    public void setWorkbenchImage(Integer workbenchImage) {
        this.workbenchImage = workbenchImage;
    }

    public List<Integer> getDiscussionAreas() {
        return discussionAreas;
    }

    public void setDiscussionAreas(List<Integer> discussionAreas) {
        this.discussionAreas = discussionAreas;
    }

    public List<Integer> getBackgroundWallHygiene() {
        return backgroundWallHygiene;
    }

    public void setBackgroundWallHygiene(List<Integer> backgroundWallHygiene) {
        this.backgroundWallHygiene = backgroundWallHygiene;
    }

    public List<Integer> getDesignAreaHygiene() {
        return designAreaHygiene;
    }

    public void setDesignAreaHygiene(List<Integer> designAreaHygiene) {
        this.designAreaHygiene = designAreaHygiene;
    }

    public Integer getDesignAreaImage() {
        return designAreaImage;
    }

    public void setDesignAreaImage(Integer designAreaImage) {
        this.designAreaImage = designAreaImage;
    }

    public List<Integer> getBrandImagePlace() {
        return brandImagePlace;
    }

    public void setBrandImagePlace(List<Integer> brandImagePlace) {
        this.brandImagePlace = brandImagePlace;
    }

    public List<Integer> getSalesPromotionMaterials() {
        return salesPromotionMaterials;
    }

    public void setSalesPromotionMaterials(List<Integer> salesPromotionMaterials) {
        this.salesPromotionMaterials = salesPromotionMaterials;
    }

    public Integer getShopEmployeesNumber() {
        return shopEmployeesNumber;
    }

    public void setShopEmployeesNumber(Integer shopEmployeesNumber) {
        this.shopEmployeesNumber = shopEmployeesNumber;
    }

    public Integer getDesigner() {
        return designer;
    }

    public void setDesigner(Integer designer) {
        this.designer = designer;
    }

    public List<Integer> getShopEmployeesImage() {
        return shopEmployeesImage;
    }

    public void setShopEmployeesImage(List<Integer> shopEmployeesImage) {
        this.shopEmployeesImage = shopEmployeesImage;
    }

    public Integer getWelcomeGuest() {
        return welcomeGuest;
    }

    public void setWelcomeGuest(Integer welcomeGuest) {
        this.welcomeGuest = welcomeGuest;
    }

    public Integer getProductionIntroduce() {
        return productionIntroduce;
    }

    public void setProductionIntroduce(Integer productionIntroduce) {
        this.productionIntroduce = productionIntroduce;
    }

    public Integer getSmileHello() {
        return smileHello;
    }

    public void setSmileHello(Integer smileHello) {
        this.smileHello = smileHello;
    }

    public List<Integer> getViolations() {
        return violations;
    }

    public void setViolations(List<Integer> violations) {
        this.violations = violations;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getExhibitVacant() {
        return exhibitVacant;
    }

    public void setExhibitVacant(Integer exhibitVacant) {
        this.exhibitVacant = exhibitVacant;
    }

    public Integer getNewProduction() {
        return newProduction;
    }

    public void setNewProduction(Integer newProduction) {
        this.newProduction = newProduction;
    }

    public Integer getGuestInOut() {
        return guestInOut;
    }

    public void setGuestInOut(Integer guestInOut) {
        this.guestInOut = guestInOut;
    }

    public List<Integer> getGuestSnack() {
        return guestSnack;
    }

    public void setGuestSnack(List<Integer> guestSnack) {
        this.guestSnack = guestSnack;
    }

    public List<Integer> getGuestDrink() {
        return guestDrink;
    }

    public void setGuestDrink(List<Integer> guestDrink) {
        this.guestDrink = guestDrink;
    }

    public List<Integer> getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(List<Integer> promotionType) {
        this.promotionType = promotionType;
    }

    public List<Integer> getPromotionStyle() {
        return promotionStyle;
    }

    public void setPromotionStyle(List<Integer> promotionStyle) {
        this.promotionStyle = promotionStyle;
    }

    public Integer getSpecialOffer() {
        return specialOffer;
    }

    public void setSpecialOffer(Integer specialOffer) {
        this.specialOffer = specialOffer;
    }

    public Long getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(Long exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getQueryBeginDate() {
        return queryBeginDate;
    }

    public void setQueryBeginDate(String queryBeginDate) {
        this.queryBeginDate = queryBeginDate;
    }

    public String getQueryEndDate() {
        return queryEndDate;
    }

    public void setQueryEndDate(String queryEndDate) {
        this.queryEndDate = queryEndDate;
    }

    public String getCreateByName() {
        return UserCache.getUserName(this.createBy);
    }
}
