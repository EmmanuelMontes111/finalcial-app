package com.company.financial.app.application.utils;

import com.company.financial.app.application.service.exception.PersistenceException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

public final class PdfToXmlConverter {

    @XmlRootElement
    public static class PdfContent {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static String getXmlByPdf(String pdfBase64){
        String readPdf = readPdfText(pdfBase64);
        return getXML(readPdf);
    }


    // Método para leer el texto de un PDF
    private static String readPdfText(String pdfBase64)  {
        // Decodificar desde base64 a byte[]
        byte[] pdfBytes = Base64.getDecoder().decode(pdfBase64);

        try (PDDocument document = PDDocument.load(new ByteArrayInputStream(pdfBytes))) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }catch (Exception ex){
            throw new PersistenceException("A ocurrido un error Generando el XML", ex);
        }
    }

    private static String getXML(String pdfText) {
        // Crear un objeto para encapsular el contenido del PDF
        PdfContent pdfContent = new PdfContent();
        pdfContent.setContent(pdfText);

        // Convertir el objeto a XML
        String xmlString = convertObjectToXml(pdfContent);

        // Convertir el XML a base64
        String base64Xml = convertToBase64(xmlString);

        deleteXMLFile();
        saveXMLFile(base64Xml);
        // Imprimir el XML generado
        System.out.println("PDF convertido a XML en base64:");
        return base64Xml;
    }

    private static void deleteXMLFile( ) {
        String xmlFilePath = "src/main/resources/static/archivo.xml";

        // Convertir la ruta a un objeto Path
        Path path = Paths.get(xmlFilePath);

        try {
            // Eliminar el archivo
            Files.delete(path);
            System.out.println("El archivo XML ha sido eliminado correctamente.");
        } catch (IOException e) {
            System.err.println("No se pudo eliminar el archivo XML: " + e.getMessage());
        }
    }

    private static void saveXMLFile(String base64String) {
        try {
            // Decodificar la cadena base64 a un byte[]
            byte[] decodedBytes = Base64.getDecoder().decode(base64String);

            // Convertir bytes decodificados a String
            String xmlContent = new String(decodedBytes);

            // Guardar el contenido XML en un archivo
            // Definir la ruta relativa al directorio de trabajo actual
            String relativePath = "src/main/resources/static/archivo.xml";

            // Obtener la ruta absoluta combinando el directorio de trabajo actual con la ruta relativa
            String absolutePath = Paths.get(System.getProperty("user.dir"), relativePath).toString();
            Files.write(Paths.get(absolutePath), xmlContent.getBytes(), StandardOpenOption.CREATE);

            System.out.println("Archivo XML decodificado guardado exitosamente en: " + absolutePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para convertir un objeto a XML
    private static String convertObjectToXml(Object object) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            marshaller.marshal(object, outputStream);

            return new String(outputStream.toByteArray(), StandardCharsets.UTF_8);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para convertir una cadena a base64
    private static String convertToBase64(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes(StandardCharsets.UTF_8));
    }
}