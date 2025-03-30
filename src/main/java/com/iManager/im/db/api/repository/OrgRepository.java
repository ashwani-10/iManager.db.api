package com.iManager.im.db.api.repository;

import com.iManager.im.db.api.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrgRepository extends JpaRepository<Organization, UUID> {
    Optional<Organization> findByEmail(String email);

    @Query("SELECT o FROM Organization o LEFT JOIN FETCH o.projects WHERE o.id = :id")
    Optional<Organization> findByIdWithProjects(@Param("id") UUID id);

    @Query("SELECT o FROM Organization o LEFT JOIN FETCH o.roles WHERE o.id = :id")
    Optional<Organization> findByIdWithRoles(@Param("id") UUID id);

    @Query("SELECT o FROM Organization o LEFT JOIN FETCH o.users WHERE o.id = :id")
    Optional<Organization> findByIdWithUsers(@Param("id") UUID id);




}
