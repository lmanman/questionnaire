package com.visionet.letsdesk.app.dictionary.repository;

import com.visionet.letsdesk.app.dictionary.entity.City;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CityDao extends CrudRepository<City, Long> {

    @Query("select c from City c where c.delFlag=0 ")
    List<City> findCity();
}
