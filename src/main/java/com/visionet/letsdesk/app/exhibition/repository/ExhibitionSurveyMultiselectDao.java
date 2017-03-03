package com.visionet.letsdesk.app.exhibition.repository;

import com.visionet.letsdesk.app.exhibition.entity.ExhibitionSurveyMultiselect;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExhibitionSurveyMultiselectDao extends CrudRepository<ExhibitionSurveyMultiselect, Long> {

    @Query("select m.sundryId from ExhibitionSurveyMultiselect m where m.surveyId = ?1 and m.surveyField = ?2")
    List<Integer> findSundryIdBySurveyIdAndSurveyField(Long surveyId,String fieldName);

    List<ExhibitionSurveyMultiselect> findBySurveyId(Long surveyId);

    @Query("select m from ExhibitionSurveyMultiselect m,ExhibitionSurveyField f " +
            " where f.fieldName = m.surveyField and m.surveyId = ?1 and f.shortFlag=1")
    List<ExhibitionSurveyMultiselect> findByShortSurveyId(Long surveyId);

    @Modifying
    @Query("delete from ExhibitionSurveyMultiselect m where surveyId=?1")
    void deleteBySurveyId(Long surveyId);


    @Modifying
    @Query("delete from ExhibitionSurveyMultiselect m where surveyId=?1 and surveyField=?2")
    void deleteBySurveyIdAndField(Long surveyId,String surveyField);
}
