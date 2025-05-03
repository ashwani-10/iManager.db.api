package com.iManager.im.db.api.controller;

import com.iManager.im.db.api.model.*;
import com.iManager.im.db.api.repository.*;
import com.iManager.im.db.api.requestDTO.StatusRequestDTO;
import com.iManager.im.db.api.responseDTO.StatusResponseDTO;
import com.iManager.im.db.api.utils.ValidateAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/db/api/status")
public class StatusController {
    @Autowired
    SubProjectRepository subProjectRepository;
    @Autowired
    StatusRepository statusRepository;
    @Autowired
    OperationRepository operationRepository;
    @Autowired
    ValidateAuth validateAuth;

    @PostMapping("/create/{subProjectId}/{loggedId}")
    public ResponseEntity createStatus(@PathVariable UUID subProjectId,
                                       @PathVariable String loggedId,
                                       @RequestBody StatusRequestDTO requestDTO){

        UUID opId = UUID.fromString("bd56658c-b46f-47f0-804f-4f59cee7d8a7");
        Operation operation = operationRepository.findById(opId).orElseThrow();

        if (validateAuth.validateUser(loggedId,operation)){
            try {
                SubProject subProject = subProjectRepository.findById(subProjectId).orElseThrow();
                Status status = new Status();
                status.setName(requestDTO.getName().toLowerCase());
                status.setSubProject(subProject);
                statusRepository.save(status);

                StatusResponseDTO responseDTO = new StatusResponseDTO();
                responseDTO.setId(status.getId());
                responseDTO.setName(status.getName());
                return new ResponseEntity(responseDTO, HttpStatus.CREATED);
            }catch (Exception e){
                return new ResponseEntity("Failed creating new Status",HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>("You are not authorized for this operation",HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/get/{subProjectId}")
    public ResponseEntity getStatus(@PathVariable UUID subProjectId) {
        SubProject subProject = subProjectRepository.findByIdWithStatus(subProjectId).orElseThrow();
        List<Status> statusList = subProject.getStatusList();
        List<StatusResponseDTO> responseDTOList = new ArrayList<>();

        for(Status status : statusList){
            StatusResponseDTO responseDTO = new StatusResponseDTO();
            responseDTO.setId(status.getId());
            responseDTO.setName(status.getName());
            responseDTOList.add(responseDTO);
        }

        return ResponseEntity.ok(responseDTOList);
    }
}
