package com.iManager.im.db.api.kafkaMessageDTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskAssignedMessageDTO {
    String taskTitle;
    String assignedName;
    String assignedMail;
    String Priority;
    LocalDate dueDate;

    public TaskAssignedMessageDTO() {
    }

    public TaskAssignedMessageDTO(String taskTitle, String assignedName, String assignedMail,
                                  String priority, LocalDate dueDate) {
        this.taskTitle = taskTitle;
        this.assignedName = assignedName;
        this.assignedMail = assignedMail;
        Priority = priority;
        this.dueDate = dueDate;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getAssignedMail() {
        return assignedMail;
    }

    public void setAssignedMail(String assignedMail) {
        this.assignedMail = assignedMail;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getAssignedName() {
        return assignedName;
    }

    public void setAssignedName(String assignedName) {
        this.assignedName = assignedName;
    }
}
