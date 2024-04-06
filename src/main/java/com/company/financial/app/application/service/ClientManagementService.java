package com.company.financial.app.application.service;

import com.company.financial.app.application.usecases.ClientService;
import com.company.financial.app.domain.model.dto.ClientDto;
import com.company.financial.app.domain.model.dto.request.ClientRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientManagementService implements ClientService {

    @Override
    public ResponseEntity<ClientDto> createClient(ClientRequest clientRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ClientDto> getClientById(Long identificationNumber) {
        return null;
    }

    @Override
    public ResponseEntity<List<ClientDto>> getAllClients() {
        return null;
    }

    @Override
    public ResponseEntity<ClientDto> deleteClientById(Long identificationNumber) {
        return null;
    }

    @Override
    public ResponseEntity<ClientDto> updateClient(Long identificationNumber, ClientRequest clientRequest) {
        return null;
    }
}
