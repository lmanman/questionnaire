package com.visionet.letsdesk.app.attachment.repository;

import com.visionet.letsdesk.app.attachment.entity.Photo;
import org.springframework.data.repository.CrudRepository;

public interface PhotoDao extends CrudRepository<Photo, Long> {
}
