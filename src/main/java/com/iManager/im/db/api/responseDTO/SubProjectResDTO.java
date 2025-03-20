package com.iManager.im.db.api.responseDTO;

import java.util.UUID;

public class SubProjectResDTO {
    private UUID id;
    private String name;

    public SubProjectResDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public SubProjectResDTO() {
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
