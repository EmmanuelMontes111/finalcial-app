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
        String pdfText;
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

        // Imprimir el XML generado
        System.out.println("PDF convertido a XML en base64:");
        return base64Xml;
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