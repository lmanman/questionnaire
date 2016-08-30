package com.visionet.letsdesk.app.dictionary.repository;

import com.visionet.letsdesk.app.dictionary.entity.Brand;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BrandDao extends PagingAndSortingRepository<Brand, Long>, JpaSpecificationExecutor<Brand> {
}
