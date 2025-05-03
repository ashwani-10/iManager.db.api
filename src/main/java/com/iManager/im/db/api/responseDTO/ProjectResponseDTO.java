package com.iManager.im.db.api.responseDTO;

import java.time.Instant;
import java.util.UUID;

public class ProjectResponseDTO {
    private UUID id;
    private String name;
    private Instant createdAt;


    public ProjectResponseDTO(UUID id, String name, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public ProjectResponseDTO() {
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
