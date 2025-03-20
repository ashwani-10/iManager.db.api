package com.iManager.im.db.api.responseDTO;

import java.util.UUID;

public class UserResponseDTO {
    private UUID id;
    private String name;

    public UserResponseDTO() {
    }

    public UserResponseDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
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
}
