package com.company.financial.app.application.usecases;

import com.company.financial.app.domain.model.dto.ClientDto;
import com.company.financial.app.domain.model.dto.request.ClientRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ClientService {

    ResponseEntity<ClientDto> createClient(ClientRequest clientRequest);
    ResponseEntity<ClientDto> getClientById(Long identificationNumber);
    ResponseEntity<List<ClientDto>> getAllClients();
    ResponseEntity<ClientDto> deleteClientById(Long identificationNumber);
    ResponseEntity<ClientDto> updateClient(Long identificationNumber, ClientRequest clientRequest);
}
