package com.iManager.im.db.api.controller;

import com.iManager.im.db.api.enums.Priority;
import com.iManager.im.db.api.kafkaMessageDTO.TaskAssignedMessageDTO;
import com.iManager.im.db.api.model.*;
import com.iManager.im.db.api.repository.*;
import com.iManager.im.db.api.requestDTO.TaskRequestDTO;
import com.iManager.im.db.api.responseDTO.HistoryResponseDTO;
import com.iManager.im.db.api.responseDTO.TaskResponseDTO;
import com.iManager.im.db.api.service.MessageProducer;
import com.iManager.im.db.api.utils.KafkaMapper;
import com.iManager.im.db.api.utils.Mapper;
import com.iManager.im.db.api.utils.ValidateAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/db/api/task")
public class TaskController {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    Mapper mapper;
    @Autowired
    SubProjectRepository subProjectRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    StatusRepository statusRepository;
    @Autowired
    MessageProducer messageProducer;
    @Autowired
    KafkaMapper kafkaMapper;
    @Autowired
    OperationRepository operationRepository;
    @Autowired
    ValidateAuth validateAuth;
    @Autowired
    HistoryRepo historyRepo;

    @PostMapping("/create/{loggedId}")
    public ResponseEntity createTask(@PathVariable String loggedId,
                                     @RequestBody TaskRequestDTO requestDTO){

        UUID opId = UUID.fromString("cf0cd34e-5e9c-4cd1-89a8-f94eadc6ea15");
        Operation operation = operationRepository.findById(opId).orElseThrow();

        if (validateAuth.validateUser(loggedId,operation)) {
            try {
                Tasks task = mapper.createTask(requestDTO);
                User user = userRepository.findById(requestDTO.getAssignedUser()).orElseThrow();
                task.setAssignedUser(user);
                taskRepository.save(task);

                TaskResponseDTO responseDTO = mapper.taskResponse(task, user);
                TaskAssignedMessageDTO messageDTO = kafkaMapper.assignedMessageMapper(task, user);
                messageProducer.taskAssignee(messageDTO);

                return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
            } catch (Exception e) {
                System.out.println("Failed creating task");
                return new ResponseEntity("Failed creating task", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>("You are not authorized for this operation",HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity deleteTask(@PathVariable UUID taskId){
        try {
            Tasks task = taskRepository.findById(taskId)
                    .orElseThrow(()-> new RuntimeException("Task Does not exists"));
            taskRepository.delete(task);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            System.out.println("Failed deleting task");
            return new ResponseEntity("Failed deleting",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{loggedUserName}")
    public ResponseEntity updateTask(@PathVariable String loggedUserName,
                                     @RequestBody TaskRequestDTO reqDTO){
        try {
            Tasks tasks = taskRepository.findById(reqDTO.getId())
                    .orElseThrow(()-> new RuntimeException("Task does not exists"));
            User user = userRepository.findById(reqDTO.getAssignedUser()).orElseThrow();

            if(!tasks.getAssignedUser().getId().equals(user.getId())){

                History history = new History();
                history.setTicketId(tasks.getTicketId());
                String message = "Assignee Changed: from "
                        +(tasks.getAssignedUser().getName()).toUpperCase()
                        +" to "+user.getName().toUpperCase();
                tasks.setAssignedUser(user);
                history.setHistoryMessage(message);
                history.setUserName(loggedUserName);
                historyRepo.save(history);
            }
            if(!reqDTO.getTitle().isEmpty() && !tasks.getTitle().equals(reqDTO.getTitle())){
                History history = new History();
                history.setTicketId(tasks.getTicketId());
                String message = "Title Changed: from "
                        +(tasks.getTitle()).toUpperCase()
                        +" to "+reqDTO.getTitle().toUpperCase();
                tasks.setTitle(reqDTO.getTitle());
                history.setHistoryMessage(message);
                history.setUserName(loggedUserName);
                historyRepo.save(history);
            }
            if(!reqDTO.getDescription().isEmpty() && !tasks.getDescription().equals(reqDTO.getDescription())){
                tasks.setDescription(reqDTO.getDescription());
            }
            if(!tasks.getStatus().getId().equals(reqDTO.getStatusId())){
                Status status = statusRepository.findById(reqDTO.getStatusId()).orElseThrow();
                String message = "Status Changed: from "
                        +(tasks.getStatus().getName()).toUpperCase();
                tasks.setStatus(status);
                String statusName = status.getName().toUpperCase();

                History history = new History();
                history.setTicketId(tasks.getTicketId());
                message = message +" to "+statusName;
                history.setHistoryMessage(message);
                history.setUserName(loggedUserName);
                historyRepo.save(history);
            }
            if(!reqDTO.getPriority().isEmpty() && !tasks.getPriority().equals(Priority.valueOf(reqDTO.getPriority()))){
                String message = "Priority Changed: from "
                        +(tasks.getPriority().toString()).toUpperCase();

                tasks.setPriority(Priority.valueOf(reqDTO.getPriority()));

                History history = new History();
                history.setTicketId(tasks.getTicketId());
                message = message +" to "+reqDTO.getPriority().toUpperCase();
                history.setHistoryMessage(message);
                history.setUserName(loggedUserName);
                historyRepo.save(history);
            }
            taskRepository.save(tasks);
            return new ResponseEntity<Object>("task updated successfully", HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println("Failed updating task");
            return new ResponseEntity("Failed updating",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{subProjectId}")
    public ResponseEntity getSubProject(@PathVariable UUID subProjectId){
        try {
            SubProject subProject = subProjectRepository.findByIdWithTasks(subProjectId).orElseThrow();
            List<Tasks> tasksList = subProject.getTasks();
            List<TaskResponseDTO> taskResponseDTOS = new ArrayList<>();
            for(Tasks task : tasksList){
                User user = userRepository.findById(task.getAssignedUser().getId()).orElseThrow();
                TaskResponseDTO responseDTO = mapper.taskResponse(task,user);
                taskResponseDTOS.add(responseDTO);
            }
            return new ResponseEntity(taskResponseDTOS,HttpStatus.OK);
        }catch (Exception e){
            System.out.println("Failed fetching subProjects");
            return new ResponseEntity("Failed fetching",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/history/{ticketId}")
    public ResponseEntity getTaskHistory(@PathVariable String ticketId){
        try{
            List<History> historyList = historyRepo.findByTicketId(ticketId);
            List<HistoryResponseDTO> responseDTOList = mapper.historyRespnse(historyList);
            return ResponseEntity.ok(responseDTOList);
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
