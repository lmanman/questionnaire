package com.visionet.letsdesk.app.dictionary.service;

import com.google.common.collect.Lists;
import com.visionet.letsdesk.app.base.service.BaseService;
import com.visionet.letsdesk.app.common.constant.BusinessStatus;
import com.visionet.letsdesk.app.common.modules.persistence.DynamicSpecifications;
import com.visionet.letsdesk.app.common.modules.persistence.SearchFilter;
import com.visionet.letsdesk.app.common.modules.validate.Validator;
import com.visionet.letsdesk.app.common.utils.BeanConvertMap;
import com.visionet.letsdesk.app.common.utils.PageInfo;
import com.visionet.letsdesk.app.common.utils.SearchFilterUtil;
import com.visionet.letsdesk.app.dictionary.entity.Brand;
import com.visionet.letsdesk.app.dictionary.entity.Category;
import com.visionet.letsdesk.app.dictionary.entity.Manufacturer;
import com.visionet.letsdesk.app.dictionary.repository.BrandDao;
import com.visionet.letsdesk.app.dictionary.repository.CategoryDao;
import com.visionet.letsdesk.app.dictionary.repository.ManufacturerDao;
import com.visionet.letsdesk.app.dictionary.repository.SundryDao;
import com.visionet.letsdesk.app.dictionary.vo.BrandVo;
import com.visionet.letsdesk.app.dictionary.vo.ManufacturerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class DictionaryService extends BaseService{

    @Autowired
    private BrandDao brandDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private ManufacturerDao manufacturerDao;
    @Autowired
    private SundryDao sundryDao;


    /**
     * 品牌查询
     * @return
     */
    public Page<BrandVo> searchBrand(BrandVo query) throws Exception{
        Map<String, Object> searchParams = SearchFilterUtil.convertBean(query);
        PageInfo pageInfo = query.getPageInfo();
        if(pageInfo == null){
            pageInfo = new PageInfo();
        }

        Map<String, SearchFilter> filters = SearchFilterUtil.parse(searchParams);

        if(filters.get("name")!=null){
            filters.put("name", new SearchFilter("name", SearchFilter.Operator.LIKE, filters.get("name").value));
        }
        if(filters.get("categoryMain")!=null){
            filters.put("categoryMain", new SearchFilter("categoryMainId", SearchFilter.Operator.EQ, ((Category)filters.get("categoryMain").value).getId()));
        }
        if(filters.get("categoryFunction")!=null){
            filters.put("categoryFunction", new SearchFilter("categoryFunctionId", SearchFilter.Operator.EQ, ((Category)filters.get("categoryFunction").value).getId()));
        }
        if(filters.get("categoryMaterial")!=null){
            filters.put("categoryMaterial", new SearchFilter("categoryMaterialId", SearchFilter.Operator.EQ, ((Category)filters.get("categoryMaterial").value).getId()));
        }
        if(filters.get("categoryStyle")!=null){
            filters.put("categoryStyle", new SearchFilter("categoryStyleId", SearchFilter.Operator.EQ, ((Category)filters.get("categoryStyle").value).getId()));
        }
        if(filters.get("categoryImport")!=null){
            filters.put("categoryImport", new SearchFilter("categoryImportId", SearchFilter.Operator.EQ, ((Category)filters.get("categoryImport").value).getId()));
        }

        PageRequest pageRequest = buildPageRequest(pageInfo.getPageNumber(), pageInfo.getPageSize(),pageInfo.getSortColumn());

        Page<Brand> page = brandDao.findAll(DynamicSpecifications.bySearchFilter(filters.values(), Brand.class), pageRequest);
        List<BrandVo> voList = Lists.newArrayList();
        for(Brand brand:page.getContent()){
            BrandVo vo = BeanConvertMap.map(brand,BrandVo.class);
            this.transferBrandVo(brand,vo);
            voList.add(vo);
        }
        return new PageImpl<BrandVo>(voList, new PageRequest(page.getNumber(),
                page.getSize(), page.getSort()), page.getTotalElements());
    }

    /**
     * 品牌明细
     * @param brandId
     * @return
     */
    public BrandVo findBrandVoById(Long brandId){
        Brand brand = brandDao.findOne(brandId);
        BrandVo vo = BeanConvertMap.map(brand,BrandVo.class);
        this.transferBrandVo(brand,vo);
        return vo;
    }
    private void transferBrandVo(Brand brand,BrandVo vo){
        if(Validator.isNotNull(brand.getManufacturerId())) {
            vo.setManufacturer(manufacturerDao.findOne(brand.getManufacturerId()));
        }
        if(Validator.isNotNull(brand.getCategoryMainId())) {
            vo.setCategoryMain(categoryDao.findOne(brand.getCategoryMainId().longValue()));
        }
        if(Validator.isNotNull(brand.getCategoryFunctionId())) {
            vo.setCategoryFunction(categoryDao.findOne(brand.getCategoryFunctionId().longValue()));
        }
        if(Validator.isNotNull(brand.getCategoryMaterialId())) {
            vo.setCategoryMaterial(categoryDao.findOne(brand.getCategoryMaterialId().longValue()));
        }
        if(Validator.isNotNull(brand.getCategoryStyleId())) {
            vo.setCategoryStyle(categoryDao.findOne(brand.getCategoryStyleId().longValue()));
        }
        if(Validator.isNotNull(brand.getCategoryImportId())) {
            vo.setCategoryImport(categoryDao.findOne(brand.getCategoryImportId().longValue()));
        }
    }

    /**
     * 品牌保存
     * @param brand
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public void saveBrand(Brand brand) throws Exception{
        if(brand.getId()==null){
            if(Validator.isNull(brand.getName())){
                throwException(BusinessStatus.REQUIRE,"name is null!");
            }
            if(Validator.isNull(brand.getType())){
                throwException(BusinessStatus.REQUIRE,"type is null!");
            }
            if(Validator.isNull(brand.getCategoryMainId())){
                throwException(BusinessStatus.REQUIRE,"categoryMainId is null!");
            }
            brandDao.save(brand);
        }else{
            Brand po = brandDao.findOne(brand.getId());
            SearchFilterUtil.copyBeans(po, brand);
            brandDao.save(po);
        }
    }

    /**
     * 品类List
     * @return
     */
    public List<Category> findCategoryList(){
        return (List<Category>)categoryDao.findAll();
    }

    /**
     * 品类保存
     * @param category
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public void saveCategory(Category category) throws Exception{
        if(category.getId()==null){
            if(Validator.isNull(category.getName())){
                throwException(BusinessStatus.REQUIRE,"name is null!");
            }
            if(Validator.isNull(category.getLevel())){
                throwException(BusinessStatus.REQUIRE,"level is null!");
            }
            if(Validator.isNull(category.getParentId())){
                throwException(BusinessStatus.REQUIRE,"parentId is null!");
            }
            categoryDao.save(category);
        }else{
            Category po = categoryDao.findOne(category.getId());
            SearchFilterUtil.copyBeans(po, category);
            categoryDao.save(po);
        }
    }


    /**
     * 厂商查询
     * @param vo
     * @return
     * @throws Exception
     */
    public Page<Manufacturer> searchManufacturer(ManufacturerVo vo) throws Exception{
        Map<String, Object> searchParams = SearchFilterUtil.convertBean(vo);
        PageInfo pageInfo = vo.getPageInfo();
        if(pageInfo == null){
            pageInfo = new PageInfo();
        }

        Map<String, SearchFilter> filters = SearchFilterUtil.parse(searchParams);

        if(filters.get("name")!=null){
            filters.put("name", new SearchFilter("name", SearchFilter.Operator.LIKE, filters.get("name").value));
        }
        PageRequest pageRequest = buildPageRequest(pageInfo.getPageNumber(), pageInfo.getPageSize(),pageInfo.getSortColumn());

        return manufacturerDao.findAll(DynamicSpecifications.bySearchFilter(filters.values(), Manufacturer.class), pageRequest);
    }

    /**
     * 厂商保存
     * @param manufacturer
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public void saveManufacturer(Manufacturer manufacturer) throws Exception{
        if(manufacturer.getId()==null){
            if(Validator.isNull(manufacturer.getName())){
                throwException(BusinessStatus.REQUIRE,"name is null!");
            }

            manufacturerDao.save(manufacturer);
        }else{
            Manufacturer po = manufacturerDao.findOne(manufacturer.getId());
            SearchFilterUtil.copyBeans(po, manufacturer);
            manufacturerDao.save(po);
        }
    }



}
