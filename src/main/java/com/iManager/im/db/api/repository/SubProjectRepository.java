package com.iManager.im.db.api.repository;

import com.iManager.im.db.api.model.Project;
import com.iManager.im.db.api.model.SubProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubProjectRepository extends JpaRepository<SubProject, UUID> {
    @Query("SELECT s FROM SubProject s LEFT JOIN FETCH s.tasks WHERE s.id = :id")
    Optional<SubProject> findByIdWithTasks(@Param("id") UUID id);

    @Query("SELECT s FROM SubProject s LEFT JOIN FETCH s.statusList WHERE s.id = :id")
    Optional<SubProject> findByIdWithStatus(@Param("id") UUID id);

    @Query("SELECT s FROM SubProject s LEFT JOIN FETCH s.members WHERE s.id = :id")
    Optional<SubProject> findByIdWithUsers(@Param("id") UUID id);
}
