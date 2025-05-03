package com.iManager.im.db.api.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @Column
    String name;


    public Operation() {
    }

    public Operation(UUID id, String name) {
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
