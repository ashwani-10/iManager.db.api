package com.iManager.im.db.api.model;

import com.iManager.im.db.api.enums.ProjectRole;
import com.iManager.im.db.api.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name ="users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false ,unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean isActive = false;

    @ElementCollection
    @CollectionTable(name = "user_project_roles", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "project_name")
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Map<String, ProjectRole> projectRole;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @OneToMany(mappedBy = "assignedUser")
    List<Tasks> assignedTasks; //  To remove

    @ManyToMany(mappedBy = "members")
    List<Project> projects;

    public User(UUID id, String name, String email, String password, Role role,
                Map<String, ProjectRole> projectRole, boolean isActive,
                Organization organization, List<Tasks> assignedTasks,
                List<Project> projects) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.projectRole = projectRole;
        this.isActive = isActive;
        this.organization = organization;
        this.assignedTasks = assignedTasks;
        this.projects = projects;
    }

    public User() {
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public boolean isActive() {
        return isActive;
    }

    public Organization getOrganization() {
        return organization;
    }

    public List<Tasks> getAssignedTasks() {
        return assignedTasks;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public void setAssignedTasks(List<Tasks> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public String getPassword() {
        return this.password;
    }


    public String getEmail() {
        return this.email;
    }

    public Map<String, ProjectRole> getProjectRole() {
        return projectRole;
    }

    public void setProjectRole(Map<String, ProjectRole> projectRole) {
        this.projectRole = projectRole;
    }
}
