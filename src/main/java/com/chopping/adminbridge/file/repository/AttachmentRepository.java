package com.chopping.adminbridge.file.repository;

import com.chopping.adminbridge.file.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
}