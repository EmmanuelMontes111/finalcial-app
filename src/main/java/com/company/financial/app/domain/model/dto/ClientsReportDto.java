package com.company.financial.app.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class ClientsReportDto {
    double totalInvestmentClients;
    double totalInvestmentCompany;
    List<ClientDto> clientDtos;
}
