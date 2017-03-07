package com.visionet.letsdesk.app.exhibition.repository;

import com.visionet.letsdesk.app.exhibition.entity.ExhibitionSurvey;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ExhibitionSurveyDao extends PagingAndSortingRepository<ExhibitionSurvey, Long>, JpaSpecificationExecutor<ExhibitionSurvey> {

    List<ExhibitionSurvey> findByFormId(Long formId);

}
