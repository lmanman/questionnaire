package com.visionet.letsdesk.app.exhibition.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.visionet.letsdesk.app.base.service.BaseService;
import com.visionet.letsdesk.app.common.modules.utils.Collections3;
import com.visionet.letsdesk.app.common.modules.validate.Validator;
import com.visionet.letsdesk.app.common.utils.BeanConvertMap;
import com.visionet.letsdesk.app.dictionary.entity.*;
import com.visionet.letsdesk.app.dictionary.repository.*;
import com.visionet.letsdesk.app.dictionary.vo.SundryVo;
import com.visionet.letsdesk.app.exhibition.entity.Dealer;
import com.visionet.letsdesk.app.exhibition.entity.ExhibitionSurveyField;
import com.visionet.letsdesk.app.exhibition.repository.DealerDao;
import com.visionet.letsdesk.app.exhibition.repository.ExhibitionSurveyFieldDao;
import com.visionet.letsdesk.app.exhibition.vo.ExhibitionSurveyFieldVo;
import com.visionet.letsdesk.app.foundation.KeyWord;
import com.visionet.letsdesk.app.market.entity.Market;
import com.visionet.letsdesk.app.market.repository.MarketDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExhibitionSurveyFieldService extends BaseService{

    @Autowired
    private ExhibitionSurveyFieldDao exhibitionSurveyFieldDao;
    @Autowired
    private SundryDao sundryDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private ManufacturerDao manufacturerDao;
    @Autowired
    private DealerDao dealerDao;
    @Autowired
    private ProvinceDao provinceDao;
    @Autowired
    private CityDao cityDao;
    @Autowired
    private MarketDao marketDao;

    /**
     * 展厅问卷字段项
     * @return
     */
    public List<ExhibitionSurveyFieldVo> findExhibitionField(Long formId){
        List<ExhibitionSurveyFieldVo> voList = Lists.newArrayList();
        List<ExhibitionSurveyField> fieldList = exhibitionSurveyFieldDao.findByFormId(formId);
        for(ExhibitionSurveyField field:fieldList){
            ExhibitionSurveyFieldVo vo = BeanConvertMap.map(field, ExhibitionSurveyFieldVo.class);
            vo.setOptionList(this.findSundry(vo.getFieldName(),vo.getRelationData()));
            vo.setOtherOptionVo(this.findOtherMap());
            voList.add(vo);
        }
        return voList;
    }

    public List<ExhibitionSurveyFieldVo> findExhibitionFieldShort(Long formId){
        List<ExhibitionSurveyFieldVo> voList = Lists.newArrayList();
        List<ExhibitionSurveyField> fieldList = exhibitionSurveyFieldDao.findByFormId(formId, KeyWord.Y);
        for(ExhibitionSurveyField field:fieldList){
            ExhibitionSurveyFieldVo vo = BeanConvertMap.map(field, ExhibitionSurveyFieldVo.class);
            vo.setOptionList(this.findSundry(vo.getFieldName(),vo.getRelationData()));
            vo.setOtherOptionVo(this.findOtherMap());
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

    public List<SundryVo> findSundry(final String type,String relationData){
        if(Validator.isNotNull(relationData)) {
            if ("yn".equals(relationData) || "exist".equals(relationData)) {
                return BeanConvertMap.mapList(sundryDao.findByType(relationData), SundryVo.class);
            } else if("d_category".equals(relationData)){   //品类
                String cgType = type.replaceFirst("category","").toLowerCase();
                List<Category> categoryList = categoryDao.findByType(cgType);
                return categoryList.parallelStream().map(c->{
                    SundryVo s = new SundryVo();
                    s.setId(c.getId());
                    s.setCode(c.getId().toString());
                    s.setName(c.getName());
                    s.setType(type);
                    return s;
                }).collect(Collectors.toList());
            } else if("d_brand".equals(relationData)){  //品牌
                return this.findBrandTree();
            } else if("d_city".equals(relationData)){  //省市区
                List<Province> provinceList = provinceDao.findProvince();
                List<City> cityList = cityDao.findCity();
                return provinceList.stream().map(p -> {
                    SundryVo s = new SundryVo();
                    s.setId(p.getId());
                    s.setCode(p.getId().toString());
                    s.setName(p.getProvinceName());
                    s.setChildDataList(cityList.parallelStream()
                            .filter(c -> c.getProvinceId().intValue() == p.getId().intValue())
                            .map(c -> {
                                SundryVo.ChildData childData = new SundryVo.ChildData();
                                childData.setId(c.getId());
                                childData.setName(c.getCityName());
                                return childData;
                            }).collect(Collectors.toList()));
                    s.setType(type);
                    return s;
                }).collect(Collectors.toList());
            } else if("d_manufacturer".equals(relationData)){   //厂商
                return ((List<Manufacturer>)manufacturerDao.findAll()).parallelStream().map(m -> new SundryVo(m.getId(),m.getName())).collect(Collectors.toList());
            } else if("s_dealer".equals(relationData)){ //经销商
                return ((List<Dealer>)dealerDao.findAll()).stream().map(d -> {
                    SundryVo s = new SundryVo();
                    s.setId(d.getId());
                    s.setCode(d.getId().toString());
                    s.setName(d.getName());
                    s.setType(type);
                    return s;
                }).collect(Collectors.toList());
            } else if("s_market".equals(relationData)){ //商场
                return ((List<Market>)marketDao.findAll()).stream().map(d -> {
                    SundryVo s = new SundryVo();
                    s.setId(d.getId());
                    s.setCode(d.getId().toString());
                    s.setName(d.getName());
                    s.setType(type);
                    return s;
                }).collect(Collectors.toList());
            }
        }else if(Validator.isNotNull(type)) {
            return BeanConvertMap.mapList(sundryDao.findByType(type), SundryVo.class);
        }
        return null;
    }

    public Map<String,String> findOtherMap(){
        Map<String,String> otherMap = Maps.newHashMap();
        List<String> otherList = exhibitionSurveyFieldDao.findFieldNameWithOther();
        if(Collections3.isNotEmpty(otherList)){
            otherList.parallelStream().forEach(obj -> otherMap.put(obj, ""));
        }

        return otherMap;
    }

    public List<SundryVo> findBrandTree(){
        List<SundryVo> voList = Lists.newArrayList();
        List<Brand> brandList = brandDao.findOrderByParentId();

        brandList.stream().forEach(brand -> {
            if(brand.getType().intValue()==1) {
                SundryVo vo = new SundryVo();
                vo.setId(brand.getId());
                vo.setName(brand.getName());
                vo.setType("select");
                vo.setChildDataList(Lists.newArrayList());
                voList.add(vo);
            }else if(brand.getType().intValue()==2){
                SundryVo.ChildData childData = new SundryVo.ChildData();
                childData.setId(brand.getId());
                childData.setName(brand.getName());
                childData.setGrandchildDataList(Lists.newArrayList());
                SundryVo parentVo = voList.parallelStream().filter(v -> v.getId().longValue() == brand.getParentId().longValue()).findFirst().get();
                parentVo.getChildDataList().add(childData);
            }else if(brand.getType().intValue()==3){
                SundryVo.ChildData.GrandchildData grandchildData = new SundryVo.ChildData.GrandchildData();
                grandchildData.setId(brand.getId());
                grandchildData.setName(brand.getName());
                voList.parallelStream().forEach(v -> {
                    Optional<SundryVo.ChildData> optional = v.getChildDataList().parallelStream().filter(c -> c.getId().longValue() == brand.getParentId().longValue()).findFirst();
                    if (optional.isPresent()) {
                        optional.get().getGrandchildDataList().add(grandchildData);
                    }
                });
            }
        });

        return voList;
    }

}
