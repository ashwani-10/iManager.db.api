package com.iManager.im.db.api.model;

import com.iManager.im.db.api.enums.Priority;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String title;

    private String description;

    @OneToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "SubProject_id")
    private SubProject subProject;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User assignedUser;

    public Tasks() {
    }
    public Tasks(UUID id, String title, String description, Status status,
                 Priority priority, SubProject subProject, User assignedUser) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.subProject = subProject;
        this.assignedUser = assignedUser;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public SubProject getSubProject() {
        return subProject;
    }

    public void setSubProject(SubProject subProject) {
        this.subProject = subProject;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }
}
