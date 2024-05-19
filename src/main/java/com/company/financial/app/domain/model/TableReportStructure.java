package com.company.financial.app.domain.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class TableReportStructure {
    private String titleDocument;
    private List<String>  titleTableList;
    private List<String> contentTableList;
}
