package com.visionet.letsdesk.app.foundation.repository;

import com.visionet.letsdesk.app.foundation.entity.SystemDict;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.QueryHint;
import java.util.List;

public interface SystemDictDao extends PagingAndSortingRepository<SystemDict, Long>, JpaSpecificationExecutor<SystemDict> {

    @Query("select d from SystemDict d order by d.systemType,d.id")
    List<SystemDict> findDictList();

    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    @Query("select d from SystemDict d where d.systemType = ?1 order by d.id")
    List<SystemDict> findBySystemType(String type);

    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    @Query("select d.systemName from SystemDict d where d.systemType = ?1 and d.systemCode = ?2")
    String findBySystemTypeAndCode(String type,String code);

    @Query("select d from SystemDict d where d.systemType = ?1 and d.systemCode not in (?2) order by d.id")
    List<SystemDict> findOtherBySystemType(String systemType,List<String> existCodes);

}
