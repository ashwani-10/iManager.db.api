package com.iManager.im.db.api.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class SubProject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "project_id",nullable = false)
    private Project project;

    @OneToMany(mappedBy = "subProject",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Tasks> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "subProject",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Status> statusList;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "members",
            joinColumns = @JoinColumn(name = "subProject_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> members;

    public SubProject() {
    }

    public SubProject(UUID id, String name, Project project, List<Tasks> tasks,
                      List<Status> statusList, List<User> members) {
        this.id = id;
        this.name = name;
        this.project = project;
        this.tasks = tasks;
        this.statusList = statusList;
        this.members = members;
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Tasks> getTasks() {
        return tasks;
    }

    public void setTasks(List<Tasks> tasks) {
        this.tasks = tasks;
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }
}
