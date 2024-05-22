package com.company.financial.app.application.usecases;

import com.company.financial.app.domain.model.dto.ClientDto;
import com.company.financial.app.domain.model.dto.ClientsReportDto;
import com.company.financial.app.domain.model.dto.request.ClientRequest;
import com.company.financial.app.domain.model.responseRest.Response;
import org.springframework.http.ResponseEntity;

public interface ClientService {

    ResponseEntity<Response<ClientDto>> createClient(ClientRequest clientRequest);

    ResponseEntity<Response<ClientDto>> getClientById(Long identificationNumber);

    ResponseEntity<Response<ClientsReportDto>> getAllClients();
    ResponseEntity<Response> getClientsByFilter(ClientRequest clientRequest);
    ResponseEntity<Response> getClientReportsPDF(ClientRequest clientRequest);
    ResponseEntity<Response> getClientReportsXML(ClientRequest clientRequest);
    ResponseEntity<Response> getClientReportXSL(ClientRequest clientRequest);

    ResponseEntity<Response> deleteClientById(Long identificationNumber);

    ResponseEntity<Response<ClientDto>> updateClient(Long identificationNumber, ClientRequest clientRequest);
}
