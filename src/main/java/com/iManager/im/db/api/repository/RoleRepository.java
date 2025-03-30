package com.iManager.im.db.api.repository;

import com.iManager.im.db.api.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface RoleRepository extends JpaRepository<Roles, UUID> {
}
