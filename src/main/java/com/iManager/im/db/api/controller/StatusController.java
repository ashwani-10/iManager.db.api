package com.iManager.im.db.api.controller;

import com.iManager.im.db.api.model.Status;
import com.iManager.im.db.api.model.SubProject;
import com.iManager.im.db.api.repository.StatusRepository;
import com.iManager.im.db.api.repository.SubProjectRepository;
import com.iManager.im.db.api.requestDTO.StatusRequestDTO;
import com.iManager.im.db.api.responseDTO.StatusResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/db/api/status")
public class StatusController {
    @Autowired
    SubProjectRepository subProjectRepository;
    @Autowired
    StatusRepository statusRepository;

    @PostMapping("/create/{subProjectId}")
    public ResponseEntity createStatus(@PathVariable UUID subProjectId,
                                       @RequestBody StatusRequestDTO requestDTO){
        SubProject subProject = subProjectRepository.findById(subProjectId).orElseThrow();
        Status status = new Status();
        status.setName(requestDTO.getName().toLowerCase());
        status.setSubProject(subProject);
        statusRepository.save(status);
        StatusResponseDTO responseDTO = new StatusResponseDTO();
        responseDTO.setId(status.getId());
        responseDTO.setName(status.getName());
        return new ResponseEntity(responseDTO, HttpStatus.CREATED);
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
