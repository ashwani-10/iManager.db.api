package com.iManager.im.db.api.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false,unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubProject> subProjects;

    public Project(UUID id, String name, Organization organization,
                   List<SubProject> subProjects) {
        this.id = id;
        this.name = name;
        this.organization = organization;
        this.subProjects = subProjects;
    }

    public Project() {
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

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<SubProject> getSubProjects() {
        return subProjects;
    }

    public void setSubProjects(List<SubProject> subProjects) {
        this.subProjects = subProjects;
    }
}
