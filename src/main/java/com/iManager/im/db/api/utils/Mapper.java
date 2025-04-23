package com.iManager.im.db.api.utils;

import com.iManager.im.db.api.enums.Priority;
import com.iManager.im.db.api.model.*;
import com.iManager.im.db.api.repository.*;
import com.iManager.im.db.api.requestDTO.TaskRequestDTO;
import com.iManager.im.db.api.responseDTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class Mapper {
    @Autowired
    OperationRepository operationRepository;
    @Autowired
    SubProjectRepository subProjectRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    StatusRepository statusRepository;

    public List<Operation> mapOpertaionList(List<UUID> operationIdList){
        List<Operation> operationList = new ArrayList<>();
        for(UUID id : operationIdList){
            Operation op = operationRepository.findById(id).orElseThrow();
            operationList.add(op);
        }
        return operationList;
    }

    public Tasks createTask(TaskRequestDTO requestDTO){
        SubProject subProject = subProjectRepository.findById(requestDTO.getSubProjectID())
                .orElseThrow();
        Random random = new Random();
        String ticketId = "IMTK"+String.valueOf(100000 + random.nextInt(900000));
        Tasks task = new Tasks();
        task.setTicketId(ticketId);
        task.setSubProject(subProject);
        task.setTitle(requestDTO.getTitle());
        task.setDescription(requestDTO.getDescription());
        Status status = statusRepository.findById(requestDTO.getStatusId()).orElseThrow();
        task.setStatus(status);
        task.setPriority(Priority.valueOf(requestDTO.getPriority()));
        return task;
    }

    public TaskResponseDTO taskResponse(Tasks task,User user){
        TaskResponseDTO responseDTO = new TaskResponseDTO();
        responseDTO.setTicketId(task.getTicketId());
        responseDTO.setId(task.getId());
        responseDTO.setTitle(task.getTitle());
        responseDTO.setDescription(task.getDescription());
        responseDTO.setStatus(task.getStatus().getName());
        responseDTO.setPriority(task.getPriority().toString());
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setName(user.getName());
        responseDTO.setAssignedUsers(userResponseDTO);
        return responseDTO;
    }

    public ProjectResponseDTO projectResponse(Project project){
        ProjectResponseDTO projectResponseDTO = new ProjectResponseDTO();
        projectResponseDTO.setId(project.getId());
        projectResponseDTO.setName(project.getName());
        projectResponseDTO.setCreatedAt(project.getCreatedAt());

        return projectResponseDTO;
    }

    public RoleResponseDTO roleResponse(Roles role){
        RoleResponseDTO responseDTO = new RoleResponseDTO();
        responseDTO.setId(role.getId());
        responseDTO.setName(role.getName());
        responseDTO.setDescription(role.getDescription());

        return responseDTO;
    }

    public List<OperationResponseDTO> operationResponse(List<Operation> operations){
        List<OperationResponseDTO> responseDTOList = new ArrayList<>();
        for(Operation operation : operations){
            OperationResponseDTO operationResponseDTO = new OperationResponseDTO();
            operationResponseDTO.setId(operation.getId());
            operationResponseDTO.setName(operation.getName());
            responseDTOList.add(operationResponseDTO);
        }
        return responseDTOList;
    }

    public List<PullResponseDTO> pullResponseDTOS(List<PullRequest> pullRequests){
        List<PullResponseDTO> pullResponseDTOS = new ArrayList<>();

        for(PullRequest pr : pullRequests){
            PullResponseDTO responseDTO = new PullResponseDTO();
            responseDTO.setId(pr.getId());
            responseDTO.setAuthor(pr.getAuthor());
            responseDTO.setPrTitle(pr.getPrTitle());
            responseDTO.setPrUrl(pr.getPrUrl());
            responseDTO.setState(pr.getState());
            responseDTO.setBaseBranch(pr.getBaseBranch());
            responseDTO.setHeadBranch(pr.getHeadBranch());
            pullResponseDTOS.add(responseDTO);
        }
        return pullResponseDTOS;
    }

    public List<HistoryResponseDTO> historyRespnse(List<History> historyList) {
        List<HistoryResponseDTO> responseDTOList = new ArrayList<>();
        for(History history : historyList){
            HistoryResponseDTO responseDTO = new HistoryResponseDTO();
            responseDTO.setUserName(history.getUserName());
            responseDTO.setHistoryMessage(history.getHistoryMessage());
            responseDTOList.add(responseDTO);
        }
        return responseDTOList;
    }
}
