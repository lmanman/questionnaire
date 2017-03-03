package com.visionet.letsdesk.app.market.repository;

import com.visionet.letsdesk.app.market.entity.Market;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MarketDao extends PagingAndSortingRepository<Market, Long>, JpaSpecificationExecutor<Market> {

    @Query("select m.name from Market m where m.id=?1")
    String findNameById(Long id);
}
