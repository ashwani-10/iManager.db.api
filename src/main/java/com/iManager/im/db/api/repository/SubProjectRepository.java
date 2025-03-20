package com.iManager.im.db.api.repository;

import com.iManager.im.db.api.model.SubProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubProjectRepository extends JpaRepository<SubProject, UUID> {
}
