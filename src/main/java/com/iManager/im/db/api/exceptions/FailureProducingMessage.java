package com.iManager.im.db.api.exceptions;

public class FailureProducingMessage extends RuntimeException {
    public FailureProducingMessage(String message,Exception e) {
        super(message);
    }
}
