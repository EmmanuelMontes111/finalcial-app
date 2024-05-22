package com.company.financial.app.application.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

@WebServlet("/transformXml")
public class XslServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {
            // Obtener el directorio actual del usuario
            String userDir = System.getProperty("user.dir");

            // Rutas de los archivos XML y XSL
            String xmlFilePath = userDir + File.separator + "companyTable.xml";
            String xslFilePath = userDir + File.separator + "template.xsl";

            // Verificar si los archivos existen
            File xmlFile = new File(xmlFilePath);
            File xslFile = new File(xslFilePath);

            if (!xmlFile.exists() || !xslFile.exists()) {
                throw new IOException("No se encontraron los archivos XML y XSL en el directorio actual del usuario.");
            }

            // Crear una instancia de TransformerFactory
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer(new StreamSource(xslFile));

            // Transformar el XML utilizando el XSL
            transformer.transform(new StreamSource(xmlFile), new StreamResult(out));

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
