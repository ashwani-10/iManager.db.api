package com.iManager.im.db.api.repository;

import com.iManager.im.db.api.model.Organization;
import com.iManager.im.db.api.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    @Query("SELECT p FROM Project p LEFT JOIN FETCH p.subProjects WHERE p.id = :id")
    Optional<Project> findByIdWithSubProjects(@Param("id") UUID id);
}
