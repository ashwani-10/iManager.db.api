package com.iManager.im.db.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iManager.im.db.api.model.Organization;
import com.iManager.im.db.api.model.Project;
import com.iManager.im.db.api.repository.OrgRepository;
import com.iManager.im.db.api.repository.ProjectRepository;
import com.iManager.im.db.api.requestDTO.ProjectRequestDTO;
import com.iManager.im.db.api.responseDTO.ProjectResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("db/api/project")
public class ProjectController {
    ProjectRepository projectRepository;
    OrgRepository orgRepository;
    ObjectMapper objectMapper;

    public ProjectController(ProjectRepository projectRepository,
                             OrgRepository orgRepository,
                             ObjectMapper objectMapper) {
        this.projectRepository = projectRepository;
        this.orgRepository = orgRepository;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/create")
    public ResponseEntity createProject(@RequestBody ProjectRequestDTO projectRequestDTO){
        try {
            Organization org = orgRepository.findById(projectRequestDTO.getOrgId())
                    .orElseThrow(() -> new RuntimeException("Org does not exists"));
            Project project = new Project();
            project.setName(projectRequestDTO.getName());
            project.setOrganization(org);
            projectRepository.save(project);
            ProjectResponseDTO responseDTO = new ProjectResponseDTO();
            responseDTO.setId(project.getId());
            responseDTO.setName(project.getName());
            return new ResponseEntity<Object>(responseDTO,HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println("Failed creating project");
            return new ResponseEntity("Failed creating",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity deleteProject(@PathVariable UUID projectId){
        try {
            Project project = projectRepository.findById(projectId)
                            .orElseThrow(()-> new RuntimeException("No project exists with this Id"+projectId));
            projectRepository.delete(project);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            System.out.println("Failed deleting project");
            return new ResponseEntity("Failed deleting",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity updateProject(@RequestBody ProjectRequestDTO projectRequestDTO){
        try {
            Project project = projectRepository.findById(projectRequestDTO.getId())
                    .orElseThrow(()-> new RuntimeException("Project does not exists"));
            project.setName(projectRequestDTO.getName());
            projectRepository.save(project);
            return new ResponseEntity<Object>("project updated successfully",HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println("Failed updating project");
            return new ResponseEntity("Failed updating",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{orgId}")
    public ResponseEntity getProject(@PathVariable UUID orgId){
        try {
            Organization org = orgRepository.findByIdWithProjects(orgId)
                    .orElseThrow(()->new RuntimeException("Oeg not found"));
            List<Project> projects = org.getProjects();
            List<ProjectResponseDTO> responseDTOList = new ArrayList<>();
            for(Project project : projects){
                ProjectResponseDTO projectResponseDTO = new ProjectResponseDTO();
                projectResponseDTO.setId(project.getId());
                projectResponseDTO.setName(project.getName());
                responseDTOList.add(projectResponseDTO);
            }
            return new ResponseEntity(responseDTOList,HttpStatus.OK);
        }catch (Exception e){
            System.out.println("Failed fetching project");
            return new ResponseEntity("Failed fetching",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
