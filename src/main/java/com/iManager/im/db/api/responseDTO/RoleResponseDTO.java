package com.iManager.im.db.api.responseDTO;

import java.util.UUID;

public class RoleResponseDTO {
    UUID id;
    String name;
    String description;

    public RoleResponseDTO() {
    }

    public RoleResponseDTO(UUID id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
