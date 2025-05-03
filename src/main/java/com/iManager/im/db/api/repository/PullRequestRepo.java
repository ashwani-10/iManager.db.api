package com.iManager.im.db.api.repository;

import com.iManager.im.db.api.model.PullRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PullRequestRepo extends JpaRepository<PullRequest, Long> {
    List<PullRequest> findByTicketId(String ticketId);
}
