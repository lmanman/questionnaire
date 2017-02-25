package com.visionet.letsdesk.app.dictionary.repository;

import com.visionet.letsdesk.app.dictionary.entity.Brand;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BrandDao extends PagingAndSortingRepository<Brand, Long>, JpaSpecificationExecutor<Brand> {

    @Query("select b from Brand b order by b.parentId,b.id")
    List<Brand> findOrderByParentId();
}
