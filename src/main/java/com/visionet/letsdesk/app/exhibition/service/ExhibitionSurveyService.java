package com.visionet.letsdesk.app.exhibition.service;

import com.google.common.collect.Lists;
import com.visionet.letsdesk.app.base.service.BaseService;
import com.visionet.letsdesk.app.common.constant.BusinessStatus;
import com.visionet.letsdesk.app.common.modules.time.DateUtil;
import com.visionet.letsdesk.app.common.modules.utils.Collections3;
import com.visionet.letsdesk.app.common.modules.validate.Validator;
import com.visionet.letsdesk.app.common.utils.BeanConvertMap;
import com.visionet.letsdesk.app.common.utils.SearchFilterUtil;
import com.visionet.letsdesk.app.dictionary.entity.Sundry;
import com.visionet.letsdesk.app.dictionary.repository.SundryDao;
import com.visionet.letsdesk.app.exhibition.entity.ExhibitionSurvey;
import com.visionet.letsdesk.app.exhibition.entity.ExhibitionSurveyField;
import com.visionet.letsdesk.app.exhibition.entity.ExhibitionSurveyMultiselect;
import com.visionet.letsdesk.app.exhibition.repository.ExhibitionSurveyDao;
import com.visionet.letsdesk.app.exhibition.repository.ExhibitionSurveyFieldDao;
import com.visionet.letsdesk.app.exhibition.repository.ExhibitionSurveyMultiselectDao;
import com.visionet.letsdesk.app.exhibition.vo.ExhibitionSurveyFieldVo;
import com.visionet.letsdesk.app.foundation.KeyWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;

@Service
public class ExhibitionSurveyService extends BaseService{

    @Autowired
    private ExhibitionSurveyDao exhibitionSurveyDao;
    @Autowired
    private ExhibitionSurveyFieldDao exhibitionSurveyFieldDao;
    @Autowired
    private SundryDao sundryDao;
    @Autowired
    private ExhibitionSurveyMultiselectDao exhibitionSurveyMultiselectDao;

    /**
     * 展厅问卷明细
     * @param id
     * @return
     */
    public ExhibitionSurvey findById(Long id){
        return exhibitionSurveyDao.findOne(id);
    }

    /**
     * 展厅问卷字段项
     * @return
     */
    public List<ExhibitionSurveyFieldVo> findExhibitionField(){
        List<ExhibitionSurveyFieldVo> voList = Lists.newArrayList();
        List<ExhibitionSurveyField> fieldList = exhibitionSurveyFieldDao.findByTableName(KeyWord.SURVEY_FIELD_TABLE_BRAND);
        for(ExhibitionSurveyField field:fieldList){
            ExhibitionSurveyFieldVo vo = BeanConvertMap.map(field,ExhibitionSurveyFieldVo.class);
            vo.setOptionList(this.findSundry(vo.getFieldName()));
            voList.add(vo);
        }
        return voList;
    }

    /**
     * 展厅问卷某字段选项说明
     * @param type 字段名
     * @return
     */
    public List<Sundry> findSundry(String type){
        return sundryDao.findByType(type);
    }

    /**
     * 展厅问卷保存
     * @param survey
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public void save(ExhibitionSurvey survey) throws Exception{
        if(survey.getId()==null){
            if(Validator.isNull(survey.getBrand())){
                throwException(BusinessStatus.REQUIRE,"brand is null!");
            }
            survey.setCreateDate(DateUtil.getCurrentDate());
            exhibitionSurveyDao.save(survey);

            //多选项保存
            List<String> checkboxNameList = exhibitionSurveyFieldDao.findByFieldFormatAndTableName
                    (KeyWord.FIELD_FORMAT_CHECKBOX,KeyWord.SURVEY_FIELD_TABLE_SURVEY);
            BeanInfo beanInfo = Introspector.getBeanInfo(survey.getClass());
            PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
            for (int i = 0; i< propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if(!checkboxNameList.contains(propertyName)) continue;

                Method readMethod = descriptor.getReadMethod();
                if(readMethod!=null) {
                    Object result = readMethod.invoke(survey, new Object[0]);
                    if (result != null) {
                        if (result instanceof List) {
                            List<Integer> list = (List<Integer>)result;
                            if(Collections3.isNotEmpty(list)){
                                this.multiselectSave(survey.getId(),propertyName,list);
                            }
                        }
                    }
                }
            }

/*
            if(Collections3.isNotEmpty(survey.getPeripheryFacility())){
                this.multiselectSave(survey.getId(),"peripheryFacility",survey.getPeripheryFacility());
            }
            if(Collections3.isNotEmpty(survey.getPublicAdType())){
                this.multiselectSave(survey.getId(),"publicAdType",survey.getPublicAdType());
            }
            if(Collections3.isNotEmpty(survey.getBrandSponsorType())){
                this.multiselectSave(survey.getId(),"brandSponsorType",survey.getBrandSponsorType());
            }
            if(Collections3.isNotEmpty(survey.getHygiene())){
                this.multiselectSave(survey.getId(),"hygiene",survey.getHygiene());
            }
            if(Collections3.isNotEmpty(survey.getWorkbenchHygiene())){
                this.multiselectSave(survey.getId(),"workbenchHygiene",survey.getWorkbenchHygiene());
            }
            if(Collections3.isNotEmpty(survey.getDiscussionAreas())){
                this.multiselectSave(survey.getId(),"discussionAreas",survey.getDiscussionAreas());
            }
            if(Collections3.isNotEmpty(survey.getBackgroundWallHygiene())){
                this.multiselectSave(survey.getId(),"backgroundWallHygiene",survey.getBackgroundWallHygiene());
            }
            if(Collections3.isNotEmpty(survey.getDesignAreaHygiene())){
                this.multiselectSave(survey.getId(),"designAreaHygiene",survey.getDesignAreaHygiene());
            }
            if(Collections3.isNotEmpty(survey.getBrandImagePlace())){
                this.multiselectSave(survey.getId(),"brandImagePlace",survey.getBrandImagePlace());
            }
            if(Collections3.isNotEmpty(survey.getSalesPromotionMaterials())){
                this.multiselectSave(survey.getId(),"salesPromotionMaterials",survey.getSalesPromotionMaterials());
            }
            if(Collections3.isNotEmpty(survey.getShopEmployeesImage())){
                this.multiselectSave(survey.getId(),"shopEmployeesImage",survey.getShopEmployeesImage());
            }
            if(Collections3.isNotEmpty(survey.getGuestSnack())){
                this.multiselectSave(survey.getId(),"guestSnack",survey.getGuestSnack());
            }
            if(Collections3.isNotEmpty(survey.getGuestDrink())){
                this.multiselectSave(survey.getId(),"guestDrink",survey.getGuestDrink());
            }
            if(Collections3.isNotEmpty(survey.getPromotionType())){
                this.multiselectSave(survey.getId(),"promotionType",survey.getPromotionType());
            }
            if(Collections3.isNotEmpty(survey.getPromotionStyle())){
                this.multiselectSave(survey.getId(),"promotionStyle",survey.getPromotionStyle());
            }
*/
        }else{
            ExhibitionSurvey po = exhibitionSurveyDao.findOne(survey.getId());
            SearchFilterUtil.copyBeans(po, survey);
            po.setUpdateDate(DateUtil.getCurrentDate());
            exhibitionSurveyDao.save(po);
        }
    }

    @Transactional(readOnly = false)
    private void multiselectSave(Long surveyId,String field,List<Integer> sundryIdList){
        for(Integer sundryId:sundryIdList) {
            ExhibitionSurveyMultiselect multiselect = new ExhibitionSurveyMultiselect();
            multiselect.setSurveyId(surveyId);
            multiselect.setSurveyField(field);
            multiselect.setSundryId(sundryId);
            exhibitionSurveyMultiselectDao.save(multiselect);
        }
    }


}
