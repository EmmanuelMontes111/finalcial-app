package com.company.financial.app.domain.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class ClientFilter {
    private String idType;
    private Long identificationNumber;
    private String name;
    private String lastName;
    private String email;
    private double moneyInvested;
    private String operatorComparator;
}
