package com.iManager.im.db.api.controller;

import com.iManager.im.db.api.enums.Role;
import com.iManager.im.db.api.model.*;
import com.iManager.im.db.api.repository.*;
import com.iManager.im.db.api.requestDTO.UserRequestDTO;
import com.iManager.im.db.api.responseDTO.UserResponseDTO;
import com.iManager.im.db.api.service.MessageProducer;
import com.iManager.im.db.api.utils.ValidateAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/db/api/user")
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    OrgRepository orgRepository;
    @Autowired
    MessageProducer messageProducer;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    SubProjectRepository subProjectRepository;
    @Autowired
    OperationRepository operationRepository;
    @Autowired
    ValidateAuth validateAuth;

    @PostMapping("/create/{loggedId}")
    public ResponseEntity createUser(@PathVariable String loggedId,
                                     @RequestParam String userEmail,
                                     @RequestParam String userRole,
                                     @RequestParam String orgId) {

        UUID opId = UUID.fromString("106fcd90-b89a-43a2-89ed-16e186ece1ad");
        Operation operation = operationRepository.findById(opId).orElseThrow();

        if (validateAuth.validateUser(loggedId, operation)) {
            User user = new User();
            user.setEmail(userEmail);
            user.setName("");
            user.setPassword("");
            user.setRole(Role.valueOf(userRole));
            Organization org = orgRepository.findById(UUID.fromString(orgId))
                    .orElseThrow(() -> new RuntimeException("Org does not exists with this Id"));
            user.setOrganization(org);
            try {
                userRepository.save(user);
                messageProducer.inviteUser(userEmail, org.getName());
                return new ResponseEntity("User created with inActive Account", HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity("Failed creating User account", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>("You are not authorized for this operation", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/finalize")
    public ResponseEntity finalizeUser(@RequestBody UserRequestDTO userRequestDTO) {
        User user = userRepository.findByEmail(userRequestDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(userRequestDTO.getName());
        user.setPassword(userRequestDTO.getPassword());
        user.setActive(true);
        try {
            userRepository.save(user);
            return new ResponseEntity("User Finalized Successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Failed finalizing User", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{userEmail}")
    public ResponseEntity getUser(@PathVariable String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        UserRequestDTO requestDTO = new UserRequestDTO();
        requestDTO.setId(user.getId());
        requestDTO.setEmail(user.getEmail());
        requestDTO.setName(user.getName());
        requestDTO.setPassword(user.getPassword());
        requestDTO.setRole(user.getRole());
        requestDTO.setOrgId(user.getOrganization().getId());
        Organization organization = orgRepository.findById(user.getOrganization().getId()).orElseThrow();
        requestDTO.setOrgName(organization.getName());
        User userWithRoles = userRepository.findByIdWithSubProjectRole(user.getId()).orElseThrow();
        Map<UUID,Roles> rolesMap = userWithRoles.getSubProjectRole();
        List<String> operations = new ArrayList<>();
        for(Roles roles : rolesMap.values()){
            Roles role = roleRepository.findByIdWithOperations(roles.getId()).orElseThrow();
            for(Operation operation : role.getOperations()){
                if(!operations.contains(operation.getName())) {
                    operations.add(operation.getName());
                }
            }
        }
        requestDTO.setOperations(operations);
        return ResponseEntity.ok(requestDTO);
    }

    @PostMapping("/add/role/{subProjectId}/{userId}/{roleId}/{loggedId}")
    public ResponseEntity addUserRole(@PathVariable UUID subProjectId,
                                      @PathVariable UUID userId,
                                      @PathVariable UUID roleId,
                                      @PathVariable String loggedId) {

        UUID opId = UUID.fromString("106fcd90-b89a-43a2-89ed-16e186ece1ad");
        Operation operation = operationRepository.findById(opId).orElseThrow();

        if (validateAuth.validateUser(loggedId, operation)) {
            try {
                Roles role = roleRepository.findById(roleId).orElseThrow();
                User user = userRepository.findByIdWithSubProjectRole(userId).orElseThrow();
                Map<UUID, Roles> rolesMap = user.getSubProjectRole();
                rolesMap.put(subProjectId, role);
                userRepository.save(user);
                SubProject subProject = subProjectRepository.findByIdWithUsers(subProjectId).orElseThrow();
                List<User> userList = subProject.getMembers();
                userList.add(user);
                subProject.setMembers(userList);
                subProjectRepository.save(subProject);
                UserResponseDTO responseDTO = new UserResponseDTO();
                responseDTO.setId(user.getId());
                responseDTO.setName(user.getName());
                responseDTO.setEmail(user.getEmail());
                if (rolesMap.containsKey(subProjectId)) {
                    responseDTO.setProjectRole(rolesMap.get(subProjectId).getName());
                }
                return ResponseEntity.ok(responseDTO);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        return new ResponseEntity<>("You are not authorized for this operation", HttpStatus.UNAUTHORIZED);
    }
}
