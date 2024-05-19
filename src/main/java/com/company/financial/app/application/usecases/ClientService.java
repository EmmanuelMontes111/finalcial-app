package com.company.financial.app.application.usecases;

import com.company.financial.app.domain.model.dto.request.ClientRequest;
import com.company.financial.app.domain.model.responseRest.Response;
import org.springframework.http.ResponseEntity;

public interface ClientService {

    ResponseEntity<Response> createClient(ClientRequest clientRequest);

    ResponseEntity<Response> getClientById(Long identificationNumber);

    ResponseEntity<Response> getAllClients();
    ResponseEntity<Response> getClientsByFilter(ClientRequest clientRequest);
    ResponseEntity<Response> getClientReports(ClientRequest clientRequest);

    ResponseEntity<Response> deleteClientById(Long identificationNumber);

    ResponseEntity<Response> updateClient(Long identificationNumber, ClientRequest clientRequest);
}
