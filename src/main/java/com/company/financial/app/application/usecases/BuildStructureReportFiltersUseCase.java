package com.company.financial.app.application.usecases;

import com.company.financial.app.domain.model.dto.ClientDto;

import java.util.List;

public interface BuildStructureReportFiltersUseCase {
    String generateTableReportStructure(List<ClientDto> clientDtos);
}
