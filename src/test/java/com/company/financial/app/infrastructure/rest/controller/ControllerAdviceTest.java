package com.company.financial.app.infrastructure.rest.controller;

import com.company.financial.app.domain.model.dto.ErrorDto;
import com.company.financial.app.infrastructure.rest.controller.ResExceptiont.DataClientException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ControllerAdviceTest {

    private ControllerAdvice underTest;

    @BeforeEach
    void setUp() {
        underTest = new ControllerAdvice();
    }

    @Test
    void dataClientExceptionHandler() {
        ErrorDto errorDtoExpected = ErrorDto.builder().code("400").message("Mensaje de error").build();
        ResponseEntity<ErrorDto> responseExpected = new ResponseEntity<>(errorDtoExpected, HttpStatus.BAD_REQUEST);
        ResponseEntity<ErrorDto> response = underTest.dataClientExceptionHandler(new DataClientException("Mensaje de error"));
        assertEquals(responseExpected, response);
    }

    @Test
    void persistenceExceptionHandler() {
        ErrorDto errorDtoExpected = ErrorDto.builder().code("500").message("Mensaje de error").build();
        ResponseEntity<ErrorDto> responseExpected = new ResponseEntity<>(errorDtoExpected, HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseEntity<ErrorDto> response = underTest.persistenceExceptionHandler(new DataClientException("Mensaje de error"));
        assertEquals(responseExpected, response);
    }
}