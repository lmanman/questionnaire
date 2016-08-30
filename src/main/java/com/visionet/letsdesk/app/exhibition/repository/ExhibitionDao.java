package com.visionet.letsdesk.app.exhibition.repository;

import com.visionet.letsdesk.app.exhibition.entity.Exhibition;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ExhibitionDao extends PagingAndSortingRepository<Exhibition, Long>, JpaSpecificationExecutor<Exhibition> {
}
