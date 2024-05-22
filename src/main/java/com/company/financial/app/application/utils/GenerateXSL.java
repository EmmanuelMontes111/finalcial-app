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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public final class GenerateXSL {

    public static void createXSL(List<ClientDto> clientDtos) {
        try {
            // Obtener el directorio actual del usuario
            String userDir = System.getProperty("user.dir");

            // Crear una instancia de DocumentBuilderFactory
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            // Crear una instancia de DocumentBuilder
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            // Crear un nuevo documento
            Document document = documentBuilder.newDocument();

            // Crear el elemento raíz del XSL
            Element xslElement = document.createElement("xsl:stylesheet");
            xslElement.setAttribute("version", "1.0");
            xslElement.setAttribute("xmlns:xsl", "http://www.w3.org/1999/XSL/Transform");
            document.appendChild(xslElement);

            // Crear el template
            Element templateElement = document.createElement("xsl:template");
            templateElement.setAttribute("match", "/");
            xslElement.appendChild(templateElement);

            // Crear la estructura HTML dentro del XSL
            Element htmlElement = document.createElement("html");
            templateElement.appendChild(htmlElement);

            Element bodyElement = document.createElement("body");
            htmlElement.appendChild(bodyElement);

            Element h2Element = document.createElement("h2");
            h2Element.appendChild(document.createTextNode("Company Clients"));
            bodyElement.appendChild(h2Element);

            Element tableElement = document.createElement("table");
            tableElement.setAttribute("border", "1");
            bodyElement.appendChild(tableElement);

            // Encabezados de la tabla
            Element trElement = document.createElement("tr");
            tableElement.appendChild(trElement);

            createTableHeader(document, trElement, "Identification Number");
            createTableHeader(document, trElement, "ID Type");
            createTableHeader(document, trElement, "Name");
            createTableHeader(document, trElement, "Last Name");
            createTableHeader(document, trElement, "Email");
            createTableHeader(document, trElement, "Birthdate");
            createTableHeader(document, trElement, "Creation Date");
            createTableHeader(document, trElement, "Modification Date");
            createTableHeader(document, trElement, "Money Invested");
            createTableHeader(document, trElement, "Percentage Invested");

            // Crear las filas de datos
            int totalMoneyInvested = 0;
            for (ClientDto clientDto : clientDtos) {
                Element dataRowElement = document.createElement("tr");
                tableElement.appendChild(dataRowElement);

                createTableData(document, dataRowElement, clientDto.getIdentificationNumber().toString());
                createTableData(document, dataRowElement, clientDto.getIdType());
                createTableData(document, dataRowElement, clientDto.getName());
                createTableData(document, dataRowElement, clientDto.getLastName());
                createTableData(document, dataRowElement, clientDto.getEmail());
                createTableData(document, dataRowElement, transformLongToDate(clientDto.getBirthdate()));
                createTableData(document, dataRowElement, transformLongToDate(clientDto.getCreationDate()));
                createTableData(document, dataRowElement, transformLongToDate(clientDto.getModificationDate()));
                createTableData(document, dataRowElement, String.valueOf(clientDto.getMoneyInvested()));
                createTableData(document, dataRowElement, String.valueOf(clientDto.getPercentageInvested()));

                totalMoneyInvested += clientDto.getMoneyInvested();
            }

            // Agregar el total de dinero invertido al final del documento
            Element totalElement = document.createElement("p");
            totalElement.appendChild(document.createTextNode("Total Money Invested: " + totalMoneyInvested));
            bodyElement.appendChild(totalElement);

            // Crear una instancia de TransformerFactory
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // Establecer la salida del transformer
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource domSource = new DOMSource(document);

            // Ruta del archivo XSL
            String xslFilePath = userDir + File.separator + "template.xsl";
            StreamResult streamResult = new StreamResult(new File(xslFilePath));

            // Transformar el DOM a un archivo
            transformer.transform(domSource, streamResult);

            System.out.println("Archivo XSL creado exitosamente en: " + xslFilePath);

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private static void createTableHeader(Document document, Element parent, String headerName) {
        Element thElement = document.createElement("th");
        thElement.appendChild(document.createTextNode(headerName));
        parent.appendChild(thElement);
    }

    private static void createTableData(Document document, Element parent, String value) {
        Element tdElement = document.createElement("td");
        tdElement.appendChild(document.createTextNode(value));
        parent.appendChild(tdElement);
    }

    private static String transformLongToDate(long longDate) {
        Date date = new Date(longDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}
