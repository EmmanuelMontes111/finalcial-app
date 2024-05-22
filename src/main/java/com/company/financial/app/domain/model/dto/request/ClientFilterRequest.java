package com.company.financial.app.domain.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Builder
@Getter
public class ClientFilterRequest implements Serializable {
    private String idType;
    private Long identificationNumber;
    private String name;
    private String lastName;
    private String email;
    private double moneyInvested;
    private String operatorComparator;
}
