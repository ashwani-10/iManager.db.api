package com.iManager.im.db.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iManager.im.db.api.model.Organization;
import com.iManager.im.db.api.model.Project;
import com.iManager.im.db.api.model.SubProject;
import com.iManager.im.db.api.repository.ProjectRepository;
import com.iManager.im.db.api.repository.SubProjectRepository;
import com.iManager.im.db.api.requestDTO.ProjectRequestDTO;
import com.iManager.im.db.api.requestDTO.SubProjectReqDTO;
import com.iManager.im.db.api.responseDTO.ProjectResponseDTO;
import com.iManager.im.db.api.responseDTO.SubProjectResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("db/api/subProject")
public class SubProjectController {
    @Autowired
    SubProjectRepository subProjectRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ObjectMapper objectMapper;

    @PostMapping("/create")
    public ResponseEntity createSubProject(@RequestBody SubProjectReqDTO requestDTO){
        try {
            Project project = projectRepository.findById(requestDTO.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project does not exists"));
            SubProject subProject = new SubProject();
            subProject.setName(requestDTO.getName());
            subProject.setProject(project);
            subProjectRepository.save(subProject);
            SubProjectResDTO responseDTO = new SubProjectResDTO();
            responseDTO.setId(subProject.getId());
            responseDTO.setName(subProject.getName());
            return new ResponseEntity<Object>(responseDTO, HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println("Failed creating subProject");
            return new ResponseEntity("Failed creating subProject",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{subProjectId}")
    public ResponseEntity deleteSubProject(@PathVariable UUID subProjectId){
        try {
            SubProject subProject = subProjectRepository.findById(subProjectId)
                    .orElseThrow(()-> new RuntimeException("No subProject exists with this Id"+subProjectId));
            subProjectRepository.delete(subProject);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            System.out.println("Failed deleting subProject");
            return new ResponseEntity("Failed deleting",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity updateSubProject(@RequestBody SubProjectReqDTO reqDTO){
        try {
            SubProject subProject = subProjectRepository.findById(reqDTO.getId())
                    .orElseThrow(()-> new RuntimeException("No subProject exists with this Id"+reqDTO.getId()));
            subProject.setName(reqDTO.getName());
            subProjectRepository.save(subProject);
            SubProjectResDTO responseDTO = new SubProjectResDTO();
            responseDTO.setId(subProject.getId());
            responseDTO.setName(subProject.getName());
            return new ResponseEntity<Object>(responseDTO, HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println("Failed updating subProject");
            return new ResponseEntity("Failed updating",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{projectId}")
    public ResponseEntity getSubProject(@PathVariable UUID projectId){
        try {
            Project project = projectRepository.findByIdWithSubProjects(projectId)
                    .orElseThrow(()-> new RuntimeException("No project exists with this Id"+projectId));
            List<SubProject> subProjects = project.getSubProjects();
            List<SubProjectResDTO> responseDTOList = new ArrayList<>();
            for(SubProject subProject : subProjects){
                SubProjectResDTO subProjectResDTO = new SubProjectResDTO();
                subProjectResDTO.setId(subProject.getId());
                subProjectResDTO.setName(subProject.getName());
                responseDTOList.add(subProjectResDTO);
            }
            return new ResponseEntity(responseDTOList,HttpStatus.OK);
        }catch (Exception e){
            System.out.println("Failed fetching subProjects");
            return new ResponseEntity("Failed fetching",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}