package com.iManager.im.db.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iManager.im.db.api.enums.Priority;
import com.iManager.im.db.api.enums.TaskStatus;
import com.iManager.im.db.api.model.*;
import com.iManager.im.db.api.repository.SubProjectRepository;
import com.iManager.im.db.api.repository.TaskRepository;
import com.iManager.im.db.api.repository.UserRepository;
import com.iManager.im.db.api.requestDTO.ProjectRequestDTO;
import com.iManager.im.db.api.requestDTO.TaskRequestDTO;
import com.iManager.im.db.api.responseDTO.ProjectResponseDTO;
import com.iManager.im.db.api.responseDTO.TaskResponseDTO;
import com.iManager.im.db.api.responseDTO.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/db/api/task")
public class TaskController {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    SubProjectRepository subProjectRepository;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity createTask(@RequestBody TaskRequestDTO requestDTO){
        try {
            SubProject subProject = subProjectRepository.findById(requestDTO.getSubProjectID())
                    .orElseThrow();
            Tasks task = new Tasks();
            task.setSubProject(subProject);
            task.setTitle(requestDTO.getTitle());
            task.setDescription(requestDTO.getDescription());
            task.setStatus(TaskStatus.valueOf(requestDTO.getStatus()));
            task.setPriority(Priority.valueOf(requestDTO.getPriority()));
            User user = userRepository.findById(requestDTO.getAssignedUser()).orElseThrow();
            task.setAssignedUser(user);
            taskRepository.save(task);
            TaskResponseDTO responseDTO = new TaskResponseDTO();
            responseDTO.setId(task.getId());
            responseDTO.setTitle(task.getTitle());
            responseDTO.setDescription(task.getDescription());
            responseDTO.setStatus(requestDTO.getStatus());
            responseDTO.setPriority(requestDTO.getPriority());
            UserResponseDTO userResponseDTO = new UserResponseDTO();
            userResponseDTO.setId(user.getId());
            userResponseDTO.setName(user.getName());
            responseDTO.setAssignedUsers(userResponseDTO);
            return new ResponseEntity<>(responseDTO,HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println("Failed creating project");
            return new ResponseEntity("Failed creating",HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
}
