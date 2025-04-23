package com.iManager.im.db.api.model;

import jakarta.persistence.*;

@Entity
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticket_id")
    private String ticketId;

    @Column(name = "history_message")
    private String historyMessage;

    @Column(name = "userName")
    private String userName;

    public History() {
    }

    public History(Long id, String ticketId, String historyMessage, String userName) {
        this.id = id;
        this.ticketId = ticketId;
        this.historyMessage = historyMessage;
        this.userName = userName;
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

    public String getHistoryMessage() {
        return historyMessage;
    }

    public void setHistoryMessage(String historyMessage) {
        this.historyMessage = historyMessage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
