package com.company.financial.app.application.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

@WebServlet("/loadXml")
public class XmlServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {
            String xmlFilePath = "/companyTable.xml";

            // Leer el contenido del archivo XML
            ClassPathResource resource = new ClassPathResource(xmlFilePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(resource.getInputStream());

            // Obtener la lista de filas (rows)
            NodeList rows = doc.getElementsByTagName("row");

            // Iniciar la tabla HTML
            out.println("<html>");
            out.println("<head><title>Tabla de Datos</title></head>");
            out.println("<body>");
            out.println("<h2>Tabla de Datos</h2>");
            out.println("<table border=\"1\">");

            // Encabezados de columna
            out.println("<tr>");
            out.println("<th>identificationNumber</th>");
            out.println("<th>idType</th>");
            out.println("<th>name</th>");
            out.println("<th>lastName</th>");
            out.println("<th>email</th>");
            out.println("<th>birthdate</th>");
            out.println("<th>creationDate</th>");
            out.println("<th>modificationDate</th>");
            out.println("<th>moneyInvested</th>");
            out.println("<th>percentageInvested</th>");
            out.println("</tr>");

            // Iterar sobre las filas
            for (int i = 0; i < rows.getLength(); i++) {
                Element row = (Element) rows.item(i);

                NodeList columnValues = row.getElementsByTagName("columnValue");
                if (columnValues.getLength() >= 10) { // aseguramos que existan al menos 10 columnValue
                    String identificationNumber = columnValues.item(0).getTextContent();
                    String idType = columnValues.item(1).getTextContent();
                    String name = columnValues.item(2).getTextContent();
                    String lastName = columnValues.item(3).getTextContent();
                    String email = columnValues.item(4).getTextContent();
                    String birthdate = columnValues.item(5).getTextContent();
                    String creationDate = columnValues.item(6).getTextContent();
                    String modificationDate = columnValues.item(7).getTextContent();
                    String moneyInvested = columnValues.item(8).getTextContent();
                    String percentageInvested = columnValues.item(9).getTextContent();

                    // Agregar fila a la tabla HTML
                    out.println("<tr>");
                    out.println("<td>" + identificationNumber + "</td>");
                    out.println("<td>" + idType + "</td>");
                    out.println("<td>" + name + "</td>");
                    out.println("<td>" + lastName + "</td>");
                    out.println("<td>" + email + "</td>");
                    out.println("<td>" + birthdate + "</td>");
                    out.println("<td>" + creationDate + "</td>");
                    out.println("<td>" + modificationDate + "</td>");
                    out.println("<td>" + moneyInvested + "</td>");
                    out.println("<td>" + percentageInvested + "</td>");
                    out.println("</tr>");
                }
            }

            // Obtener el total de dinero invertido
            NodeList totalElements = doc.getElementsByTagName("totalMoneyInvested");
            if (totalElements.getLength() > 0) {
                String totalMoneyInvested = totalElements.item(0).getTextContent();

                // Agregar una fila para el total al final de la tabla HTML
                out.println("<tr>");
                out.println("<td colspan=\"8\" style=\"text-align:right;\"><b>Total Money Invested:</b></td>");
                out.println("<td colspan=\"2\"><b>" + totalMoneyInvested + "</b></td>");
                out.println("</tr>");
            }

            // Cerrar la tabla y el documento HTML
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al procesar el XML");
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
