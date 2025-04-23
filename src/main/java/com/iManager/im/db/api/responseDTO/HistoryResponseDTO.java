package com.iManager.im.db.api.responseDTO;

public class HistoryResponseDTO {
    String historyMessage;
    String userName;

    public HistoryResponseDTO() {
    }

    public HistoryResponseDTO(String historyMessage, String userName) {
        this.historyMessage = historyMessage;
        this.userName = userName;
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
