package com.iManager.im.db.api.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @Column(nullable = false)
    String name;

    @ManyToOne
    @JoinColumn(name = "subProject_id", nullable = false)
    private SubProject subProject;

    public Status() {
    }

    public Status(UUID id, String name, SubProject subProject) {
        this.id = id;
        this.name = name;
        this.subProject = subProject;
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

    public SubProject getSubProject() {
        return subProject;
    }

    public void setSubProject(SubProject subProject) {
        this.subProject = subProject;
    }
}
