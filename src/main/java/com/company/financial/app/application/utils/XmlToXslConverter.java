package com.company.financial.app.application.utils;

import com.company.financial.app.application.service.exception.PersistenceException;

import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public final class XmlToXslConverter {


    public static String getXslByXml(String xmlBase64) {
        // Decodificar el XML desde base64 a byte[]
        byte[] xmlBytes = Base64.getDecoder().decode(xmlBase64);

        // Transformar el XML a XSL y luego a base64
        String xslBase64 = transformXmlToXslBase64(xmlBytes);

        return xslBase64;
    }

    private static String transformXmlToXslBase64(byte[] xmlBytes) {
        TransformerFactory factory = TransformerFactory.newInstance();
        Source xmlSource = new StreamSource(new ByteArrayInputStream(xmlBytes));

        // Plantilla de transformación XSL vacía
        Templates templates;
        try {
            templates = factory.newTemplates(new StreamSource(new ByteArrayInputStream("<xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"1.0\"></xsl:stylesheet>".getBytes())));
        } catch (Exception ex) {
            throw new PersistenceException("A ocurrido un error Generando el XSL en Templates ", ex);
        }
        // Transformar XML a XSL y convertir a base64
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Transformer transformer = templates.newTransformer();
            transformer.transform(xmlSource, new StreamResult(outputStream));

            // Convertir el resultado a base64
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (Exception ex) {
            throw new PersistenceException("A ocurrido un error Generando el XSL", ex);
        }
    }

}
