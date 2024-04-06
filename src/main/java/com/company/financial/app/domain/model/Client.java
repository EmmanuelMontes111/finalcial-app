package com.company.financial.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Client {
    private Long id;
    private String idType;
    private Long identificationNumber;
    private String name;
    private String lastName;
    private String email;
    private String birthdate;
    private Long creationDate;
    private Long modificationDate;
}
