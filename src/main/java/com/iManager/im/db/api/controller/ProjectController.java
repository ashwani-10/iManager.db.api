package com.iManager.im.db.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iManager.im.db.api.model.*;
import com.iManager.im.db.api.repository.*;
import com.iManager.im.db.api.requestDTO.ProjectRequestDTO;
import com.iManager.im.db.api.responseDTO.ProjectResponseDTO;
import com.iManager.im.db.api.utils.Mapper;
import com.iManager.im.db.api.utils.ValidateAuth;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("db/api/project")
public class ProjectController {
    ProjectRepository projectRepository;
    OrgRepository orgRepository;
    ObjectMapper objectMapper;
    OperationRepository operationRepository;
    UserRepository userRepository;
    RoleRepository roleRepository;
    ValidateAuth validateAuth;
    Mapper mapper;

    public ProjectController(ProjectRepository projectRepository,
                             OrgRepository orgRepository,
                             ObjectMapper objectMapper,
                             OperationRepository operationRepository,
                             UserRepository userRepository,
                             RoleRepository roleRepository,
                             ValidateAuth validateAuth,
                             Mapper mapper) {
        this.projectRepository = projectRepository;
        this.orgRepository = orgRepository;
        this.objectMapper = objectMapper;
        this.operationRepository = operationRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.validateAuth = validateAuth;
        this.mapper = mapper;
    }

    @PostMapping("/create/{loggedId}")
    public ResponseEntity createProject(@PathVariable String loggedId,
                                        @RequestBody ProjectRequestDTO projectRequestDTO){
            UUID opId = UUID.fromString("4ad91b2d-ea24-492c-979c-4d5dac9c7162");
            Operation operation = operationRepository.findById(opId).orElseThrow();

            if (validateAuth.validateUser(loggedId,operation)) {
                try{
                Organization org = orgRepository.findById(projectRequestDTO.getOrgId())
                        .orElseThrow(() -> new RuntimeException("Org does not exists"));
                Project project = new Project();
                project.setName(projectRequestDTO.getName());
                project.setOrganization(org);
                project.setCreatedAt(Instant.now());
                projectRepository.save(project);

                ProjectResponseDTO responseDTO = new ProjectResponseDTO();
                responseDTO.setId(project.getId());
                responseDTO.setName(project.getName());
                responseDTO.setCreatedAt(project.getCreatedAt());
                return new ResponseEntity<Object>(responseDTO, HttpStatus.CREATED);
            }catch(Exception e){
                System.out.println("Failed creating project");
                return new ResponseEntity("Failed creating", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>("You are not authorized for this operation",HttpStatus.UNAUTHORIZED);
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
                ProjectResponseDTO projectResponseDTO = mapper.projectResponse(project);
                responseDTOList.add(projectResponseDTO);
            }
            return new ResponseEntity(responseDTOList,HttpStatus.OK);
        }catch (Exception e){
            System.out.println("Failed fetching project");
            return new ResponseEntity("Failed fetching",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
