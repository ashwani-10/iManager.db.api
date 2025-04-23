package com.iManager.im.db.api.utils;

import com.iManager.im.db.api.kafkaMessageDTO.TaskAssignedMessageDTO;
import com.iManager.im.db.api.model.Tasks;
import com.iManager.im.db.api.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class KafkaMapper {

    public TaskAssignedMessageDTO assignedMessageMapper(Tasks task, User user){
        TaskAssignedMessageDTO messageDTO = new TaskAssignedMessageDTO();
        messageDTO.setTaskTitle(task.getTitle());
        messageDTO.setAssignedMail(user.getEmail());
        messageDTO.setPriority(task.getPriority().toString());
        messageDTO.setDueDate(LocalDate.now());
        messageDTO.setAssignedName(user.getName());

        return messageDTO;
    }
}
