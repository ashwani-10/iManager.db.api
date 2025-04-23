package com.iManager.im.db.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class PullRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ticketId;          // e.g., IMTK198585
    private String prTitle;           // e.g., github repos list added...
    private String prUrl;             // PR URL from GitHub
    private String baseBranch;        // main
    private String headBranch;        // dev/IMTK198585
    private String state;             // open / closed     // true / false
    private String author;

    public PullRequest() {
    }

    public PullRequest(Long id, String ticketId, String prTitle, String prUrl, String baseBranch,
                       String headBranch, String state, String author) {
        this.id = id;
        this.ticketId = ticketId;
        this.prTitle = prTitle;
        this.prUrl = prUrl;
        this.baseBranch = baseBranch;
        this.headBranch = headBranch;
        this.state = state;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getPrTitle() {
        return prTitle;
    }

    public void setPrTitle(String prTitle) {
        this.prTitle = prTitle;
    }

    public String getPrUrl() {
        return prUrl;
    }

    public void setPrUrl(String prUrl) {
        this.prUrl = prUrl;
    }

    public String getBaseBranch() {
        return baseBranch;
    }

    public void setBaseBranch(String baseBranch) {
        this.baseBranch = baseBranch;
    }

    public String getHeadBranch() {
        return headBranch;
    }

    public void setHeadBranch(String headBranch) {
        this.headBranch = headBranch;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
