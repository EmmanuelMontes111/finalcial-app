package com.company.financial.app.application.service;

import com.company.financial.app.application.mapper.ClientDtoMapper;
import com.company.financial.app.application.mapper.ClientRequestMapper;
import com.company.financial.app.application.service.exception.PersistenceException;
import com.company.financial.app.application.usecases.ClientService;
import com.company.financial.app.domain.model.Client;
import com.company.financial.app.domain.model.dto.ClientDto;
import com.company.financial.app.domain.model.dto.request.ClientRequest;
import com.company.financial.app.domain.model.responseRest.Response;
import com.company.financial.app.domain.port.ClientPersistencePort;
import com.company.financial.app.infrastructure.rest.controller.ResExceptiont.DataClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ClientManagementService implements ClientService {


    @Autowired(required = true)
    ClientPersistencePort clientPersistencePort;

    @Autowired
    ClientRequestMapper clientRequestMapper;

    @Autowired
    ClientDtoMapper clientDtoMapper;


    @Override
    public ResponseEntity<Response> createClient(ClientRequest clientRequest) {
        Client client;
        Client clientToCreate = clientRequestMapper.requestToModel(clientRequest);
        Long currentDate = System.currentTimeMillis();

        clientToCreate.toBuilder()
                .creationDate(currentDate)
                .modificationDate(currentDate)
                .build();

        try {
            client = clientPersistencePort.create(clientToCreate);
        } catch (Exception ex) {
            throw new PersistenceException("A ocurrido un error guardando el cliente en base de datos", ex);
        }

        ClientDto clientDto = clientDtoMapper.modelToDto(client);
        Response response = new Response(200, clientDto, "Cliente creado exitosamente", "");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> getClientById(Long identificationNumber) {
        Client client;
        try {
            client = clientPersistencePort.getById(identificationNumber);
        } catch (Exception ex) {
            if (ex instanceof DataClientException) {
                throw ex;
            }
            //TODO: Analizar si debo generar un nuevo tipo de exepcion
            throw new PersistenceException("A ocurrido un error en base de datos obteniendo los  datos ", ex);
        }

        ClientDto clientDto = clientDtoMapper.modelToDto(client);
        Response response = new Response(200, clientDto, "Informacion del cliente", "");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> getAllClients() {
        return null;
    }

    @Override
    public ResponseEntity<Response> deleteClientById(Long identificationNumber) {
        Client client;
        ResponseEntity<Response> clientRequest = getClientById(identificationNumber);
        client = (Client) Objects.requireNonNull(clientRequest.getBody()).getData();

        try {
            clientPersistencePort.deleteClient(client);
        } catch (Exception ex) {
            throw new PersistenceException("A ocurrido un error eliminando el usuario", ex);
        }
        Response response = new Response(204, null, "Cliente eliminado exitosamente", "");
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Response> updateClient(Long identificationNumber, ClientRequest clientRequest) {
        Client client;
        Client clientToUpdate = clientRequestMapper.requestToModel(clientRequest);
        Long currentDate = System.currentTimeMillis();

        clientToUpdate = clientToUpdate.toBuilder()
                .modificationDate(currentDate)
                .build();

        try {
            client = clientPersistencePort.create(clientToUpdate);
        } catch (Exception ex) {
            throw new PersistenceException("A ocurrido un error actualizando el cliente en base de datos", ex);
        }

        ClientDto clientDto = clientDtoMapper.modelToDto(client);
        Response response = new Response(200, clientDto, "Cliente actualizado exitosamente", "");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
