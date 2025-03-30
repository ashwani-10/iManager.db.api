package com.iManager.im.db.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iManager.im.db.api.model.Organization;
import com.iManager.im.db.api.model.Roles;
import com.iManager.im.db.api.repository.OrgRepository;
import com.iManager.im.db.api.repository.RoleRepository;
import com.iManager.im.db.api.requestDTO.RoleRequestDTO;
import com.iManager.im.db.api.responseDTO.RoleResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("db/api/role")
public class RoleController {
    @Autowired
    OrgRepository orgRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ObjectMapper objectMapper;

    @PostMapping("/create/{orgId}")
    public ResponseEntity createRole(@PathVariable UUID orgId,
                                     @RequestBody RoleRequestDTO requestDTO){
        Organization organization = orgRepository.findById(orgId).orElseThrow();
        Roles role = new Roles();
        role.setName(requestDTO.getName().toLowerCase());
        role.setDescription(requestDTO.getDescription().toLowerCase());
        role.setOrganization(organization);
        roleRepository.save(role);
        RoleResponseDTO responseDTO = new RoleResponseDTO();
        responseDTO.setId(role.getId());
        responseDTO.setName(role.getName());
        responseDTO.setDescription(role.getDescription());
        return new ResponseEntity(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/get/{orgId}")
    public ResponseEntity getRoles(@PathVariable UUID orgId){
        Organization organization = orgRepository.findByIdWithRoles(orgId).orElseThrow();
        List<Roles> roles = organization.getRoles();
        List<RoleResponseDTO> responseDTOList = new ArrayList<>();
        for(Roles role : roles){
            RoleResponseDTO responseDTO = new RoleResponseDTO();
            responseDTO.setId(role.getId());
            responseDTO.setName(role.getName());
            responseDTO.setDescription(role.getDescription());
            responseDTOList.add(responseDTO);
        }
        return ResponseEntity.ok(responseDTOList);
    }
}
