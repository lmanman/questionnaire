package com.visionet.letsdesk.app.attachment.repository;

import com.visionet.letsdesk.app.attachment.entity.Photo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PhotoDao extends CrudRepository<Photo, Long> {

    List<Photo> findByRefId(Long refId);

}
