package com.company.financial.app.application.service.exception;

import org.springframework.context.ApplicationContextException;

public class PersistenceException extends ApplicationContextException {
    public PersistenceException(String msg) {
        super(msg);
    }
    public PersistenceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
