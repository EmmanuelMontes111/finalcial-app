package com.company.financial.app.application.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.springframework.core.io.ClassPathResource;

@WebServlet("/transformXml")
public class XslServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {
            // Rutas de los archivos XML y XSL
            String xmlFilePath = "/companyTable.xml";
            String xslFilePath = "/template.xsl";

            // Leer el archivo XML
            ClassPathResource xmlResource = new ClassPathResource(xmlFilePath);

            // Leer el archivo XSL
            ClassPathResource xslResource = new ClassPathResource(xslFilePath);

            // Crear una instancia de TransformerFactory
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer(new StreamSource(xslResource.getInputStream()));

            // Transformar el XML utilizando el XSL
            transformer.transform(new StreamSource(xmlResource.getInputStream()), new StreamResult(out));

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al procesar el XML con XSL");
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
