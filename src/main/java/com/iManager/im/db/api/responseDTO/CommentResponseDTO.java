package com.iManager.im.db.api.responseDTO;

import java.time.Instant;
import java.util.UUID;

public class CommentResponseDTO {
    UUID id;
    String message;
    String userName;
    Instant createdAt;

    public CommentResponseDTO() {
    }

    public CommentResponseDTO(UUID id, String message, String userName, Instant createdAt) {
        this.id = id;
        this.message = message;
        this.userName = userName;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
