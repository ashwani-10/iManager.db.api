package com.iManager.im.db.api.repository;

import com.iManager.im.db.api.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    @Query("SELECT c FROM Comment c WHERE c.task.id = :taskId")
    List<Comment> findCommentsByTaskId(@Param("taskId") UUID taskId);
}
