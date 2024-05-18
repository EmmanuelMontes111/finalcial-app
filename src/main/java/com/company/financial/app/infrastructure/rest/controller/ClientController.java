package com.company.financial.app.infrastructure.rest.controller;

import com.company.financial.app.application.usecases.ClientService;
import com.company.financial.app.domain.model.dto.request.ClientRequest;
import com.company.financial.app.domain.model.responseRest.Response;
import com.company.financial.app.infrastructure.rest.controller.validations.UserValidationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private UserValidationData userValidationData;

    @PostMapping("/client")
    public ResponseEntity<Response> createClient(@RequestBody ClientRequest clientRequest) {
        userValidationData.validateData(clientRequest);
        return clientService.createClient(clientRequest);
    }

    @PutMapping("/client/update/{id}")
    public ResponseEntity<Response> updateClient(@PathVariable Long clientId, @RequestBody ClientRequest clientRequest) {
        userValidationData.validateData(clientRequest);
        return clientService.updateClient(clientId, clientRequest);
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<Response> getClient(@PathVariable Long clientId){
        return clientService.getClientById(clientId);
    }

    @PutMapping("/client/delete/{id}")
    public ResponseEntity<Response> deleteClient(@PathVariable Long clientId) {
        return clientService.deleteClientById(clientId);
    }

}
