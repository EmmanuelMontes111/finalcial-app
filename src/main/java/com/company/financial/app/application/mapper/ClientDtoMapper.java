package com.company.financial.app.application.mapper;

import com.company.financial.app.domain.model.Client;
import com.company.financial.app.domain.model.dto.ClientDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientDtoMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "idType", target = "idType")
    @Mapping(source = "identificationNumber", target = "identificationNumber")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "birthdate", target = "birthdate")
    @Mapping(source = "creationDate", target = "creationDate")
    @Mapping(source = "modificationDate", target = "modificationDate")
    ClientDto modelToDto(Client client);
    Client dtoToModel(ClientDto client);
}
