package com.visionet.letsdesk.app.exhibition.service;

import com.google.common.collect.Lists;
import com.visionet.letsdesk.app.base.service.BaseService;
import com.visionet.letsdesk.app.common.constant.BusinessStatus;
import com.visionet.letsdesk.app.common.modules.persistence.DynamicSpecifications;
import com.visionet.letsdesk.app.common.modules.persistence.SearchFilter;
import com.visionet.letsdesk.app.common.modules.time.DateUtil;
import com.visionet.letsdesk.app.common.modules.validate.Validator;
import com.visionet.letsdesk.app.common.utils.BeanConvertMap;
import com.visionet.letsdesk.app.common.utils.PageInfo;
import com.visionet.letsdesk.app.common.utils.SearchFilterUtil;
import com.visionet.letsdesk.app.dictionary.repository.CityDao;
import com.visionet.letsdesk.app.dictionary.service.DictionaryService;
import com.visionet.letsdesk.app.dictionary.vo.ExhibitionVo;
import com.visionet.letsdesk.app.exhibition.entity.Dealer;
import com.visionet.letsdesk.app.exhibition.entity.Exhibition;
import com.visionet.letsdesk.app.exhibition.repository.DealerDao;
import com.visionet.letsdesk.app.exhibition.repository.ExhibitionDao;
import com.visionet.letsdesk.app.exhibition.vo.DealerVo;
import com.visionet.letsdesk.app.market.repository.MarketDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ExhibitionInfoService extends BaseService{

    @Autowired
    private ExhibitionDao exhibitionDao;
    @Autowired
    private MarketDao marketDao;
    @Autowired
    private DealerDao dealerDao;
    @Autowired
    private CityDao cityDao;
    @Autowired
    private DictionaryService dictionaryService;


    /**
     * 展厅明细
     * @param id
     * @return
     */
    public Exhibition findById(Long id){
        return exhibitionDao.findOne(id);
    }
    public ExhibitionVo findVoById(Long id){
        Exhibition exhibition = exhibitionDao.findOne(id);
        ExhibitionVo vo = BeanConvertMap.map(exhibition,ExhibitionVo.class);
        this.transferExhibitionVo(exhibition,vo);
        return vo;
    }
    private void transferExhibitionVo(Exhibition exhibition,ExhibitionVo vo ){
        if(Validator.isNotNull(exhibition.getBrandId())) {
            vo.setBrandVo(dictionaryService.findBrandVoById(exhibition.getBrandId()));
        }
        if(Validator.isNotNull(exhibition.getDealerId())) {
            vo.setDealer(dealerDao.findOne(exhibition.getDealerId()));
        }
        if(Validator.isNotNull(exhibition.getMarketId())) {
            vo.setMarket(marketDao.findOne(exhibition.getMarketId()));
        }
        if(Validator.isNotNull(exhibition.getCityId())) {
            vo.setCity(cityDao.findOne(exhibition.getCityId()));
        }
    }


    /**
     * 展厅查询
     * @param query
     * @return
     * @throws Exception
     */
    public Page<ExhibitionVo> search(ExhibitionVo query) throws Exception{
        Map<String, Object> searchParams = SearchFilterUtil.convertBean(query);
        PageInfo pageInfo = query.getPageInfo();
        if(pageInfo == null){
            pageInfo = new PageInfo();
        }

        Map<String, SearchFilter> filters = SearchFilterUtil.parse(searchParams);

        if(filters.get("name")!=null){
            filters.put("name", new SearchFilter("name", SearchFilter.Operator.LIKE, filters.get("name").value));
        }
        PageRequest pageRequest = buildPageRequest(pageInfo.getPageNumber(), pageInfo.getPageSize(),pageInfo.getSortColumn());

        Page<Exhibition> page =exhibitionDao.findAll(DynamicSpecifications.bySearchFilter(filters.values(), Exhibition.class), pageRequest);

        List<ExhibitionVo> voList = Lists.newArrayList();
        for(Exhibition exhibition:page.getContent()){
            ExhibitionVo vo = BeanConvertMap.map(exhibition,ExhibitionVo.class);
            this.transferExhibitionVo(exhibition, vo);
            voList.add(vo);
        }
        return new PageImpl<ExhibitionVo>(voList, new PageRequest(page.getNumber(),
                page.getSize(), page.getSort()), page.getTotalElements());
    }

    /**
     * 展厅保存
     * @param exhibition
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public void save(Exhibition exhibition) throws Exception{
        if(exhibition.getId()==null){
            if(Validator.isNull(exhibition.getBrandId())){
                throwException(BusinessStatus.REQUIRE,"brandId is null!");
            }
            if(Validator.isNull(exhibition.getMarketId())){
                throwException(BusinessStatus.REQUIRE,"marketId is null!");
            }
            exhibition.setCreateDate(DateUtil.getCurrentDate());
            exhibitionDao.save(exhibition);
        }else{
            Exhibition po = exhibitionDao.findOne(exhibition.getId());
            SearchFilterUtil.copyBeans(po,exhibition);
            po.setUpdateDate(DateUtil.getCurrentDate());
            exhibitionDao.save(po);
        }
    }



    /**
     * 经销商查询
     * @param vo
     * @return
     * @throws Exception
     */
    public Page<Dealer> searchDealer(DealerVo vo) throws Exception{
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

        return dealerDao.findAll(DynamicSpecifications.bySearchFilter(filters.values(), Dealer.class), pageRequest);
    }

    /**
     * 经销商保存
     * @param dealer
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public void saveDealer(Dealer dealer) throws Exception{
        if(dealer.getId()==null){
            if(Validator.isNull(dealer.getName())){
                throwException(BusinessStatus.REQUIRE,"name is null!");
            }
            if(Validator.isNull(dealer.getLinkman())){
                throwException(BusinessStatus.REQUIRE,"linkman is null!");
            }
            dealerDao.save(dealer);
        }else{
            Dealer po = dealerDao.findOne(dealer.getId());
            SearchFilterUtil.copyBeans(po, dealer);
            dealerDao.save(po);
        }
    }

}
