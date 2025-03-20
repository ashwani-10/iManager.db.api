package com.iManager.im.db.api.controller;

import com.iManager.im.db.api.enums.Role;
import com.iManager.im.db.api.model.Organization;
import com.iManager.im.db.api.model.User;
import com.iManager.im.db.api.repository.OrgRepository;
import com.iManager.im.db.api.repository.UserRepository;
import com.iManager.im.db.api.requestDTO.UserRequestDTO;
import com.iManager.im.db.api.service.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("create")
    public ResponseEntity createUser(@RequestParam String userEmail,
                                     @RequestParam String userRole,
                                     @RequestParam String orgId){
        User user = new User();
        user.setEmail(userEmail);
        user.setName("");
        user.setPassword("");
        user.setRole(Role.valueOf(userRole));
        Organization org = orgRepository.findById(UUID.fromString(orgId))
                .orElseThrow(()-> new RuntimeException("Org does not exists with this Id"));
        user.setOrganization(org);
        try {
            userRepository.save(user);
            messageProducer.inviteUser(userEmail,org.getName());
            return new ResponseEntity("User created with inActive Account", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity("Failed creating User account",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/finalize")
    public ResponseEntity finalizeUser(@RequestBody UserRequestDTO userRequestDTO){
        User user = userRepository.findByEmail(userRequestDTO.getEmail())
                .orElseThrow(()-> new RuntimeException("User not found"));

        user.setName(userRequestDTO.getName());
        user.setPassword(userRequestDTO.getPassword());
        user.setActive(true);
        try {
            userRepository.save(user);
            return new ResponseEntity("User Finalized Successfully",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity("Failed finalizing User",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
