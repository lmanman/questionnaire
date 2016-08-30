package com.visionet.letsdesk.app.foundation.repository;

import com.visionet.letsdesk.app.foundation.entity.MobileClient;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MobileClientDao extends PagingAndSortingRepository<MobileClient, Long>, JpaSpecificationExecutor<MobileClient> {

    @Query("select c from MobileClient c where c.clientType=?1 order by c.id desc")
    public List<MobileClient> findByClientType(String clientType);

}
