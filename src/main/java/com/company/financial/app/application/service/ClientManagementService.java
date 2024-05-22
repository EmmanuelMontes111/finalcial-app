package com.company.financial.app.application.service;

import com.company.financial.app.application.mapper.ClientDtoMapper;
import com.company.financial.app.application.mapper.ClientRequestMapper;
import com.company.financial.app.application.service.exception.PersistenceException;
import com.company.financial.app.application.usecases.BuildStructureReportFiltersUseCase;
import com.company.financial.app.application.usecases.ClientService;
import com.company.financial.app.application.utils.GenerateXML;
import com.company.financial.app.application.utils.GenerateXSL;
import com.company.financial.app.application.utils.TransformFileToBase64;
import com.company.financial.app.domain.model.Client;
import com.company.financial.app.domain.model.dto.ClientDto;
import com.company.financial.app.domain.model.dto.ClientsReportDto;
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
    public ResponseEntity<Response<ClientDto>> createClient(ClientRequest clientRequest) {
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
        Response<ClientDto> response = new Response<>(200, clientDto, "Cliente creado exitosamente", "");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response<ClientDto>> getClientById(Long identificationNumber) {
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
        Response response = new Response<>(200, clientDto, "Informacion del cliente", "");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response<ClientsReportDto>> getAllClients() {
        List<Client> clients;
        List<ClientDto> clientDtos = new ArrayList<>();
        double totalInvestmentCompany = 0;

        try {
            clients = clientPersistencePort.getAll();
        } catch (Exception ex) {
            throw new PersistenceException("A ocurrido un error en base de datos obteniendo los  datos", ex);
        }
        totalInvestmentCompany = getReduceTotalInvestment(clients);

        for (Client client : clients) {
            double moneyInvested = client.getMoneyInvested();
            client = client.toBuilder()
                    .percentageInvested((moneyInvested / totalInvestmentCompany) * 100)
                    .build();
            clientDtos.add(clientDtoMapper.modelToDto(client));
        }

        Response<ClientsReportDto> response = getResponse(clientDtos, totalInvestmentCompany);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private Response<ClientsReportDto> getResponse(List<ClientDto> clientDtos, double totalInvestmentCompany) {
        ClientsReportDto clientsReportDto;
        Response<ClientsReportDto> response;
        if (clientDtos.isEmpty()) {
            response = new Response<>(200, null, "No hay clientes en la base de datos", "");
        } else {
            clientsReportDto = buildBodyClientsReportResponse(clientDtos, totalInvestmentCompany, totalInvestmentCompany);
            response = new Response<>(200, clientsReportDto, "Lista de clientes", "");
        }
        return response;
    }

    private static Double getReduceTotalInvestment(List<Client> clients) {
        return clients.stream()
                .map(Client::getMoneyInvested)
                .reduce(0.0, Double::sum);
    }

    private ClientsReportDto buildBodyClientsReportResponse(List<ClientDto> clientDtos, double totalInvestmentClients, double totalInvestmentCompany) {
        return ClientsReportDto.builder()
                .clientDtos(clientDtos)
                .totalInvestmentClients(totalInvestmentClients)
                .totalInvestmentCompany(totalInvestmentCompany)
                .build();
    }

    @Override
    public ResponseEntity<Response> getClientsByFilter(ClientRequest clientRequest) {
        List<Client> clientsByFilter;
        List<Client> allClients;
        List<ClientDto> clientDtos = new ArrayList<>();
        double totalInvestmentCompany = 0;
        double totalInvestmentClients = 0;
        Client clientLikeFilter = clientRequestMapper.requestToModel(clientRequest);

        try {
            clientsByFilter = clientPersistencePort.getClientByFilter(clientLikeFilter, clientRequest.getOperatorComparator());
            allClients = clientPersistencePort.getAll();
        } catch (Exception ex) {
            //TODO: Analizar si debo generar un nuevo tipo de exepcion
            throw new PersistenceException("A ocurrido un error en base de datos obteniendo los  datos", ex);
        }

        totalInvestmentClients = getReduceTotalInvestment(clientsByFilter);
        totalInvestmentCompany = getReduceTotalInvestment(allClients);

        for (Client client : clientsByFilter) {
            double moneyInvested = client.getMoneyInvested();
            client = client.toBuilder()
                    .percentageInvested((moneyInvested / totalInvestmentCompany) * 100)
                    .build();
            clientDtos.add(clientDtoMapper.modelToDto(client));
        }

        Response<ClientsReportDto> response = getResponseByFilter(clientDtos, totalInvestmentClients, totalInvestmentCompany);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private Response<ClientsReportDto> getResponseByFilter(List<ClientDto> clientDtos, double totalInvestmentClients, double totalInvestmentCompany) {
        ClientsReportDto clientsReportDto;
        Response<ClientsReportDto> response;
        if (clientDtos.isEmpty()) {
            response = new Response(200, null, "No hay clientes que coincidan con el filtro", "");
        } else {
            clientsReportDto = buildBodyClientsReportResponse(clientDtos, totalInvestmentClients, totalInvestmentCompany);
            response = new Response(200, clientsReportDto, "Lista de clientes", "");
        }
        return response;
    }

    @Override
    public ResponseEntity<Response> getClientReportsPDF(ClientRequest clientRequest) {
        List<ClientDto> clientDtos;
        ClientsReportDto clientsReportDto;
        ResponseEntity<Response> clientFilter = getClientsByFilter(clientRequest);
        clientsReportDto = (ClientsReportDto) Objects.requireNonNull(clientFilter.getBody()).getData();

        clientDtos = clientsReportDto.getClientDtos();
        String pdfBase64 = buildStructureReportFiltersUseCase.generateTableReportStructure(clientDtos);

        Response response;
        if (clientDtos.isEmpty()) {
            response = new Response(200, pdfBase64, "No hay clientes que coincidan con el filtro", "");
        } else {
            response = new Response(200, pdfBase64, "Reporte de clientes PDF", "");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> getClientReportsXML(ClientRequest clientRequest) {
        List<ClientDto> clientDtos;
        ClientsReportDto clientsReportDto;
        ResponseEntity<Response> clientFilter = getClientsByFilter(clientRequest);
        clientsReportDto = (ClientsReportDto) Objects.requireNonNull(clientFilter.getBody()).getData();

        clientDtos = clientsReportDto.getClientDtos();
        GenerateXML.createTable(clientDtos);

        String xmlFilePath = "/companyTable.xml";

        String xmlBase64 = TransformFileToBase64.transformBase64(xmlFilePath);

        Response response;
        if (clientDtos.isEmpty()) {
            response = new Response(200, xmlBase64, "No hay clientes que coincidan con el filtro", "");
        } else {
            response = new Response(200, xmlBase64, "Reporte de clientes XML", "");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<Response> getClientReportXSL(ClientRequest clientRequest) {
        List<ClientDto> clientDtos;
        ClientsReportDto clientsReportDto;
        ResponseEntity<Response> clientFilter = getClientsByFilter(clientRequest);
        clientsReportDto = (ClientsReportDto) Objects.requireNonNull(clientFilter.getBody()).getData();

        clientDtos = clientsReportDto.getClientDtos();
        GenerateXSL.createXSL(clientDtos);

        String xslFilePath = "/template.xsl";

        String xslBase64 = TransformFileToBase64.transformBase64(xslFilePath);

        Response response;
        if (clientDtos.isEmpty()) {
            response = new Response(200, xslBase64, "No hay clientes que coincidan con el filtro", "");
        } else {
            response = new Response(200, xslBase64, "Reporte de clientes XSL", "");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> deleteClientById(Long identificationNumber) {
        ClientDto clientDto;
        Client clientToDelete;
        ResponseEntity<Response<ClientDto>> clientRequest = getClientById(identificationNumber);
        clientDto = Objects.requireNonNull(clientRequest.getBody()).getData();
        clientToDelete = clientDtoMapper.dtoToModel(clientDto);
        try {
            clientPersistencePort.deleteClient(clientToDelete);
        } catch (Exception ex) {
            throw new PersistenceException("A ocurrido un error eliminando el usuario", ex);
        }
        Response<ClientDto> response = new Response<>(204, null, "Cliente eliminado exitosamente", "");
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Response<ClientDto>> updateClient(Long identificationNumber, ClientRequest clientRequest) {
        ClientDto clientFoundDto;
        Client clientResponse;
        Client clientFound;
        ResponseEntity<Response<ClientDto>> clientById = getClientById(identificationNumber);

        clientFoundDto = Objects.requireNonNull(clientById.getBody()).getData();
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
                .moneyInvested(clientToUpdate.getPercentageInvested())
                .build();

        try {
            clientResponse = clientPersistencePort.create(clientFound);
        } catch (Exception ex) {
            throw new PersistenceException("A ocurrido un error actualizando el cliente en base de datos", ex);
        }

        ClientDto clientDto = clientDtoMapper.modelToDto(clientResponse);
        Response<ClientDto> response = new Response<>(200, clientDto, "Cliente actualizado exitosamente", "");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
