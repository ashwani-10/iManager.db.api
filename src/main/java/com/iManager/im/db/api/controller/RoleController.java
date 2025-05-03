package com.iManager.im.db.api.controller;

import com.iManager.im.db.api.model.Operation;
import com.iManager.im.db.api.model.Organization;
import com.iManager.im.db.api.model.Roles;
import com.iManager.im.db.api.repository.OperationRepository;
import com.iManager.im.db.api.repository.OrgRepository;
import com.iManager.im.db.api.repository.RoleRepository;
import com.iManager.im.db.api.requestDTO.RoleRequestDTO;
import com.iManager.im.db.api.responseDTO.RoleResponseDTO;
import com.iManager.im.db.api.utils.Mapper;
import com.iManager.im.db.api.utils.ValidateAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("db/api/role")
public class RoleController {
    @Autowired
    OrgRepository orgRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    OperationRepository operationRepository;
    @Autowired
    Mapper mapper;
    @Autowired
    ValidateAuth validateAuth;

    @PostMapping("/create/{orgId}/{loggedId}")
    public ResponseEntity createRole(@PathVariable UUID orgId,
                                     @PathVariable String loggedId,
                                     @RequestBody RoleRequestDTO requestDTO){
        UUID opId = UUID.fromString("b4ba4abe-b587-42e7-b1aa-f4fe8791d1cc");
        Operation operation = operationRepository.findById(opId).orElseThrow();

        if(validateAuth.validateUser(loggedId,operation)){
            try {
                Organization organization = orgRepository.findById(orgId).orElseThrow();
                Roles role = new Roles();
                role.setName(requestDTO.getName().toLowerCase());
                role.setDescription(requestDTO.getDescription().toLowerCase());
                role.setOrganization(organization);
                role.setOperations(mapper.mapOpertaionList(requestDTO.getOperationsId()));
                roleRepository.save(role);

                RoleResponseDTO responseDTO = mapper.roleResponse(role);
                return new ResponseEntity(responseDTO, HttpStatus.CREATED);
            }catch (Exception e){
                return new ResponseEntity("Failed creating role", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>("You are not authorized for this operation",HttpStatus.UNAUTHORIZED);

    }

    @GetMapping("/get/{orgId}")
    public ResponseEntity getRoles(@PathVariable UUID orgId){
        Organization organization = orgRepository.findByIdWithRoles(orgId).orElseThrow();
        List<Roles> roles = organization.getRoles();
        List<RoleResponseDTO> responseDTOList = new ArrayList<>();
        for(Roles role : roles){
            RoleResponseDTO responseDTO = mapper.roleResponse(role);
            responseDTOList.add(responseDTO);
        }
        return ResponseEntity.ok(responseDTOList);
    }
}
