package com.iManager.im.db.api.repository;

import com.iManager.im.db.api.model.Status;
import org.apache.kafka.common.metrics.Stat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StatusRepository extends JpaRepository<Status, UUID> {
    Optional<Status> findByName(String name);
}
