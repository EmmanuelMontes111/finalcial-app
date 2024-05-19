package com.company.financial.app.application.service;

import com.company.financial.app.application.mapper.ClientDtoMapper;
import com.company.financial.app.application.mapper.ClientRequestMapper;
import com.company.financial.app.application.service.exception.PersistenceException;
import com.company.financial.app.application.usecases.BuildStructureReportFiltersUseCase;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ClientManagementService implements ClientService {


    @Autowired(required = true)
    ClientPersistencePort clientPersistencePort;

    @Autowired
    ClientRequestMapper clientRequestMapper;
    @Autowired
    ClientDtoMapper clientDtoMapper;

    @Autowired
    BuildStructureReportFiltersUseCase buildStructureReportFiltersUseCase;


    @Override
    public ResponseEntity<Response> createClient(ClientRequest clientRequest) {
        Client client;
        Client clientToCreate = clientRequestMapper.requestToModel(clientRequest);
        Long currentDate = System.currentTimeMillis();

        clientToCreate = clientToCreate.toBuilder()
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
        List<Client> clients;
        List<ClientDto> clientDtos = new ArrayList<>();
        try {
            clients = clientPersistencePort.getAll();
        } catch (Exception ex) {
            //TODO: Analizar si debo generar un nuevo tipo de exepcion
            throw new PersistenceException("A ocurrido un error en base de datos obteniendo los  datos", ex);
        }

        for (Client client : clients) {
            clientDtos.add(clientDtoMapper.modelToDto(client));
        }
        Response response;
        if (clientDtos.isEmpty()) {
            response = new Response(200, clientDtos, "No hay clientes en la base de datos", "");
        } else {
            response = new Response(200, clientDtos, "Lista de clientes", "");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> getClientsByFilter(ClientRequest clientRequest) {
        List<Client> clients;
        List<ClientDto> clientDtos = new ArrayList<>();
        Client clientLikeFilter = clientRequestMapper.requestToModel(clientRequest);

        try {
            clients = clientPersistencePort.getClientByFilter(clientLikeFilter);
        } catch (Exception ex) {
            //TODO: Analizar si debo generar un nuevo tipo de exepcion
            throw new PersistenceException("A ocurrido un error en base de datos obteniendo los  datos", ex);
        }

        for (Client client : clients) {
            clientDtos.add(clientDtoMapper.modelToDto(client));
        }

        Response response;
        if (clientDtos.isEmpty()) {
            response = new Response(200, clientDtos, "No hay clientes que coincidan con el filtro", "");
        } else {
            response = new Response(200, clientDtos, "Lista de clientes", "");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> getClientReports(ClientRequest clientRequest) {
        List<ClientDto> clientDtos;
        ResponseEntity<Response> clientFilter = getClientsByFilter(clientRequest);
        clientDtos = (List<ClientDto>) Objects.requireNonNull(clientFilter.getBody()).getData();

        String pdfBase64 = buildStructureReportFiltersUseCase.generateTableReportStructure(clientDtos);

        Response response;
        if (clientDtos.isEmpty()) {
            response = new Response(200, pdfBase64, "No hay clientes que coincidan con el filtro", "");
        } else {
            response = new Response(200, pdfBase64, "Reporte de clientes", "");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> deleteClientById(Long identificationNumber) {
        ClientDto clientDto;
        Client clientToDelete;
        ResponseEntity<Response> clientRequest = getClientById(identificationNumber);
        clientDto = (ClientDto) Objects.requireNonNull(clientRequest.getBody()).getData();
        clientToDelete = clientDtoMapper.dtoToModel(clientDto);
        try {
            clientPersistencePort.deleteClient(clientToDelete);
        } catch (Exception ex) {
            throw new PersistenceException("A ocurrido un error eliminando el usuario", ex);
        }
        Response response = new Response(204, null, "Cliente eliminado exitosamente", "");
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Response> updateClient(Long identificationNumber, ClientRequest clientRequest) {
        ClientDto clientFoundDto;
        Client clientResponse;
        Client clientFound;
        ResponseEntity<Response> clientById = getClientById(identificationNumber);


        clientFoundDto = (ClientDto) Objects.requireNonNull(clientById.getBody()).getData();
        clientFound = clientDtoMapper.dtoToModel(clientFoundDto);
        Client clientToUpdate = clientRequestMapper.requestToModel(clientRequest);

        clientFound = clientFound.toBuilder()
                .name(clientToUpdate.getName())
                .lastName(clientToUpdate.getLastName())
                .email(clientToUpdate.getEmail())
                .idType(clientToUpdate.getIdType())
                .birthdate(clientToUpdate.getBirthdate())
                .identificationNumber(clientToUpdate.getIdentificationNumber())
                .modificationDate(System.currentTimeMillis())
                .build();

        try {
            clientResponse = clientPersistencePort.create(clientFound);
        } catch (Exception ex) {
            throw new PersistenceException("A ocurrido un error actualizando el cliente en base de datos", ex);
        }

        ClientDto clientDto = clientDtoMapper.modelToDto(clientResponse);
        Response response = new Response(200, clientDto, "Cliente actualizado exitosamente", "");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
