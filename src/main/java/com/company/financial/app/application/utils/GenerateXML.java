package com.company.financial.app.application.utils;

import com.company.financial.app.domain.model.dto.ClientDto;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public final class GenerateXML {
    public static void createTable(List<ClientDto> clientDtos) {
        try {
            // Crear una instancia de DocumentBuilderFactory
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            // Crear una instancia de DocumentBuilder
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            // Crear un nuevo documento
            Document document = documentBuilder.newDocument();

            // Crear el elemento raíz
            Element root = document.createElement("company");
            document.appendChild(root);

            // Crear el elemento tabla
            Element table = document.createElement("table");
            root.appendChild(table);

            // Crear los títulos de las columnas
            Element columnHeaders = document.createElement("columnHeaders");
            table.appendChild(columnHeaders);

            createColumnHeader(document, columnHeaders, "identificationNumber");
            createColumnHeader(document, columnHeaders, "idType");
            createColumnHeader(document, columnHeaders, "name");
            createColumnHeader(document, columnHeaders, "lastName");
            createColumnHeader(document, columnHeaders, "email");
            createColumnHeader(document, columnHeaders, "birthdate");
            createColumnHeader(document, columnHeaders, "creationDate");
            createColumnHeader(document, columnHeaders, "modificationDate");
            createColumnHeader(document, columnHeaders, "moneyInvested");
            createColumnHeader(document, columnHeaders, "percentageInvested");

            // Calcular el total de dinero invertido
            int totalMoneyInvested = 0;

            // Aquí puedes agregar filas de datos
            for (ClientDto clientDto : clientDtos) {
                addRow(document, table, clientDto.getIdentificationNumber().toString(), clientDto.getIdType(), clientDto.getName(),
                        clientDto.getLastName(), clientDto.getEmail(),
                        transformLongToDate(clientDto.getBirthdate()),
                        transformLongToDate(clientDto.getCreationDate()),
                        transformLongToDate(clientDto.getModificationDate()),
                        String.valueOf(clientDto.getMoneyInvested()), String.valueOf(clientDto.getPercentageInvested()));

                totalMoneyInvested += clientDto.getMoneyInvested();
            }

            // Agregar el total de dinero invertido al final del documento
            Element totalElement = document.createElement("totalMoneyInvested");
            totalElement.appendChild(document.createTextNode(String.valueOf(totalMoneyInvested)));
            root.appendChild(totalElement);

            // Crear una instancia de TransformerFactory
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // Establecer la salida del transformer
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(document);

            // Obtener la ruta del directorio actual
            String currentDir = System.getProperty("user.dir");

            // Ruta del archivo XML junto con el nombre del archivo
            String xmlFileName = "companyTable.xml";
            String xmlFilePath = currentDir + File.separator + xmlFileName;
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            // Transformar el DOM a un archivo
            transformer.transform(domSource, streamResult);

            System.out.println("Archivo XML creado exitosamente en: " + xmlFilePath);

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private static String transformLongToDate(long longDate) {
        Date date = new Date(longDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    private static void createColumnHeader(Document document, Element parent, String name) {
        Element column = document.createElement("column");
        column.appendChild(document.createTextNode(name));
        parent.appendChild(column);
    }

    private static void createColumnValue(Document document, Element parent, String value) {
        Element columnValue = document.createElement("columnValue");
        columnValue.appendChild(document.createTextNode(value));
        parent.appendChild(columnValue);
    }

    private static void addRow(Document document, Element parent, String identificationNumber, String idType,
                               String name, String lastName, String email, String birthdate, String creationDate,
                               String modificationDate, String moneyInvested, String percentageInvested) {
        Element row = document.createElement("row");
        parent.appendChild(row);

        createColumnValue(document, row, identificationNumber);
        createColumnValue(document, row, idType);
        createColumnValue(document, row, name);
        createColumnValue(document, row, lastName);
        createColumnValue(document, row, email);
        createColumnValue(document, row, birthdate);
        createColumnValue(document, row, creationDate);
        createColumnValue(document, row, modificationDate);
        createColumnValue(document, row, String.valueOf(moneyInvested));
        createColumnValue(document, row, String.valueOf(percentageInvested));
    }
}
