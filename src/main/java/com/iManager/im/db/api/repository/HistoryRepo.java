package com.iManager.im.db.api.repository;

import com.iManager.im.db.api.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepo extends JpaRepository<History,Long> {
    List<History> findByTicketId(String ticketId);
}
