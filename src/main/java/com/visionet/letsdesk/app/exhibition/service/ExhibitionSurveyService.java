package com.visionet.letsdesk.app.exhibition.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.visionet.letsdesk.app.base.service.BaseService;
import com.visionet.letsdesk.app.common.constant.BusinessStatus;
import com.visionet.letsdesk.app.common.modules.time.DateUtil;
import com.visionet.letsdesk.app.common.modules.utils.Collections3;
import com.visionet.letsdesk.app.common.modules.validate.Validator;
import com.visionet.letsdesk.app.common.utils.BeanConvertMap;
import com.visionet.letsdesk.app.common.utils.PageInfo;
import com.visionet.letsdesk.app.common.utils.SearchFilterUtil;
import com.visionet.letsdesk.app.dictionary.entity.City;
import com.visionet.letsdesk.app.dictionary.repository.BrandDao;
import com.visionet.letsdesk.app.dictionary.repository.CategoryDao;
import com.visionet.letsdesk.app.dictionary.repository.CityDao;
import com.visionet.letsdesk.app.exhibition.entity.ExhibitionSurvey;
import com.visionet.letsdesk.app.exhibition.entity.ExhibitionSurveyMultiselect;
import com.visionet.letsdesk.app.exhibition.entity.ExhibitionSurveyOtherOption;
import com.visionet.letsdesk.app.exhibition.entity.ExhibitionSurveyPublicShow;
import com.visionet.letsdesk.app.exhibition.repository.*;
import com.visionet.letsdesk.app.exhibition.vo.ExhibitionSurveyListVo;
import com.visionet.letsdesk.app.exhibition.vo.ExhibitionSurveyVo;
import com.visionet.letsdesk.app.foundation.KeyWord;
import com.visionet.letsdesk.app.market.repository.MarketDao;
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
import java.util.Map;

@Service
public class ExhibitionSurveyService extends BaseService{

    @Autowired
    private ExhibitionSurveyDao exhibitionSurveyDao;
    @Autowired
    private ExhibitionSurveyFieldDao exhibitionSurveyFieldDao;
    @Autowired
    private ExhibitionSurveyMultiselectDao exhibitionSurveyMultiselectDao;
    @Autowired
    private ExhibitionSurveyOtherOptionDao exhibitionSurveyOtherOptionDao;
    @Autowired
    private ExhibitionSurveyPublicShowDao exhibitionSurveyPublicShowDao;
    @Autowired
    private ExhibitionSurveyDaoImpl exhibitionSurveyDaoImpl;
    @Autowired
    private ExhibitionDao exhibitionDao;
    @Autowired
    private MarketDao marketDao;
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private DealerDao dealerDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private CityDao cityDao;

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
    public ExhibitionSurveyVo findShortById(Long id) throws Exception{
        ExhibitionSurvey survey = exhibitionSurveyDao.findOne(id);
        if(survey==null){
            throwException(BusinessStatus.NOTFIND,"id not exist!");
        }
        ExhibitionSurveyVo vo = BeanConvertMap.map(survey,ExhibitionSurveyVo.class);
        this.transferExhibitionSurveyShortVo(vo);
        return vo;
    }

    private void transferExhibitionSurveyVo(ExhibitionSurveyVo vo) throws Exception{
        vo.setPublicShow(exhibitionSurveyPublicShowDao.findBySurveyId(vo.getId()));
        List<ExhibitionSurveyMultiselect> list = exhibitionSurveyMultiselectDao.findBySurveyId(vo.getId());
        for(ExhibitionSurveyMultiselect multiselect:list){
            if(multiselect.getSurveyField().equals("peripheryFacility")){
                vo.getPeripheryFacility().add(multiselect.getSundryId());
            }
            if(multiselect.getSurveyField().equals("hygiene")){
                vo.getHygiene().add(multiselect.getSundryId());
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
            if(multiselect.getSurveyField().equals("customerPicWall")){
                vo.getCustomerPicWall().add(multiselect.getSundryId());
            }
            if(vo.getPublicShow()!=null){
                if(multiselect.getSurveyField().equals("publicAdType")){
                    vo.getPublicShow().getPublicAdType().add(multiselect.getSundryId());
                }
                if(multiselect.getSurveyField().equals("brandSponsorType")){
                    vo.getPublicShow().getBrandSponsorType().add(multiselect.getSundryId());
                }
            }
        }

        List<ExhibitionSurveyOtherOption> otherList = exhibitionSurveyOtherOptionDao.findBySurveyId(vo.getId());
        Map<String,String> otherMap = Maps.newHashMap();
        if(Collections3.isNotEmpty(otherList)){
            otherList.parallelStream().forEach(o -> otherMap.put(o.getSurveyField(), o.getOtherOption()));
        }
        vo.setOtherOptionVo(otherMap);
    }

    private void transferExhibitionSurveyShortVo(ExhibitionSurveyVo vo) throws Exception{
        List<ExhibitionSurveyMultiselect> list = exhibitionSurveyMultiselectDao.findByShortSurveyId(vo.getId());
        for(ExhibitionSurveyMultiselect multiselect:list){
            if(multiselect.getSurveyField().equals("peripheryFacility")){
                vo.getPeripheryFacility().add(multiselect.getSundryId());
            }
            if(multiselect.getSurveyField().equals("hygiene")){
                vo.getHygiene().add(multiselect.getSundryId());
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
            if(multiselect.getSurveyField().equals("customerPicWall")){
                vo.getCustomerPicWall().add(multiselect.getSundryId());
            }
            if(vo.getPublicShow()!=null){
                if(multiselect.getSurveyField().equals("publicAdType")){
                    vo.getPublicShow().getPublicAdType().add(multiselect.getSundryId());
                }
                if(multiselect.getSurveyField().equals("brandSponsorType")){
                    vo.getPublicShow().getBrandSponsorType().add(multiselect.getSundryId());
                }
            }
        }

        List<ExhibitionSurveyOtherOption> otherList = exhibitionSurveyOtherOptionDao.findByShortSurveyId(vo.getId());
        Map<String,String> otherMap = Maps.newHashMap();
        if(Collections3.isNotEmpty(otherList)){
            otherList.parallelStream().forEach(o -> otherMap.put(o.getSurveyField(), o.getOtherOption()));
        }
        vo.setOtherOptionVo(otherMap);
    }


    /**
     * 展厅问卷保存
     * @param survey
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public void save(ExhibitionSurvey survey) throws Exception{
        if(Validator.isNull(survey.getExhibitionId())){
            throwException(BusinessStatus.REQUIRE,"exhibitionId is null!");
        }

        if(Validator.isNull(survey.getId())){
            if(survey.getId()!=null && survey.getId().longValue()==0){
                survey.setId(null);
            }

//            Exhibition store = exhibitionDao.findOne(survey.getExhibitionId());
//            if(store==null){
//                throwException(BusinessStatus.NOTFIND,"exhibitionId not exist!");
//            }

            survey.setExhibitionProvince(this.getProvinceId(survey.getExhibitionCity()));
            survey.setCreateDate(DateUtil.getCurrentDate());
            survey.setUpdateDate(DateUtil.getCurrentDate());
            exhibitionSurveyDao.save(survey);

            //公区
            ExhibitionSurveyPublicShow publicShow = survey.getPublicShow();
            if(publicShow!=null) {
                publicShow.setSurveyId(survey.getId());
                exhibitionSurveyPublicShowDao.save(publicShow);
            }
            //多选项保存
            this.multiselectSave(survey);

            //其它填写项保存
            this.otherOptionSave(survey.getId(),survey.getOtherOptionVo());

        }else if(survey.getShortFlag()!=null && survey.getShortFlag().intValue() == KeyWord.Y) {    //短问卷修改
            ExhibitionSurvey po = exhibitionSurveyDao.findOne(survey.getId());
            if(po.getExhibitionCity()==null ||
                    (Validator.isNotNull(survey.getExhibitionCity())
                            && survey.getExhibitionCity().intValue()!=po.getExhibitionCity().intValue())){
                po.setExhibitionProvince(this.getProvinceId(survey.getExhibitionCity()));
            }
            SearchFilterUtil.copyBeans(po, survey);

            survey.setExhibitionProvince(this.getProvinceId(survey.getExhibitionCity()));
            survey.setUpdateDate(DateUtil.getCurrentDate());
            exhibitionSurveyDao.save(survey);


            //多选项修改
            this.shortMultiselectSave(survey);
            //其它填写项修改
            this.shortOtherOptionSave(survey.getId(), survey.getOtherOptionVo());
        }else{

            survey.setExhibitionProvince(this.getProvinceId(survey.getExhibitionCity()));
            survey.setUpdateDate(DateUtil.getCurrentDate());
            exhibitionSurveyDao.save(survey);

            //公区
            ExhibitionSurveyPublicShow poShow =exhibitionSurveyPublicShowDao.findBySurveyId(survey.getId());
            if(poShow!=null){
                if(survey.getPublicShow()!=null) {
                    survey.getPublicShow().setId(poShow.getId());
                    exhibitionSurveyPublicShowDao.save(survey.getPublicShow());
                }else {
                    exhibitionSurveyPublicShowDao.delete(poShow);
                }
            }else {
                survey.getPublicShow().setSurveyId(survey.getId());
                exhibitionSurveyPublicShowDao.save(survey.getPublicShow());
            }

            //多选项保存
            exhibitionSurveyMultiselectDao.deleteBySurveyId(survey.getId());
            this.multiselectSave(survey);

            //其它填写项保存
            exhibitionSurveyOtherOptionDao.deleteBySurveyId(survey.getId());
            this.otherOptionSave(survey.getId(), survey.getOtherOptionVo());

        }
    }

    public Integer getProvinceId(Integer exhibitionCity){
        if(Validator.isNotNull(exhibitionCity)){
            City city = cityDao.findOne(exhibitionCity.longValue());
            if(city!=null) {
                return city.getProvinceId().intValue();
            }
        }
        return null;
    }

    private void multiselectSave(ExhibitionSurvey survey) throws Exception{

        List<String> checkboxNameList = exhibitionSurveyFieldDao.findFieldNameByFieldFormat
                (KeyWord.FIELD_FORMAT_CHECKBOX);
        //普通多选
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
        if(survey.getPublicShow()!=null) {
            //公区多选
            BeanInfo beanInfo2 = Introspector.getBeanInfo(survey.getPublicShow().getClass());
            PropertyDescriptor[] propertyDescriptors2 = beanInfo2.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors2.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors2[i];
                String propertyName = descriptor.getName();
                if (!checkboxNameList.contains(propertyName)) continue;

                Method readMethod = descriptor.getReadMethod();
                if (readMethod != null) {
                    Object result = readMethod.invoke(survey.getPublicShow(), new Object[0]);
                    if (result != null) {
                        if (result instanceof List) {
                            List<Integer> list = (List<Integer>) result;
                            if (Collections3.isNotEmpty(list)) {
                                this.multiselectSave(survey.getId(), propertyName, list);
                            }
                        }
                    }
                }
            }
        }
    }

    private void shortMultiselectSave(ExhibitionSurvey survey) throws Exception{
        List<String> checkboxNameList = exhibitionSurveyFieldDao.findFieldNameByFieldFormat
                (KeyWord.FIELD_FORMAT_CHECKBOX,KeyWord.Y);
        //普通多选
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
                            exhibitionSurveyMultiselectDao.deleteBySurveyIdAndField(survey.getId(), propertyName);
                            this.multiselectSave(survey.getId(),propertyName,list);
                        }
                    }
                }
            }
        }
    }

    @Transactional(readOnly = false)
    private void otherOptionSave(Long surveyId,Map<String,String> otherMap){
        if(otherMap!=null) {
            otherMap.forEach((k, v) -> {
                if(Validator.isNotNull(v)) {
                    exhibitionSurveyOtherOptionDao.deleteBySurveyIdAndField(surveyId, k);

                    ExhibitionSurveyOtherOption otherOption = new ExhibitionSurveyOtherOption();
                    otherOption.setSurveyId(surveyId);
                    otherOption.setSurveyField(k);
                    otherOption.setOtherOption(v);
                    exhibitionSurveyOtherOptionDao.save(otherOption);
                }
            });
        }
    }

    @Transactional(readOnly = false)
    private void shortOtherOptionSave(Long surveyId,Map<String,String> otherMap){
        if(otherMap!=null) {
            otherMap.forEach((k, v) -> {
                if(Validator.isNotNull(v)) {
                    ExhibitionSurveyOtherOption otherOption = new ExhibitionSurveyOtherOption();
                    otherOption.setSurveyId(surveyId);
                    otherOption.setSurveyField(k);
                    otherOption.setOtherOption(v);
                    exhibitionSurveyOtherOptionDao.save(otherOption);
                }
            });
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
        exhibitionSurveyMultiselectDao.deleteBySurveyId(id);
        exhibitionSurveyOtherOptionDao.deleteBySurveyId(id);
        ExhibitionSurveyPublicShow poShow =exhibitionSurveyPublicShowDao.findBySurveyId(id);
        if(poShow!=null){
            exhibitionSurveyPublicShowDao.delete(poShow);
        }
        exhibitionSurveyDao.delete(id);
    }


    public Page<ExhibitionSurveyVo> search(ExhibitionSurveyVo query) throws Exception{
        PageInfo pageInfo = query.getPageInfo();
        if(pageInfo == null){
            pageInfo = new PageInfo();
        }

        List<String> checkboxNameList = exhibitionSurveyFieldDao.findFieldNameByFieldFormat
                (KeyWord.FIELD_FORMAT_CHECKBOX);
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



    public Page<ExhibitionSurveyListVo> list(ExhibitionSurveyVo query) throws Exception{
        PageInfo pageInfo = query.getPageInfo();
        if(pageInfo == null){
            pageInfo = new PageInfo();
        }
//        System.out.println("count="+exhibitionSurveyDao.count());
        Page<ExhibitionSurvey> page = exhibitionSurveyDaoImpl.searchByCondition(query, null, pageInfo);

        List<ExhibitionSurveyListVo> voList = Lists.newArrayList();
        page.getContent().forEach(po->voList.add(this.generateListVo(po)));

        return new PageImpl<ExhibitionSurveyListVo>(voList, new PageRequest(page.getNumber(),
                page.getSize(), page.getSort()), page.getTotalElements());
    }

    private ExhibitionSurveyListVo generateListVo(ExhibitionSurvey survey){
        ExhibitionSurveyListVo vo = new ExhibitionSurveyListVo();
        vo.setId(survey.getId());
        if(Validator.isNotNull(survey.getBrand())) {
            vo.setBrandName(brandDao.findNameById(survey.getBrand().longValue()));
        }
        vo.setAddress(survey.getExhibitionAddress());
        if(Validator.isNotNull(survey.getMarketId())) {
            vo.setExhibitionName(marketDao.findNameById(survey.getMarketId()));
        }
        if(Validator.isNotNull(survey.getExhibitionId())) {
            vo.setMarketName(exhibitionDao.findNameById(survey.getExhibitionId()));
        }
        if(survey.getBusinessNature()!=null) {
            vo.setDealerName(dealerDao.findNameById(survey.getBusinessNature().longValue()));
        }
        if(Validator.isNotNull(survey.getCategoryMain())) {
            vo.setCategoryName(categoryDao.findNameById(survey.getCategoryMain().longValue()));
        }
        if(Validator.isNotNull(survey.getExhibitionCity())){
            City city = cityDao.findOne(survey.getExhibitionCity().longValue());
            if(city!=null){
                vo.setCityName((city.getProvinceName()==null?"":city.getProvinceName())+"/"+city.getCityName());
            }
        }
        vo.setSchedule(new int[]{GetAnswerNum(survey),64});
        vo.setUpdateDate(survey.getUpdateDate());
        vo.setCreateBy(survey.getCreateBy());
        return vo;
    }

    public static int GetAnswerNum(ExhibitionSurvey survey){
        return 64;
    }



}
