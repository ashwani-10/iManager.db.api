package com.iManager.im.db.api.requestDTO;

import java.util.List;
import java.util.UUID;

public class TaskRequestDTO {
    private String title;

    private String description;

    private String status;

    private String priority;

    private UUID assignedUser;

    private UUID subProjectID;

    public TaskRequestDTO() {
    }

    public TaskRequestDTO(String title, String description, String status, String priority,
                          UUID assignedUser, UUID subProjectID) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.assignedUser = assignedUser;
        this.subProjectID = subProjectID;
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

    public UUID getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUsers(UUID assignedUser) {
        this.assignedUser = assignedUser;
    }

    public UUID getSubProjectID() {
        return subProjectID;
    }

    public void setSubProjectID(UUID subProjectID) {
        this.subProjectID = subProjectID;
    }
}
