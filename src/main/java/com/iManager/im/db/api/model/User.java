package com.iManager.im.db.api.model;

import com.iManager.im.db.api.enums.Role;
import jakarta.persistence.*;

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

    @OneToMany
    @JoinTable(
            name = "user_subProject_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @MapKeyColumn(name = "subProject_id") // Key column for the map
    private Map<UUID, Roles> subProjectRole;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @OneToMany(mappedBy = "assignedUser")
    List<Tasks> assignedTasks; //  To remove

    @ManyToMany(mappedBy = "members")
    List<SubProject> projects;

    public User(UUID id, String name, String email, String password, Role role,
                Map<UUID, Roles> subProjectRole, boolean isActive,
                Organization organization, List<Tasks> assignedTasks,
                List<SubProject> projects) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.subProjectRole = subProjectRole;
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

    public List<SubProject> getProjects() {
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

    public void setProjects(List<SubProject> projects) {
        this.projects = projects;
    }

    public String getPassword() {
        return this.password;
    }


    public String getEmail() {
        return this.email;
    }

    public Map<UUID, Roles> getSubProjectRole() {
        return subProjectRole;
    }

    public void setSubProjectRole(Map<UUID, Roles> subProjectRole) {
        this.subProjectRole = subProjectRole;
    }
}
