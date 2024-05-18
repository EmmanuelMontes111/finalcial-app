package com.company.financial.app.application.service.exception;

import org.springframework.context.ApplicationContextException;

public class ResourceNotFoundException extends ApplicationContextException {
    public ResourceNotFoundException(String msg) {
        super(msg);
    }

    public ResourceNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}