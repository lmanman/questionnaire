package com.visionet.letsdesk.app.attachment.repository;

import com.visionet.letsdesk.app.attachment.entity.Document;
import org.springframework.data.repository.CrudRepository;

public interface DocumentDao extends CrudRepository<Document, Long> {
}
