package com.iManager.im.db.api.controller;

import com.iManager.im.db.api.enums.Role;
import com.iManager.im.db.api.model.Organization;
import com.iManager.im.db.api.model.User;
import com.iManager.im.db.api.repository.OrgRepository;
import com.iManager.im.db.api.requestDTO.OrgRequestDTO;
import com.iManager.im.db.api.responseDTO.UserResponseDTO;
import com.iManager.im.db.api.service.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/db/api/org")
public class OrgController {

    @Autowired
    OrgRepository orgRepository;
    @Autowired
    MessageProducer messageProducer;

    @PostMapping("/registration")
    public ResponseEntity orgRegistration(@RequestBody Organization org
                                          ){
        try{
            System.out.println("/inside db app");
            org.setRole(Role.ADMIN);
            orgRepository.save(org);
            System.out.println("org created successfully");
            return new ResponseEntity<>("Org registered successfully", HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>("registration failed",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload/logo/{orgId}")
    public ResponseEntity uploadLogo(@PathVariable UUID orgId,
                                     @RequestParam String logoUrl){
        try{
            Organization org = orgRepository.findById(orgId).orElseThrow();
            org.setLogoUrl(logoUrl);
            orgRepository.save(org);
            return ResponseEntity.ok("logo uploaded");
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/get/logo/{orgId}")
    public ResponseEntity getLogo(@PathVariable UUID orgId){
        try{
            Organization org = orgRepository.findById(orgId).orElseThrow();
            String logoUrl = org.getLogoUrl();
            return ResponseEntity.ok(logoUrl);
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/get")
    public ResponseEntity getOrganization(@RequestParam String orgEmail){
        Optional<Organization> org = orgRepository.findByEmail(orgEmail);
        System.out.println("endpoint hi ho gaya hai");
        if(org.isPresent()){
            Organization organization = org.get();
            OrgRequestDTO orgRequestDTO = new OrgRequestDTO();
            orgRequestDTO.setId(organization.getId());
            orgRequestDTO.setName(organization.getName());
            orgRequestDTO.setRole(organization.getRole());
            orgRequestDTO.setEmail(organization.getEmail());
            orgRequestDTO.setPassword(organization.getPassword());

            return new ResponseEntity<>(orgRequestDTO, HttpStatus.OK);
        }else {
            OrgRequestDTO requestDTO = null;
            return new ResponseEntity<>(requestDTO,HttpStatus.OK);
        }
    }

    @GetMapping("/get/{orgId}")
    public ResponseEntity getOrgById(@PathVariable UUID orgId){
        Optional<Organization> org = orgRepository.findById(orgId);
        System.out.println("endpoint hi ho gaya hai");
        if(org.isPresent()){
            Organization organization = org.get();
            organization.setUsers(new ArrayList<>());
            organization.setProjects(new ArrayList<>());

            return new ResponseEntity<>(organization, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("null",HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/docker")
    public ResponseEntity dockerTest(){
        String resp = "Docker connected with db";
        System.out.println(resp);
        return new ResponseEntity(resp, HttpStatus.OK);
    }

    @GetMapping("/users/{orgId}")
    public ResponseEntity getMembers(@PathVariable UUID orgId){
        Optional<Organization> org = orgRepository.findByIdWithUsers(orgId);
        System.out.println("endpoint hit ho gaya hai");
        if(org.isPresent()){
            Organization organization = org.get();
            List<User> users = organization.getUsers();
            List<UserResponseDTO> responseDTOList = new ArrayList<>();
            for(User user : users){
                if(user.isActive()) {
                    UserResponseDTO responseDTO = new UserResponseDTO();
                    responseDTO.setId(user.getId());
                    responseDTO.setName(user.getName());
                    responseDTO.setEmail(user.getEmail());
                    responseDTOList.add(responseDTO);
                }
            }
            return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("null",HttpStatus.UNAUTHORIZED);
        }
    }
}


