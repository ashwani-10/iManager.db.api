package com.iManager.im.db.api.requestDTO;

import java.util.UUID;

public class TaskRequestDTO {
    private UUID id;

    private String title;

    private String description;

    private UUID statusId;

    private String priority;

    private UUID assignedUser;

    private UUID subProjectID;

    public TaskRequestDTO() {
    }

    public TaskRequestDTO(UUID id, String title, String description,
                          UUID statusId, String priority, UUID assignedUser, UUID subProjectID) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.statusId = statusId;
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

    public UUID getStatusId() {
        return statusId;
    }

    public void setStatusId(UUID statusId) {
        this.statusId = statusId;
    }

    public UUID getSubProjectID() {
        return subProjectID;
    }

    public void setSubProjectID(UUID subProjectID) {
        this.subProjectID = subProjectID;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setAssignedUser(UUID assignedUser) {
        this.assignedUser = assignedUser;
    }
}
