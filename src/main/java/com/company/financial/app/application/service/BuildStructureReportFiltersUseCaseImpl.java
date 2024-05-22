package com.company.financial.app.application.service;

import com.company.financial.app.application.usecases.BuildStructureReportFiltersUseCase;
import com.company.financial.app.application.utils.GeneratePdf;
import com.company.financial.app.application.utils.GenerateXML;
import com.company.financial.app.domain.model.TableReportStructure;
import com.company.financial.app.domain.model.dto.ClientDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BuildStructureReportFiltersUseCaseImpl implements BuildStructureReportFiltersUseCase {
    public static final String REPORT_FILTER_CLIENTS = "Reporte De Clientes Filtrados";

    @Override
    public String generateTableReportStructure(List<ClientDto> clientDtos) {
        GenerateXML.createTable( clientDtos);
        TableReportStructure tableReportStructure = TableReportStructure.builder()
                .titleDocument(REPORT_FILTER_CLIENTS)
                .titleTableList(setTitlesTable())
                .contentTableList(setContentBodyTable(clientDtos))
                .build();
        return GeneratePdf.createReport(tableReportStructure);
    }

    private List<String> setTitlesTable() {
        return Stream.of("Documento", "Tipo de documento", "Nombre", "Apellido", "Fecha de nacimiento")
                .collect(Collectors.toList());
    }


    private List<String> setContentBodyTable(List<ClientDto> clientDtos) {
        List<String> rowContent = new ArrayList<>();

        clientDtos.forEach(clientRequest -> {
            rowContent.add(clientRequest.getIdentificationNumber().toString());
            rowContent.add(clientRequest.getIdType());
            rowContent.add(clientRequest.getName());
            rowContent.add(clientRequest.getLastName());
            rowContent.add(doNotShowDateIfIsZero(clientRequest.getBirthdate()));
        });
        return rowContent;
    }

    private static String doNotShowDateIfIsZero(Long dateSchedule) {
        return Objects.isNull(dateSchedule)
                ? ""
                : dateSchedule.toString();
    }

}
