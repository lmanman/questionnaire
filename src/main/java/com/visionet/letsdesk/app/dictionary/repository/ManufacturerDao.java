package com.visionet.letsdesk.app.dictionary.repository;

import com.visionet.letsdesk.app.dictionary.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ManufacturerDao extends PagingAndSortingRepository<Manufacturer, Long>, JpaSpecificationExecutor<Manufacturer> {
}
