package com.company.financial.app.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDto  {
    private String id;
    private String idType;
    private Long identificationNumber;
    private String name;
    private String lastName;
    private String email;
    private Long birthdate;
    private Long creationDate;
    private Long modificationDate;
}
