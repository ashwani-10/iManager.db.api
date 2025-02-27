package com.iManager.im.db.api.repository;

import com.iManager.im.db.api.model.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository<OTP,Integer> {
    OTP findByEmail(String email);
}
