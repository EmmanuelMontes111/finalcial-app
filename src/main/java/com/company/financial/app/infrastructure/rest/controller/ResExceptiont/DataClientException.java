package com.company.financial.app.infrastructure.rest.controller.ResExceptiont;

import org.springframework.context.ApplicationContextException;

public class DataClientException extends ApplicationContextException {
    public DataClientException(String msg) {
        super(msg);
    }

    public DataClientException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
