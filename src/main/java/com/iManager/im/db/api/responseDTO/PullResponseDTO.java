package com.iManager.im.db.api.responseDTO;

public class PullResponseDTO {
    private Long id;
    private String prTitle;
    private String prUrl;
    private String baseBranch;
    private String headBranch;
    private String state;
    private String author;

    public PullResponseDTO() {
    }

    public PullResponseDTO(Long id, String prTitle, String prUrl, String baseBranch,
                           String headBranch, String state, String author) {
        this.id = id;
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
