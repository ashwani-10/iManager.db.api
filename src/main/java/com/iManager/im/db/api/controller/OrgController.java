package com.iManager.im.db.api.controller;

import com.iManager.im.db.api.model.Organization;
import com.iManager.im.db.api.repository.OrgRepository;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/db/api/org")
public class OrgController {

    @Autowired
    OrgRepository orgRepository;

    @PostMapping("/registration")
    public ResponseEntity orgRegistration(@RequestBody Organization org
                                          ){
        try{
            System.out.println("/inside db app");
            orgRepository.save(org);
            System.out.println("org created successfully");
            return new ResponseEntity<>("Org registered successfully", HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>("registration failed",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/get")
    public ResponseEntity getOrganization(@RequestParam String orgEmail){
        Optional<Organization> org = orgRepository.findByEmail(orgEmail);
        System.out.println("endpoint hi ho gaya hai");
        if(org.isPresent()){
            Organization organization = org.get();
            organization.setUsers(new ArrayList<>());
            organization.setProjects(new ArrayList<>());

            return new ResponseEntity<>(organization, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
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
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/docker")
    public ResponseEntity dockerTest(){
        String resp = "Docker connected with db";
        System.out.println(resp);
        return new ResponseEntity(resp, HttpStatus.OK);
    }
}


