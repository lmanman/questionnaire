package com.visionet.letsdesk.app.exhibition.repository;

import com.visionet.letsdesk.app.exhibition.entity.ExhibitionSurvey;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ExhibitionSurveyDao extends PagingAndSortingRepository<ExhibitionSurvey, Long>, JpaSpecificationExecutor<ExhibitionSurvey> {

}
