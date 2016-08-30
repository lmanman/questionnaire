package com.visionet.letsdesk.app.exhibition.repository;

import com.visionet.letsdesk.app.exhibition.entity.ExhibitionSurveyField;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExhibitionSurveyFieldDao extends CrudRepository<ExhibitionSurveyField, Long> {

    List<ExhibitionSurveyField> findByTableName(String tableName);

    @Query("select f.fieldName from ExhibitionSurveyField f where f.fieldFormat = ?1 and f.tableName = ?2")
    List<String> findByFieldFormatAndTableName(String fieldFormat,String tableName);
}
