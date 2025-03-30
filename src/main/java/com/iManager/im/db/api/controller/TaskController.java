package com.iManager.im.db.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iManager.im.db.api.enums.Priority;
import com.iManager.im.db.api.model.*;
import com.iManager.im.db.api.repository.StatusRepository;
import com.iManager.im.db.api.repository.SubProjectRepository;
import com.iManager.im.db.api.repository.TaskRepository;
import com.iManager.im.db.api.repository.UserRepository;
import com.iManager.im.db.api.requestDTO.TaskRequestDTO;
import com.iManager.im.db.api.responseDTO.TaskResponseDTO;
import com.iManager.im.db.api.responseDTO.UserResponseDTO;
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
    ObjectMapper objectMapper;
    @Autowired
    SubProjectRepository subProjectRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    StatusRepository statusRepository;

    @PostMapping("/create")
    public ResponseEntity createTask(@RequestBody TaskRequestDTO requestDTO){
        try {
            SubProject subProject = subProjectRepository.findById(requestDTO.getSubProjectID())
                    .orElseThrow();
            Tasks task = new Tasks();
            task.setSubProject(subProject);
            task.setTitle(requestDTO.getTitle());
            task.setDescription(requestDTO.getDescription());
            Status status = statusRepository.findById(requestDTO.getStatusId()).orElseThrow();
            task.setStatus(status);
            task.setPriority(Priority.valueOf(requestDTO.getPriority()));
            User user = userRepository.findById(requestDTO.getAssignedUser()).orElseThrow();
            task.setAssignedUser(user);
            taskRepository.save(task);
            TaskResponseDTO responseDTO = new TaskResponseDTO();
            responseDTO.setId(task.getId());
            responseDTO.setTitle(task.getTitle());
            responseDTO.setDescription(task.getDescription());
            responseDTO.setStatus(task.getStatus().getName());
            responseDTO.setPriority(task.getPriority().toString());
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

    @PutMapping("/update")
    public ResponseEntity updateTask(@RequestBody TaskRequestDTO reqDTO){
        try {
            Tasks tasks = taskRepository.findById(reqDTO.getId())
                    .orElseThrow(()-> new RuntimeException("Task does not exists"));
            User user = userRepository.findById(reqDTO.getAssignedUser()).orElseThrow();

            if(tasks.getAssignedUser() != user){
                tasks.setAssignedUser(user);
            }
            if(tasks.getTitle() != reqDTO.getTitle()){
                tasks.setTitle(reqDTO.getTitle());
            }
            if(tasks.getDescription() != reqDTO.getDescription()){
                tasks.setDescription(reqDTO.getDescription());
            }
            if(tasks.getStatus().getId() != reqDTO.getStatusId()){
                Status status = statusRepository.findById(reqDTO.getStatusId()).orElseThrow();
                tasks.setStatus(status);
            }
            if(tasks.getPriority() != Priority.valueOf(reqDTO.getPriority())){
                tasks.setPriority(Priority.valueOf(reqDTO.getPriority()));
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
                TaskResponseDTO responseDTO = new TaskResponseDTO();
                responseDTO.setId(task.getId());
                responseDTO.setTitle(task.getTitle());
                responseDTO.setDescription(task.getDescription());
                responseDTO.setStatus(task.getStatus().getName());
                responseDTO.setPriority(task.getPriority().toString());
                User user = userRepository.findById(task.getAssignedUser().getId()).orElseThrow();
                UserResponseDTO userResponseDTO = new UserResponseDTO();
                userResponseDTO.setId(user.getId());
                userResponseDTO.setName(user.getName());
                responseDTO.setAssignedUsers(userResponseDTO);
                taskResponseDTOS.add(responseDTO);
            }
            return new ResponseEntity(taskResponseDTOS,HttpStatus.OK);
        }catch (Exception e){
            System.out.println("Failed fetching subProjects");
            return new ResponseEntity("Failed fetching",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
