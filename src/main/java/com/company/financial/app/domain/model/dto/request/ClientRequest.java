package com.company.financial.app.domain.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ClientRequest {
    private String idType;
    private Long identificationNumber;
    private String name;
    private String lastName;
    private String email;
    private String birthdate;
}
