package com.visionet.letsdesk.app.exhibition.repository;

import com.visionet.letsdesk.app.exhibition.entity.ExhibitionSurveyMultiselect;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExhibitionSurveyMultiselectDao extends CrudRepository<ExhibitionSurveyMultiselect, Long> {

    @Query("select m.sundryId from ExhibitionSurveyMultiselect m where m.surveyId = ?1 and m.surveyField = ?2")
    List<Integer> findSundryIdBySurveyIdAndSurveyField(Long surveyId,String fieldName);

    List<ExhibitionSurveyMultiselect> findBySurveyId(Long surveyId);
}
