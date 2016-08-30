package com.visionet.letsdesk.app.exhibition.service;

import com.google.common.collect.Lists;
import com.visionet.letsdesk.app.base.service.BaseService;
import com.visionet.letsdesk.app.common.constant.BusinessStatus;
import com.visionet.letsdesk.app.common.modules.time.DateUtil;
import com.visionet.letsdesk.app.common.modules.utils.Collections3;
import com.visionet.letsdesk.app.common.modules.validate.Validator;
import com.visionet.letsdesk.app.common.utils.BeanConvertMap;
import com.visionet.letsdesk.app.common.utils.PageInfo;
import com.visionet.letsdesk.app.common.utils.SearchFilterUtil;
import com.visionet.letsdesk.app.dictionary.entity.Sundry;
import com.visionet.letsdesk.app.dictionary.repository.SundryDao;
import com.visionet.letsdesk.app.exhibition.entity.Exhibition;
import com.visionet.letsdesk.app.exhibition.entity.ExhibitionSurvey;
import com.visionet.letsdesk.app.exhibition.entity.ExhibitionSurveyField;
import com.visionet.letsdesk.app.exhibition.entity.ExhibitionSurveyMultiselect;
import com.visionet.letsdesk.app.exhibition.repository.*;
import com.visionet.letsdesk.app.exhibition.vo.ExhibitionSurveyFieldVo;
import com.visionet.letsdesk.app.exhibition.vo.ExhibitionSurveyVo;
import com.visionet.letsdesk.app.foundation.KeyWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
    private ExhibitionDao exhibitionDao;
    @Autowired
    private SundryDao sundryDao;
    @Autowired
    private ExhibitionSurveyMultiselectDao exhibitionSurveyMultiselectDao;
    @Autowired
    private ExhibitionSurveyPublicShowDao exhibitionSurveyPublicShowDao;
    @Autowired
    private ExhibitionSurveyDaoImpl exhibitionSurveyDaoImpl;

    /**
     * 展厅问卷明细
     * @param id
     * @return
     */
    public ExhibitionSurveyVo findById(Long id) throws Exception{
        ExhibitionSurvey survey = exhibitionSurveyDao.findOne(id);
        if(survey==null){
            throwException(BusinessStatus.NOTFIND,"id not exist!");
        }
        ExhibitionSurveyVo vo = BeanConvertMap.map(survey,ExhibitionSurveyVo.class);
        this.transferExhibitionSurveyVo(vo);
        return vo;
    }

    private void transferExhibitionSurveyVo(ExhibitionSurveyVo vo) throws Exception{
        List<ExhibitionSurveyMultiselect> list = exhibitionSurveyMultiselectDao.findBySurveyId(vo.getId());
        for(ExhibitionSurveyMultiselect multiselect:list){
            if(multiselect.getSurveyField().equals("peripheryFacility")){
                vo.getPeripheryFacility().add(multiselect.getSundryId());
            }
            if(multiselect.getSurveyField().equals("publicAdType")){
                vo.getPublicAdType().add(multiselect.getSundryId());
            }
            if(multiselect.getSurveyField().equals("brandSponsorType")){
                vo.getBrandSponsorType().add(multiselect.getSundryId());
            }
            if(multiselect.getSurveyField().equals("hygiene")){
                vo.getHygiene().add(multiselect.getSundryId());
            }
            if(multiselect.getSurveyField().equals("workbenchHygiene")){
                vo.getWorkbenchHygiene().add(multiselect.getSundryId());
            }
            if(multiselect.getSurveyField().equals("discussionAreas")){
                vo.getDiscussionAreas().add(multiselect.getSundryId());
            }
            if(multiselect.getSurveyField().equals("backgroundWallHygiene")){
                vo.getBackgroundWallHygiene().add(multiselect.getSundryId());
            }
            if(multiselect.getSurveyField().equals("designAreaHygiene")){
                vo.getDesignAreaHygiene().add(multiselect.getSundryId());
            }
            if(multiselect.getSurveyField().equals("brandImagePlace")){
                vo.getBrandImagePlace().add(multiselect.getSundryId());
            }
            if(multiselect.getSurveyField().equals("salesPromotionMaterials")){
                vo.getSalesPromotionMaterials().add(multiselect.getSundryId());
            }
            if(multiselect.getSurveyField().equals("shopEmployeesImage")){
                vo.getShopEmployeesImage().add(multiselect.getSundryId());
            }
            if(multiselect.getSurveyField().equals("violations")){
                vo.getViolations().add(multiselect.getSundryId());
            }
            if(multiselect.getSurveyField().equals("guestSnack")){
                vo.getGuestSnack().add(multiselect.getSundryId());
            }
            if(multiselect.getSurveyField().equals("guestDrink")){
                vo.getGuestDrink().add(multiselect.getSundryId());
            }
            if(multiselect.getSurveyField().equals("promotionType")){
                vo.getPromotionType().add(multiselect.getSundryId());
            }
            if(multiselect.getSurveyField().equals("promotionStyle")){
                vo.getPromotionStyle().add(multiselect.getSundryId());
            }
        }
        vo.setPublicShowList(exhibitionSurveyPublicShowDao.findBySurveyId(vo.getId()));
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
            if(Validator.isNull(survey.getExhibitionId())){
                throwException(BusinessStatus.REQUIRE,"exhibitionId is null!");
            }
            Exhibition store = exhibitionDao.findOne(survey.getExhibitionId());
            if(store==null){
                throwException(BusinessStatus.NOTFIND,"exhibitionId not exist!");
            }
            survey.setBrand(store.getBrandId());
            survey.setCreateDate(DateUtil.getCurrentDate());
            exhibitionSurveyDao.save(survey);

            //多选项保存
            List<String> checkboxNameList = exhibitionSurveyFieldDao.findFieldNameByFieldFormatAndTableName
                    (KeyWord.FIELD_FORMAT_CHECKBOX, KeyWord.SURVEY_FIELD_TABLE_SURVEY);
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

    @Transactional(readOnly = false)
    public void delete(Long id){
        exhibitionSurveyDao.delete(id);
    }


    public Page<ExhibitionSurveyVo> search(ExhibitionSurveyVo query) throws Exception{
        PageInfo pageInfo = query.getPageInfo();
        if(pageInfo == null){
            pageInfo = new PageInfo();
        }

        List<String> checkboxNameList = exhibitionSurveyFieldDao.findFieldNameByFieldFormatAndTableName
                (KeyWord.FIELD_FORMAT_CHECKBOX, KeyWord.SURVEY_FIELD_TABLE_SURVEY);
        Page<ExhibitionSurvey> page = exhibitionSurveyDaoImpl.searchByCondition(query, checkboxNameList, pageInfo);

        List<ExhibitionSurveyVo> voList = Lists.newArrayList();
        for(ExhibitionSurvey survey:page.getContent()){
            ExhibitionSurveyVo vo = BeanConvertMap.map(survey,ExhibitionSurveyVo.class);
            this.transferExhibitionSurveyVo(vo);
            voList.add(vo);
        }
        return new PageImpl<ExhibitionSurveyVo>(voList, new PageRequest(page.getNumber(),
                page.getSize(), page.getSort()), page.getTotalElements());
    }




}
