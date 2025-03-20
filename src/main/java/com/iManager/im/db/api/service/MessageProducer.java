package com.iManager.im.db.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iManager.im.db.api.exceptions.FailureProducingMessage;
import com.iManager.im.db.api.model.Organization;
import com.iManager.im.db.api.requestDTO.OrgRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {
    @Autowired
    KafkaProducerService kafkaProducer;

    public void paymentConfirmation(OrgRequestDTO org) throws JsonProcessingException {
        final String topic = "user-registration";

        int retries = 3;

        while (retries-- > 0) {
            try {
                kafkaProducer.produceMessage(org,"payment-mail",topic);
                return;
            }catch (Exception e) {
                if (retries == 0) {
                    throw new FailureProducingMessage("Failed to send registration email after 3 retries", e);
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    throw new FailureProducingMessage("Email sending Interrupted", ex);
                }
            }
        }
    }

    public void inviteUser(String userEmail,String orgName) throws JsonProcessingException {
        final String topic = "user-registration";
        String message = userEmail+":"+orgName;
        int retries = 3;

        while (retries-- > 0) {
            try {
                kafkaProducer.produceMessage(message,"invite-mail",topic);
                return;
            }catch (Exception e) {
                if (retries == 0) {
                    throw new FailureProducingMessage("Failed to send registration email after 3 retries", e);
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    throw new FailureProducingMessage("Email sending Interrupted", ex);
                }
            }
        }
    }
}
