package com.iManager.im.db.api.requestDTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentRequestDTO {
    UUID id;
    String message;
    UUID task_id;
    UUID user_id;

    public CommentRequestDTO() {
    }

    public CommentRequestDTO(UUID id, String message, UUID task_id, UUID user_id) {
        this.id = id;
        this.message = message;
        this.task_id = task_id;
        this.user_id = user_id;
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

    public UUID getTask_id() {
        return task_id;
    }

    public void setTask_id(UUID task_id) {
        this.task_id = task_id;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }
}
