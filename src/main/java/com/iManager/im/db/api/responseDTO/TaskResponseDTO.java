package com.iManager.im.db.api.responseDTO;

import java.util.UUID;

public class TaskResponseDTO {
    private UUID id;
    private String title;
    private String description;
    private String status;
    private String priority;
    UserResponseDTO assignedUser;

    public TaskResponseDTO() {
    }

    public TaskResponseDTO(UUID id, String title, String description,
                           String status, String priority, UserResponseDTO assignedUser) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.assignedUser = assignedUser;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public UserResponseDTO getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUsers(UserResponseDTO assignedUser) {
        this.assignedUser = assignedUser;
    }
}
