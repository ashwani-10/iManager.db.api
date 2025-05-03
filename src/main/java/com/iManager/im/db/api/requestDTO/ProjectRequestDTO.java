package com.iManager.im.db.api.requestDTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectRequestDTO {
    private UUID id;
    private String name;
    private UUID orgId;

    public ProjectRequestDTO(UUID id, String name, UUID orgId) {
        this.id = id;
        this.name = name;
        this.orgId = orgId;
    }

    public ProjectRequestDTO() {
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

    public UUID getOrgId() {
        return orgId;
    }

    public void setOrgId(UUID orgId) {
        this.orgId = orgId;
    }
}
