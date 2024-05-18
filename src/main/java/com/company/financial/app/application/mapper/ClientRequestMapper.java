package com.company.financial.app.application.mapper;

import com.company.financial.app.domain.model.Client;
import com.company.financial.app.domain.model.dto.request.ClientRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientRequestMapper {
    @Mapping(source = "idType", target = "idType")
    @Mapping(source = "identificationNumber", target = "identificationNumber")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "birthdate", target = "birthdate")
    Client  requestToModel(ClientRequest clientRequest);
}
