package com.company.financial.app.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDto {
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
