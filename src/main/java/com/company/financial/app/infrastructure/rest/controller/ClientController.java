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

    @PostMapping("/client/create")
    public ResponseEntity<Response> createClient(@RequestBody ClientRequest clientRequest) {
        userValidationData.validateData(clientRequest);
        return clientService.createClient(clientRequest);
    }

    @PutMapping("/client/update/{clientId}")
    public ResponseEntity<Response> updateClient(@PathVariable Long clientId, @RequestBody ClientRequest clientRequest) {
        userValidationData.validateData(clientRequest);
        return clientService.updateClient(clientId, clientRequest);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<Response> getClient(@PathVariable Long clientId){
        return clientService.getClientById(clientId);
    }

    @GetMapping("/clients")
    public ResponseEntity<Response> getClients(){
        return clientService.getAllClients();
    }

    @GetMapping("/client/filter")
    public ResponseEntity<Response> getClientsByFilter(@RequestBody ClientRequest clientRequest){
        return clientService.getClientsByFilter(clientRequest);
    }

    @DeleteMapping("/client/delete/{clientId}")
    public ResponseEntity<Response> deleteClient(@PathVariable Long clientId) {
        return clientService.deleteClientById(clientId);
    }

}
