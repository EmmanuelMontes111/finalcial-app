package com.company.financial.app.domain.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class ClientRequest implements Serializable {
    private String idType;
    private Long identificationNumber;
    private String name;
    private String lastName;
    private String email;
    private Long birthdate;
}
