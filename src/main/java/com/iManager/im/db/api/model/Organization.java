package com.iManager.im.db.api.model;

import com.iManager.im.db.api.enums.Role;
import com.iManager.im.db.api.enums.Subscription;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    Subscription subscription;

    @Enumerated(EnumType.STRING)
    Role role;

    @Column(name = "order_id",nullable = false)
    String orderId;

    @Column(name = "github_token")
    String githubToken;

    @OneToMany(mappedBy = "organization",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<User> users;

    @OneToMany(mappedBy = "organization",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Project> projects;

    @OneToMany(mappedBy = "organization",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Roles> roles;

    @Column(name = "logo_url")
    private String logoUrl;

    public Organization() {
    }

    public Organization(UUID id, String name, String email, String password, Subscription subscription,
                        Role role, String orderId, List<User> users, List<Project> projects,
                        List<Roles> roles,String githubToken,String logoUrl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.subscription = subscription;
        this.role = role;
        this.orderId = orderId;
        this.users = users;
        this.projects = projects;
        this.roles = roles;
        this.githubToken = githubToken;
        this.logoUrl = logoUrl;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getGithubToken() {
        return githubToken;
    }

    public void setGithubToken(String githubToken) {
        this.githubToken = githubToken;
    }
}

//Organization org = findByEmail(orgEamil);
//String orderId = org.getOrderId();
//
//Payment payment = findByOrderId(orderId);
