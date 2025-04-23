package com.iManager.im.db.api.requestDTO;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubProjectReqDTO {
    private UUID id;
    private String name;
    private UUID projectId;
    private String repoName;

    public SubProjectReqDTO() {
    }

    public SubProjectReqDTO(UUID id, String name, UUID projectId,String repoName) {
        this.id = id;
        this.name = name;
        this.projectId = projectId;
        this.repoName = repoName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }
}
