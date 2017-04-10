package com.visionet.letsdesk.app.attachment.repository;

import com.visionet.letsdesk.app.attachment.entity.Photo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PhotoDao extends CrudRepository<Photo, Long> {

    List<Photo> findByRefId(Long refId);

    @Query("select p from Photo p,ExhibitionSurveyField f where p.refId=?1 and p.refType=f.fieldName and f.shortFlag=1 ")
    List<Photo> findByShortRefId(Long refId);
}
