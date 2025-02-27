package com.iManager.im.db.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(nullable = false,unique = true)
    String email;

    @Column(nullable = false)
    String otp;

}
