package com.visionet.letsdesk.app.exhibition.repository;

import com.visionet.letsdesk.app.exhibition.entity.ExhibitionSurveyField;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExhibitionSurveyFieldDao extends CrudRepository<ExhibitionSurveyField, Long> {

    @Query("select f from ExhibitionSurveyField f where f.formId = ?1 and f.delFlag = 0 order by f.orderId")
    List<ExhibitionSurveyField> findByFormId(Long formId);

    @Query("select f.fieldName from ExhibitionSurveyField f where f.fieldFormat = ?1 ")
    List<String> findFieldNameByFieldFormat(String fieldFormat);

}
