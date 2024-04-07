package com.company.financial.app.infrastructure.rest.controller;

import com.company.financial.app.application.usecases.ClientService;
import com.company.financial.app.domain.model.dto.request.ClientRequest;
import com.company.financial.app.domain.model.responseRest.Response;
import com.company.financial.app.infrastructure.rest.controller.ResExceptiont.DataClientException;
import com.company.financial.app.infrastructure.rest.controller.validations.UserValidationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.zip.DataFormatException;

@RestController
@RequestMapping("/api/v1")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private UserValidationData userValidationData;

    @PostMapping("/client")
    public ResponseEntity<Response>createClient(@RequestBody ClientRequest clientRequest) {
        userValidationData.validateData(clientRequest);
        return clientService.createClient(clientRequest);
    }




}
