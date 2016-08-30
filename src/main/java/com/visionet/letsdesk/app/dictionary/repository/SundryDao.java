package com.visionet.letsdesk.app.dictionary.repository;

import com.visionet.letsdesk.app.dictionary.entity.Sundry;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SundryDao extends CrudRepository<Sundry, Long> {

    List<Sundry> findByType(String type);
}
