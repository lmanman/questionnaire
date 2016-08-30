package com.visionet.letsdesk.app.attachment.repository;

import com.visionet.letsdesk.app.attachment.entity.Video;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface VideoDao extends CrudRepository<Video, Long> {

}
