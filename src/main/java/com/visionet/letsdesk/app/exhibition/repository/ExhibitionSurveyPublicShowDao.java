package com.visionet.letsdesk.app.exhibition.repository;

import com.visionet.letsdesk.app.exhibition.entity.ExhibitionSurveyPublicShow;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExhibitionSurveyPublicShowDao extends CrudRepository<ExhibitionSurveyPublicShow, Long> {

    ExhibitionSurveyPublicShow findBySurveyId(Long surveyId);
}
