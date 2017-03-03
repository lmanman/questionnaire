package com.visionet.letsdesk.app.exhibition.repository;

import com.visionet.letsdesk.app.exhibition.entity.ExhibitionSurveyOtherOption;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExhibitionSurveyOtherOptionDao extends CrudRepository<ExhibitionSurveyOtherOption, Long> {

    List<ExhibitionSurveyOtherOption> findBySurveyId(Long surveyId);

    @Query("select m from ExhibitionSurveyOtherOption m,ExhibitionSurveyField f " +
            " where f.fieldName = m.surveyField and m.surveyId = ?1 and f.shortFlag=1")
    List<ExhibitionSurveyOtherOption> findByShortSurveyId(Long surveyId);

    @Query("SELECT f.fieldName,(select o.otherOption from ExhibitionSurveyOtherOption o where o.surveyField = f.fieldName and o.surveyId=?1) as other " +
            " from ExhibitionSurveyField f where f.delFlag=0 and EXISTS (SELECT 1 from Sundry d where d.type = f.fieldName and d.code = 'Z') ")
    List<Object[]> findTemplateBySurveyId(Long surveyId);

    @Modifying
    @Query("delete from ExhibitionSurveyOtherOption o where o.surveyId=?1")
    void deleteBySurveyId(Long surveyId);
}
