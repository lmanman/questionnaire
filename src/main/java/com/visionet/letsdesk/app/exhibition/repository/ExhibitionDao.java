package com.visionet.letsdesk.app.exhibition.repository;

import com.visionet.letsdesk.app.exhibition.entity.Exhibition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ExhibitionDao extends PagingAndSortingRepository<Exhibition, Long>, JpaSpecificationExecutor<Exhibition> {

    @Query("select b.name from Exhibition b where b.id=?1")
    String findNameById(Long id);

    @Query("select b from Exhibition b where b.delFlag=0")
    List<Exhibition> findAllExhibition();

    @Query("select b from Exhibition b where b.delFlag = 0 and (b.name like %?1% " +
            "or (exists (select 1 from Brand d where d.id=b.brandId and d.name like %?1%)) " +
            "or (exists (select 1 from City c where c.id=b.cityId and c.cityName like %?1%)))")
    Page<Exhibition> search(String queryName,Pageable pageable);
}
