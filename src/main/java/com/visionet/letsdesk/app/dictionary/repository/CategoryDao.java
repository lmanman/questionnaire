package com.visionet.letsdesk.app.dictionary.repository;

import com.visionet.letsdesk.app.dictionary.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryDao extends CrudRepository<Category, Long> {

    List<Category> findByLevel(Integer level);
    List<Category> findByType(String type);


    @Query("select b.name from Category b where b.id=?1")
    String findNameById(Long id);
}
