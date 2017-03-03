package com.visionet.letsdesk.app.dictionary.repository;

import com.visionet.letsdesk.app.dictionary.entity.Province;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProvinceDao extends CrudRepository<Province, Long> {

    @Query("select p from Province p where p.delFlag=0 ")
    List<Province> findProvince();
}
