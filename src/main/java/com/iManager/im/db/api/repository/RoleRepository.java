package com.iManager.im.db.api.repository;

import com.iManager.im.db.api.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;


@Repository
public interface RoleRepository extends JpaRepository<Roles, UUID> {
    @Query("SELECT r FROM Roles r LEFT JOIN FETCH r.operations WHERE r.id = :id")
    Optional<Roles> findByIdWithOperations(@Param("id") UUID id);
}
