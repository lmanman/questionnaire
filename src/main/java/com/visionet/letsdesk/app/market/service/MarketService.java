package com.visionet.letsdesk.app.market.service;

import com.google.common.collect.Lists;
import com.visionet.letsdesk.app.base.service.BaseService;
import com.visionet.letsdesk.app.common.constant.BusinessStatus;
import com.visionet.letsdesk.app.common.modules.persistence.DynamicSpecifications;
import com.visionet.letsdesk.app.common.modules.persistence.SearchFilter;
import com.visionet.letsdesk.app.common.modules.utils.Collections3;
import com.visionet.letsdesk.app.common.modules.validate.Validator;
import com.visionet.letsdesk.app.common.utils.BeanConvertMap;
import com.visionet.letsdesk.app.common.utils.PageInfo;
import com.visionet.letsdesk.app.common.utils.SearchFilterUtil;
import com.visionet.letsdesk.app.dictionary.entity.City;
import com.visionet.letsdesk.app.dictionary.repository.CityDao;
import com.visionet.letsdesk.app.exhibition.entity.Exhibition;
import com.visionet.letsdesk.app.exhibition.repository.ExhibitionDao;
import com.visionet.letsdesk.app.exhibition.service.ExhibitionSurveyService;
import com.visionet.letsdesk.app.market.entity.Market;
import com.visionet.letsdesk.app.market.repository.MarketDao;
import com.visionet.letsdesk.app.market.vo.MarketVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class MarketService extends BaseService {

    @Autowired
    private MarketDao marketDao;
    @Autowired
    private CityDao cityDao;
    @Autowired
    private ExhibitionDao exhibitionDao;
    @Autowired
    private ExhibitionSurveyService exhibitionSurveyService;


    public Page<MarketVo> searchMarker(MarketVo vo) throws Exception {
        Map<String, Object> searchParams = SearchFilterUtil.convertBean(vo);
        PageInfo pageInfo = vo.getPageInfo();
        if (pageInfo == null) {
            pageInfo = new PageInfo();
        }

        Map<String, SearchFilter> filters = SearchFilterUtil.parse(searchParams);

        if (filters.get("name") != null) {
            filters.put("name", new SearchFilter("name", SearchFilter.Operator.LIKE, filters.get("name").value));
        }
        PageRequest pageRequest = buildPageRequest(pageInfo.getPageNumber(), pageInfo.getPageSize(), pageInfo.getSortColumn());

        Page<Market> page = marketDao.findAll(DynamicSpecifications.bySearchFilter(filters.values(), Market.class), pageRequest);

        List<MarketVo> voList = Lists.newArrayList();
        for(Market market:page.getContent()){
            MarketVo marketVo = BeanConvertMap.map(market, MarketVo.class);
            this.transferVo(market, marketVo);
            voList.add(marketVo);
        }
        return new PageImpl<MarketVo>(voList, new PageRequest(page.getNumber(),
                page.getSize(), page.getSort()), page.getTotalElements());

    }

    public void transferVo(Market market,MarketVo marketVo){
        if(Validator.isNotNull(market.getCityId())) {
            City city = cityDao.findOne(market.getCityId());
            if(city!=null) {
                marketVo.setCityName(city.getCityName());
                marketVo.setProvinceName(city.getProvinceName());
            }
        }
    }

    public Market findMarkerById(Long id) {
        return marketDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public void deleteMarker(Long id) {
        marketDao.delete(id);
        List<Exhibition> exhibitions = exhibitionDao.findByMarketId(id);
        if(Collections3.isNotEmpty(exhibitions)){
            exhibitions.stream().forEach(e->{
                e.setMarketId(null);
                exhibitionDao.save(e);
            });
        }
    }

    @Transactional(readOnly = false)
    public void saveMarker(Market dealer) throws Exception {
        if (Validator.isNull(dealer.getName())) {
            throwException(BusinessStatus.REQUIRE, "name is null!");
        }
        if(Validator.isNotNull(dealer.getCityId())) {
            dealer.setProvinceId(exhibitionSurveyService.getProvinceId(dealer.getCityId().intValue()).longValue());
        }
        marketDao.save(dealer);
    }

}