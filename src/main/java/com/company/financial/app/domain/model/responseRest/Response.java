package com.company.financial.app.domain.model.responseRest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Response<T> {
    private int status;
    private T data;
    private String message;
    private String error;
}