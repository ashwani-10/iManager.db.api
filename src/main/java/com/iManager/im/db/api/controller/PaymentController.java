package com.iManager.im.db.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iManager.im.db.api.enums.PaymentStatus;
import com.iManager.im.db.api.model.Organization;
import com.iManager.im.db.api.model.Payment;
import com.iManager.im.db.api.repository.OrgRepository;
import com.iManager.im.db.api.repository.PaymentRepository;
import com.iManager.im.db.api.requestDTO.OrgRequestDTO;
import com.iManager.im.db.api.service.KafkaProducerService;
import com.iManager.im.db.api.service.MessageProducer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("db/api/payment")
public class PaymentController {
    @Autowired
    PaymentRepository paymentRepo;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    OrgRepository orgRepository;
    @Autowired
    MessageProducer messageProducer;

    @PostMapping("/request")
    public ResponseEntity createPendingPayment(@RequestBody Payment payment){
        try{
            System.out.println(payment.getOrderId());
            paymentRepo.save(payment);
            return new ResponseEntity("Success saving pending payment", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity("Failed saving payment details: "+e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @KafkaListener(topics = {"Payment-Success"},groupId = "springboot-group-1")
    public ResponseEntity successPayment(ConsumerRecord<String, String> record) throws JsonProcessingException {
        String key = record.key();
        String amount = record.value();

        String orderId = key;
        System.out.println(orderId);
        Payment payment = paymentRepo.findByOrderId(orderId)
                .orElseThrow(()-> new RuntimeException("There is no such payment with this order_id: "+orderId));

        System.out.println(payment.getStatus());
        PaymentStatus paymentStatus = payment.getStatus();
        if(paymentStatus.equals(PaymentStatus.PENDING) || paymentStatus.equals(PaymentStatus.FAILURE)) {
            payment.setStatus(PaymentStatus.PAID);
            paymentRepo.save(payment);
        }

        System.out.println("after setting"+payment.getStatus());

        String orgData = payment.getOrgData();
        Organization org = objectMapper.readValue(orgData, Organization.class);
        org.setOrderId(orderId);
        orgRepository.save(org);

        OrgRequestDTO orgRequestDTO = objectMapper.convertValue(org, OrgRequestDTO.class);
        orgRequestDTO.setAmount(amount);

        try{
        messageProducer.paymentConfirmation(orgRequestDTO);
        }catch (Exception e){
            System.out.println("Failure sending payment confirmation email");
        }
        return new ResponseEntity<>("Successful registration of org with paid payment",HttpStatus.CREATED);
    }
}
