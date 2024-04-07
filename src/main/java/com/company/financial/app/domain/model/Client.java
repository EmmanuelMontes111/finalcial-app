package com.company.financial.app.domain.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Client {
    private Long id;
    private String idType;
    private Long identificationNumber;
    private String name;
    private String lastName;
    private String email;
    private Long birthdate;
    private Long creationDate;
    private Long modificationDate;
}
