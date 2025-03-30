package com.iManager.im.db.api.repository;

import com.iManager.im.db.api.model.Organization;
import com.iManager.im.db.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.subProjectRole WHERE u.id = :id")
    Optional<User> findByIdWithSubProjectRole(@Param("id") UUID id);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.projects WHERE u.id = :id")
    Optional<User> findByIdWithSubProjects(@Param("id") UUID id);
}
