package com.visionet.letsdesk.app.exhibition.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;
import com.visionet.letsdesk.app.base.entity.IdEntity;
import com.visionet.letsdesk.app.base.entity.JsonDateSerializer;
import com.visionet.letsdesk.app.common.cache.UserCache;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 展厅
 *
 * @author xt
 */
@Entity
@Table(name = "s_exhibition_survey")
public class ExhibitionSurvey extends IdEntity{

    private Integer categoryMain;                             //品类（大类）
    private Integer categoryFunction;                         //品类功能
    private Integer categoryMaterial;                         //品类材质
    private Integer categoryStyle;                            //品类风格
    private Integer categoryImport;                           //品类进口
    private Integer manufacturer;                             //品牌厂商
    private Integer brand;                             //品牌
    private Integer businessNature;                            //展厅经营性质(0:直营；其他：经销商 dealerId)
    private Integer standAloneStore;	                       //是否独立店面

    private Integer exhibitionProvince;                        //商场所在省／直辖市
    private Integer exhibitionCity;	                           //商场所在市／区
    private String exhibitionAddress;	                       //详细地址
    private String exhibitionBusinessHours;                    //营业时间
    private Integer exhibitionEntrance;                        //展厅入口数量
    private Integer exhibitionStatus;                          //展厅状态
    private List<Integer> outerWall = Lists.newArrayList();                                 //展厅外墙展示
    private List<Integer> brandPublicityType = Lists.newArrayList();                        //展厅品牌宣传形式
    private List<Integer> brandStained = Lists.newArrayList();                              //品牌是否存在不亮或有污损的情况
    private List<Integer> spokespersonImage = Lists.newArrayList();                         //代言人形象体现在
    private Integer promotionType2;                            //依据促销的形式划分
    private Integer promotionStyle2;                           //展厅促销物料主色调
    private Integer salesAvgPromotion;                         //展厅促销的平均折扣
    private List<Integer> seeOffGuest = Lists.newArrayList();                               //展厅是否送宾
    private Integer violationWords;                            //展厅内存在违规用语
    private Integer hasWifi;                                   //展厅是否有WIFI
    private List<Integer> customerPicWall = Lists.newArrayList();          //展厅是否有顾客照片墙
    private List<Integer> featureShow = Lists.newArrayList();                               //展厅是否有特色展示
    private Integer memberArea;                                //会员活动区域


    private Integer floor;                          //楼层
    private Integer floorPosition;                  //楼层位置
    private List<Integer> peripheryFacility = Lists.newArrayList();        //周边设施（10米以内）
    private Integer exhibitionArea;                 //展厅面积
    private List<Integer> hygiene = Lists.newArrayList();                  //卫生
    private Integer lighting;                       //灯光
    private Integer music;                          //音乐
    private Integer smell;                          //气味
    private Integer workbenchHygiene;               //工作台卫生
    private Integer workbenchStyle;                 //工作区风格
    private List<Integer> workbenchImage = Lists.newArrayList();                 //工作台形象
    private Integer discussionAreas;                //洽谈区
    private Integer backgroundWallHygiene;          //背景墙卫生
    private Integer designAreaHygiene;              //设计区卫生
    private Integer designAreaImage;                //设计区形象
    private Integer priceTag;                       //是否有价签
    private List<Integer> salesPromotionMaterials = Lists.newArrayList();  //促销物料
    private Integer shopEmployeesNumber;            //营业员数量
    private Integer designer;                       //有无设计师
    private List<Integer> shopEmployeesImage = Lists.newArrayList();       //人员形象
    private Integer welcomeGuest;                   //迎送宾客
    private List<Integer> productionIntroduce = Lists.newArrayList();            //产品介绍
    private Integer smileHello;                     //微笑问好
    private List<Integer> violations = Lists.newArrayList();               //违规行为
    private Integer gender;                         //性别
    private Integer age;                            //年龄
    private Integer exhibitVacant;                  //展品空置
    private Integer newProduction;                  //是否有新品
    private Integer guestInOut;                     //顾客进店情况
    private List<Integer> guestSnack = Lists.newArrayList();               //顾客零食
    private List<Integer> guestDrink = Lists.newArrayList();               //茶水
    private Integer promotionType;                  //依据促销的范围划分
    private List<Integer> promotionStyle = Lists.newArrayList();                 //展厅的主要促销形式
    private Integer specialOffer;                   //特价款


    private Integer hasPromotion;                   //是否有促销
    private Integer hasPublicShow;                  //是否有公区摆展

    private ExhibitionSurveyPublicShow publicShow;      //公区摆展
    private Map<String,String> otherOptionVo;           //其它填写

    private Long marketId;                  //商场ID
    private Long exhibitionId;              //展厅ID
    private Long createBy;                  //创建人
    private Long updateBy;                  //修改人
    private Date createDate;                //创建时间
    private Date updateDate;                //修改时间

    private Integer shortFlag;

    public Integer getBrand() {
        return brand;
    }

    public void setBrand(Integer brand) {
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

    @Transient
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

    public Integer getHasPromotion() {
        return hasPromotion;
    }

    public void setHasPromotion(Integer hasPromotion) {
        this.hasPromotion = hasPromotion;
    }

    public Integer getHasPublicShow() {
        return hasPublicShow;
    }

    public void setHasPublicShow(Integer hasPublicShow) {
        this.hasPublicShow = hasPublicShow;
    }

    @Transient
    public ExhibitionSurveyPublicShow getPublicShow() {
        return publicShow;
    }

    public void setPublicShow(ExhibitionSurveyPublicShow publicShow) {
        this.publicShow = publicShow;
    }

    @Transient
    public Map<String, String> getOtherOptionVo() {
        return otherOptionVo;
    }

    public void setOtherOptionVo(Map<String, String> otherOptionVo) {
        this.otherOptionVo = otherOptionVo;
    }

    @Transient
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

    public Integer getWorkbenchHygiene() {
        return workbenchHygiene;
    }

    public void setWorkbenchHygiene(Integer workbenchHygiene) {
        this.workbenchHygiene = workbenchHygiene;
    }

    public Integer getWorkbenchStyle() {
        return workbenchStyle;
    }

    public void setWorkbenchStyle(Integer workbenchStyle) {
        this.workbenchStyle = workbenchStyle;
    }

    @Transient
    public List<Integer> getWorkbenchImage() {
        return workbenchImage;
    }

    public void setWorkbenchImage(List<Integer> workbenchImage) {
        this.workbenchImage = workbenchImage;
    }

    public Integer getDiscussionAreas() {
        return discussionAreas;
    }

    public void setDiscussionAreas(Integer discussionAreas) {
        this.discussionAreas = discussionAreas;
    }

    public Integer getBackgroundWallHygiene() {
        return backgroundWallHygiene;
    }

    public void setBackgroundWallHygiene(Integer backgroundWallHygiene) {
        this.backgroundWallHygiene = backgroundWallHygiene;
    }

    public Integer getDesignAreaHygiene() {
        return designAreaHygiene;
    }

    public void setDesignAreaHygiene(Integer designAreaHygiene) {
        this.designAreaHygiene = designAreaHygiene;
    }

    public Integer getDesignAreaImage() {
        return designAreaImage;
    }

    public void setDesignAreaImage(Integer designAreaImage) {
        this.designAreaImage = designAreaImage;
    }

    public Integer getPriceTag() {
        return priceTag;
    }

    public void setPriceTag(Integer priceTag) {
        this.priceTag = priceTag;
    }

    @Transient
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

    @Transient
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

    @Transient
    public List<Integer> getProductionIntroduce() {
        return productionIntroduce;
    }

    public void setProductionIntroduce(List<Integer> productionIntroduce) {
        this.productionIntroduce = productionIntroduce;
    }

    public Integer getSmileHello() {
        return smileHello;
    }

    public void setSmileHello(Integer smileHello) {
        this.smileHello = smileHello;
    }

    @Transient
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

    @Transient
    public List<Integer> getGuestSnack() {
        return guestSnack;
    }

    public void setGuestSnack(List<Integer> guestSnack) {
        this.guestSnack = guestSnack;
    }

    @Transient
    public List<Integer> getGuestDrink() {
        return guestDrink;
    }

    public void setGuestDrink(List<Integer> guestDrink) {
        this.guestDrink = guestDrink;
    }

    public Integer getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(Integer promotionType) {
        this.promotionType = promotionType;
    }

    @Transient
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

    public Integer getCategoryMain() {
        return categoryMain;
    }

    public void setCategoryMain(Integer categoryMain) {
        this.categoryMain = categoryMain;
    }

    public Integer getCategoryFunction() {
        return categoryFunction;
    }

    public void setCategoryFunction(Integer categoryFunction) {
        this.categoryFunction = categoryFunction;
    }

    public Integer getCategoryMaterial() {
        return categoryMaterial;
    }

    public void setCategoryMaterial(Integer categoryMaterial) {
        this.categoryMaterial = categoryMaterial;
    }

    public Integer getCategoryStyle() {
        return categoryStyle;
    }

    public void setCategoryStyle(Integer categoryStyle) {
        this.categoryStyle = categoryStyle;
    }

    public Integer getCategoryImport() {
        return categoryImport;
    }

    public void setCategoryImport(Integer categoryImport) {
        this.categoryImport = categoryImport;
    }

    public Integer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Integer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getBusinessNature() {
        return businessNature;
    }

    public void setBusinessNature(Integer businessNature) {
        this.businessNature = businessNature;
    }

    public Integer getStandAloneStore() {
        return standAloneStore;
    }

    public void setStandAloneStore(Integer standAloneStore) {
        this.standAloneStore = standAloneStore;
    }

    public Integer getExhibitionProvince() {
        return exhibitionProvince;
    }

    public void setExhibitionProvince(Integer exhibitionProvince) {
        this.exhibitionProvince = exhibitionProvince;
    }

    public Integer getExhibitionCity() {
        return exhibitionCity;
    }

    public void setExhibitionCity(Integer exhibitionCity) {
        this.exhibitionCity = exhibitionCity;
    }

    public String getExhibitionAddress() {
        return exhibitionAddress;
    }

    public void setExhibitionAddress(String exhibitionAddress) {
        this.exhibitionAddress = exhibitionAddress;
    }

    public String getExhibitionBusinessHours() {
        return exhibitionBusinessHours;
    }

    public void setExhibitionBusinessHours(String exhibitionBusinessHours) {
        this.exhibitionBusinessHours = exhibitionBusinessHours;
    }

    public Integer getExhibitionEntrance() {
        return exhibitionEntrance;
    }

    public void setExhibitionEntrance(Integer exhibitionEntrance) {
        this.exhibitionEntrance = exhibitionEntrance;
    }

    public Integer getExhibitionStatus() {
        return exhibitionStatus;
    }

    public void setExhibitionStatus(Integer exhibitionStatus) {
        this.exhibitionStatus = exhibitionStatus;
    }

    @Transient
    public List<Integer> getOuterWall() {
        return outerWall;
    }

    public void setOuterWall(List<Integer> outerWall) {
        this.outerWall = outerWall;
    }

    @Transient
    public List<Integer> getBrandPublicityType() {
        return brandPublicityType;
    }

    public void setBrandPublicityType(List<Integer> brandPublicityType) {
        this.brandPublicityType = brandPublicityType;
    }

    @Transient
    public List<Integer> getBrandStained() {
        return brandStained;
    }

    public void setBrandStained(List<Integer> brandStained) {
        this.brandStained = brandStained;
    }

    @Transient
    public List<Integer> getSpokespersonImage() {
        return spokespersonImage;
    }

    public void setSpokespersonImage(List<Integer> spokespersonImage) {
        this.spokespersonImage = spokespersonImage;
    }

    public Integer getPromotionType2() {
        return promotionType2;
    }

    public void setPromotionType2(Integer promotionType2) {
        this.promotionType2 = promotionType2;
    }

    public Integer getPromotionStyle2() {
        return promotionStyle2;
    }

    public void setPromotionStyle2(Integer promotionStyle2) {
        this.promotionStyle2 = promotionStyle2;
    }

    public Integer getSalesAvgPromotion() {
        return salesAvgPromotion;
    }

    public void setSalesAvgPromotion(Integer salesAvgPromotion) {
        this.salesAvgPromotion = salesAvgPromotion;
    }

    @Transient
    public List<Integer> getSeeOffGuest() {
        return seeOffGuest;
    }

    public void setSeeOffGuest(List<Integer> seeOffGuest) {
        this.seeOffGuest = seeOffGuest;
    }

    public Integer getViolationWords() {
        return violationWords;
    }

    public void setViolationWords(Integer violationWords) {
        this.violationWords = violationWords;
    }

    public Integer getHasWifi() {
        return hasWifi;
    }

    public void setHasWifi(Integer hasWifi) {
        this.hasWifi = hasWifi;
    }

    @Transient
    public List<Integer> getCustomerPicWall() {
        return customerPicWall;
    }

    public void setCustomerPicWall(List<Integer> customerPicWall) {
        this.customerPicWall = customerPicWall;
    }

    @Transient
    public List<Integer> getFeatureShow() {
        return featureShow;
    }

    public void setFeatureShow(List<Integer> featureShow) {
        this.featureShow = featureShow;
    }

    public Integer getMemberArea() {
        return memberArea;
    }

    public void setMemberArea(Integer memberArea) {
        this.memberArea = memberArea;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public Long getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(Long exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    @Column(updatable=false)
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
    @Column(updatable=false)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Transient
    public Integer getShortFlag() {
        return shortFlag;
    }

    public void setShortFlag(Integer shortFlag) {
        this.shortFlag = shortFlag;
    }

    @Transient
    public String getCreateByName() {
        return UserCache.getUserName(this.createBy);
    }
}
