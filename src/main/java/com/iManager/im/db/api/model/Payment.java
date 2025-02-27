package com.iManager.im.db.api.model;

import com.iManager.im.db.api.enums.PaymentStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String orderId; // Razorpay Order ID

    @Column(columnDefinition = "TEXT", nullable = false)
    private String orgData; // Store OrgRequestDTO as JSON

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // "PENDING", "PAID", "FAILED"

    @Column(nullable = false)
    private LocalDateTime created_At = LocalDateTime.now();

    public Payment(UUID id, String orderId, String orgData,
                   PaymentStatus status, LocalDateTime created_At) {
        this.id = id;
        this.orderId = orderId;
        this.orgData = orgData;
        this.status = status;
        this.created_At = created_At;
    }

    public Payment() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrgData() {
        return orgData;
    }

    public void setOrgData(String orgData) {
        this.orgData = orgData;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreated_At() {
        return created_At;
    }

    public void setCreated_At(LocalDateTime created_At) {
        this.created_At = created_At;
    }
}
