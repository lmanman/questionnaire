package com.visionet.letsdesk.app.exhibition.service;

import com.google.common.collect.Lists;
import com.visionet.letsdesk.app.base.service.BaseService;
import com.visionet.letsdesk.app.common.constant.BusinessStatus;
import com.visionet.letsdesk.app.common.modules.string.StringPool;
import com.visionet.letsdesk.app.common.modules.utils.Collections3;
import com.visionet.letsdesk.app.common.modules.validate.Validator;
import com.visionet.letsdesk.app.dictionary.entity.Brand;
import com.visionet.letsdesk.app.dictionary.entity.Category;
import com.visionet.letsdesk.app.dictionary.entity.City;
import com.visionet.letsdesk.app.dictionary.entity.Sundry;
import com.visionet.letsdesk.app.dictionary.repository.BrandDao;
import com.visionet.letsdesk.app.dictionary.repository.CategoryDao;
import com.visionet.letsdesk.app.dictionary.repository.CityDao;
import com.visionet.letsdesk.app.dictionary.repository.SundryDao;
import com.visionet.letsdesk.app.exhibition.entity.*;
import com.visionet.letsdesk.app.exhibition.repository.*;
import com.visionet.letsdesk.app.foundation.KeyWord;
import com.visionet.letsdesk.app.market.entity.Market;
import com.visionet.letsdesk.app.market.repository.MarketDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExhibitionSurveyExportService extends BaseService{
    private static Logger log = LoggerFactory.getLogger(ExhibitionSurveyExportService.class);

    private static List<Sundry> sundryList;
    private static List<Category> categoryList;
    private static List<Brand> brandList;
    private static List<Dealer> dealerList;
    private static List<City> cityList;


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
    private ExhibitionDao exhibitionDao;
    @Autowired
    private MarketDao marketDao;
    @Autowired
    private SundryDao sundryDao;
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private DealerDao dealerDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private CityDao cityDao;


    public List<String[]> getExportList(Long formId) throws Exception{
        List<ExhibitionSurvey> surveyList = exhibitionSurveyDao.findByFormId(formId);
        if(Collections3.isEmpty(surveyList)){
            throwException(BusinessStatus.NOTFIND,"data empty!");
        }
        List<Market> marketList = (List<Market>)marketDao.findAll();
        List<Exhibition> exhibitionList = exhibitionDao.findAllExhibition();
        List<ExhibitionSurveyOtherOption> otherOptionList = (List<ExhibitionSurveyOtherOption>)exhibitionSurveyOtherOptionDao.findAll();
        List<ExhibitionSurveyMultiselect> multiselectList = (List<ExhibitionSurveyMultiselect>)exhibitionSurveyMultiselectDao.findAll();
        List<ExhibitionSurveyPublicShow> publicShowList = (List<ExhibitionSurveyPublicShow>)exhibitionSurveyPublicShowDao.findAll();
        sundryList = (List<Sundry>)sundryDao.findAll();
        categoryList = (List<Category>)categoryDao.findAll();
        brandList = (List<Brand>)brandDao.findAll();
        dealerList = (List<Dealer>)dealerDao.findAll();
        cityList = (List<City>)cityDao.findAll();

        List<String[]> list = Lists.newArrayList();
        //title
        List<ExhibitionSurveyField> fieldList = exhibitionSurveyFieldDao.findByFormId(1L);
        String[] titles = new String[fieldList.size()+3];
        titles[0] = "ID";
        titles[1] = "商场";
        titles[2] = "展厅";
        for(int j=0;j<fieldList.size();j++){
            ExhibitionSurveyField f = fieldList.get(j);
            titles[j+3] = f.getFieldDesc();
        }

        for(int i=0;i<surveyList.size();i++){   //行
            ExhibitionSurvey survey = surveyList.get(i);
            String[] data = new String[titles.length];
            data[0] = survey.getId().toString();
            if(Validator.isNotNull(survey.getMarketId())) {
                data[1] = marketList.parallelStream().filter(m -> m.getId().intValue() == survey.getMarketId().intValue()).findFirst().map(m -> m.getName()).get();
            }
            if(Validator.isNotNull(survey.getExhibitionId())) {
                data[2] = exhibitionList.parallelStream().filter(m -> m.getId().intValue() == survey.getExhibitionId().intValue()).findFirst().map(m -> m.getName()).get();
            }

            List<ExhibitionSurveyMultiselect> multiselectListRow=null;
            if(Collections3.isNotEmpty(multiselectList)){
                multiselectListRow = multiselectList.stream().filter(m->m.getSurveyId().longValue()==survey.getId().longValue()).collect(Collectors.toList());
            }
            List<ExhibitionSurveyOtherOption> tempList=null;
            if(Collections3.isNotEmpty(otherOptionList)){
                tempList = otherOptionList.parallelStream().filter(m->m.getSurveyId().longValue()==survey.getId().longValue()).collect(Collectors.toList());
            }

            Map<String,Object> publicShowMap = null;
            if(Collections3.isNotEmpty(publicShowList)){
                Optional<ExhibitionSurveyPublicShow> optional = publicShowList.parallelStream().filter(p->p.getSurveyId().longValue()==survey.getId().longValue()).findFirst();
                if(optional!=null && optional.isPresent()){
                    publicShowMap = mapper.fromJson(mapper.toJson(optional.get()),Map.class);
                }
            }
            final List<ExhibitionSurveyOtherOption> otherOptionRowList = tempList;
            Map<String,Object> map = this.transferVoToMap(survey);
            for(int j=0;j<fieldList.size();j++){    //列
                ExhibitionSurveyField f = fieldList.get(j);
                if(f.getFieldFormat().equals(KeyWord.FIELD_FORMAT_CHECKBOX)){
                    if(Collections3.isNotEmpty(multiselectListRow)){
                        List<String> multi = multiselectListRow.stream().filter(m->m.getSurveyField().equals(f.getFieldName()))
                                .map(m -> GetSundryVal(m.getSundryId(), otherOptionRowList, f.getFieldName(), f.getRelationData())).collect(Collectors.toList());
                        if(Collections3.isNotEmpty(multi)){
                            data[j+3] = multi.stream().reduce((a, b) -> a + StringPool.SEMICOLON + b).get();
                        }
                    }
                }else {
                    Object obj = null;
                    if(f.getIndicator().equals(KeyWord.FIELD_INDICATOR_PUBLIC_RESOURCE)) {
                        if(publicShowMap!=null) {
                            obj = publicShowMap.get(f.getFieldName());
                        }
                    }else {
                        obj = map.get(f.getFieldName());
                    }
                    if(f.getFieldFormat().equals(KeyWord.FIELD_FORMAT_RADIO) || f.getFieldFormat().equals(KeyWord.FIELD_FORMAT_SELECT)){
                        if(obj!=null&&Collections3.isNotEmpty(sundryList)) {
                            data[j+3] = GetSundryVal((Integer)obj,otherOptionRowList,f.getFieldName(),f.getRelationData());
                        }
                    }else {
                        data[j + 3] = obj == null ? null : obj.toString();
                    }
                }
            }
            list.add(data);
        }


        list.add(0,titles);
        return list;
    }

    private Map<String,Object> transferVoToMap(ExhibitionSurvey survey){
        String json = mapper.toJson(survey);
        return mapper.fromJson(json,Map.class);
    }

    private static String GetSundryVal(int sundryId,
                           List<ExhibitionSurveyOtherOption> otherOptionList,
                           String fieldName,String relationData){
        String val=null;
        if(Validator.isNotNull(relationData)) {
            if("d_category".equals(relationData)){   //品类
                Optional<Category> optional = categoryList.parallelStream().filter(c -> c.getId().intValue() == sundryId).findFirst();
                if(optional!=null && optional.isPresent()) {
                    val = optional.map(c->c.getName()).get();
                }
            } else if("d_brand".equals(relationData)){  //品牌
                Optional<Brand> optional = brandList.parallelStream().filter(b -> b.getId().intValue() == sundryId).findFirst();
                if(optional!=null && optional.isPresent()) {
                    val = optional.map(b->b.getName()).get();
                }
            } else if("d_city".equals(relationData)){  //省市区
                Optional<City> optional = cityList.parallelStream().filter(c->c.getId().intValue()==sundryId).findFirst();
                if(optional!=null && optional.isPresent()) {
                    val = optional.map(c->c.getProvinceName()+"/"+c.getCityName()).get();
                }
            } else if("s_dealer".equals(relationData)){ //经销商
                Optional<Dealer> optional = dealerList.parallelStream().filter(d->d.getId().intValue()==sundryId).findFirst();
                if(optional!=null && optional.isPresent()) {
                    val = optional.map(d -> d.getName()).get();
                }
            }
        }
        if(val==null){
            Optional<Sundry> optional1 = sundryList.parallelStream().filter(s -> s.getId().intValue() == sundryId).findFirst();
            if(optional1!=null && optional1.isPresent()){
                Sundry sundry = optional1.get();
                if (sundry != null) {
                    if (sundry.getCode().equals("Z")) {
                        Optional<ExhibitionSurveyOtherOption> optional2 = otherOptionList.parallelStream().filter(o -> o.getSurveyField().equals(fieldName)).findFirst();
                        if(optional2!=null && optional2.isPresent()) {
                            val = optional2.map(o -> o.getOtherOption()).get();
                        }
                    } else {
                        val = sundry.getName();
                    }
                }
            }
        }
        return val;
    }

//    private static String GetValByfield(ExhibitionSurvey survey,String fieldName) throws Exception{
//        BeanInfo beanInfo = Introspector.getBeanInfo(survey.getClass());
//        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
//        for (int i = 0; i< propertyDescriptors.length; i++) {
//            PropertyDescriptor descriptor = propertyDescriptors[i];
//            String propertyName = descriptor.getName();
//            if(propertyName.equals(fieldName)) {
//                Method readMethod = descriptor.getReadMethod();
//                if(readMethod!=null) {
//                    Object result = readMethod.invoke(survey, new Object[0]);
//                    if (result != null) {
//                        return result.toString();
//                    }else {
//                        return null;
//                    }
//                }
//                return null;
//            }
//        }
//        return null;
//    }

}
