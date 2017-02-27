package com.visionet.letsdesk.app.exhibition.repository;

import com.visionet.letsdesk.app.exhibition.entity.Dealer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DealerDao extends PagingAndSortingRepository<Dealer, Long>, JpaSpecificationExecutor<Dealer> {

    @Query("select b.name from Dealer b where b.id=?1")
    String findNameById(Long id);
}
