package com.iManager.im.db.api.controller;

import com.iManager.im.db.api.model.Operation;
import com.iManager.im.db.api.repository.OperationRepository;
import com.iManager.im.db.api.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/db/api/operation")
public class OperationController {
    @Autowired
    OperationRepository operationRepository;
    @Autowired
    Mapper mapper;

    @PostMapping("/create")
    public ResponseEntity createOperation(@RequestParam String opName){
        try {
            Operation operation = new Operation();
            operation.setName(opName);
            operationRepository.save(operation);
            return new ResponseEntity("Operation Created", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity("Failed creating this operation", HttpStatus.CREATED);
        }
    }

    @GetMapping("/get")
    public ResponseEntity getOperations(){
        List<Operation> operations = operationRepository.findAll();
        return ResponseEntity.ok(mapper.operationResponse(operations));
    }
}
