package com.company.financial.app.application.servelet;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.Setter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
@Service
@WebServlet("/loadXml")
public class XmlServlet extends HttpServlet {

    private static final String XML_FILE_PATH = "archivo.xml";

    @Setter
    private String xmlContent = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String xmlFilePath = "/static/archivo.xml"; // Ruta del archivo XML relativa a /resources/static

        // Leer el contenido del archivo XML
        ClassPathResource resource = new ClassPathResource(xmlFilePath);
        byte[] xmlBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
        String xmlContent = new String(xmlBytes);

        // Construir la respuesta HTML
        StringBuilder htmlResponse = new StringBuilder();
        htmlResponse.append("<!DOCTYPE html>");
        htmlResponse.append("<html lang=\"en\">");
        htmlResponse.append("<head>");
        htmlResponse.append("<meta charset=\"UTF-8\">");
        htmlResponse.append("<title>XML</title>");
        htmlResponse.append("<style>");
        htmlResponse.append("table, th, td {");
        htmlResponse.append("border: 1px solid black;");
        htmlResponse.append("border-collapse: collapse;");
        htmlResponse.append("}");
        htmlResponse.append("th, td {");
        htmlResponse.append("padding: 5px;");
        htmlResponse.append("}");
        htmlResponse.append("</style>");
        htmlResponse.append("</head>");
        htmlResponse.append("<body>");
        htmlResponse.append("<div id=\"texto\">");
        htmlResponse.append("<h1>DATOS XML</h1>");
        htmlResponse.append("<button id=\"CargarDatos\">Cargar Datos XML</button>");
        htmlResponse.append("</div>");
        htmlResponse.append("<table id=\"demo\"></table>");
        htmlResponse.append("<script>");
        htmlResponse.append("document.getElementById(\"CargarDatos\").addEventListener(\"click\", CargarDatos);");
        htmlResponse.append("function CargarDatos() {");
        htmlResponse.append("var xhr = new XMLHttpRequest();");
        htmlResponse.append("xhr.onreadystatechange = function() {");
        htmlResponse.append("if (this.readyState == 4 && this.status == 200) {");
        htmlResponse.append("cargarXML(this);");
        htmlResponse.append("}");
        htmlResponse.append("};");
        htmlResponse.append("xhr.open(\"GET\", \"archivo.xml\", true);"); // Ruta relativa al archivo XML
        htmlResponse.append("xhr.send();");
        htmlResponse.append("}");
        htmlResponse.append("function cargarXML(xml) {");
        htmlResponse.append("var docXML = xml.responseXML;");
        htmlResponse.append("var content = docXML.getElementsByTagName(\"content\")[0].textContent.trim();");
        htmlResponse.append("content = content.replace(/&#13;/g, '\\n');");
        htmlResponse.append("var lines = content.split('\\n').map(line => line.trim()).filter(line => line.length > 0);");
        htmlResponse.append("var tabla = \"<tr><th>Documento</th><th>Tipo de documento</th><th>Nombre</th><th>Apellido</th><th>Fecha de nacimiento</th></tr>\";");
        htmlResponse.append("for (var i = 3; i < lines.length; i++) {");
        htmlResponse.append("var line = lines[i];");
        htmlResponse.append("var cells = line.split(' ');");
        htmlResponse.append("if (cells.length > 5) {");
        htmlResponse.append("var documento = cells[0];");
        htmlResponse.append("var tipoDocumento = cells[1];");
        htmlResponse.append("var nombre = cells[2];");
        htmlResponse.append("var apellido = cells.slice(3, cells.length - 1).join(' ');");
        htmlResponse.append("var fechaNacimiento = cells[cells.length - 1];");
        htmlResponse.append("cells = [documento, tipoDocumento, nombre, apellido, fechaNacimiento];");
        htmlResponse.append("}");
        htmlResponse.append("if (cells.length === 5) {");
        htmlResponse.append("tabla += \"<tr>\";");
        htmlResponse.append("for (var j = 0; j < cells.length; j++) {");
        htmlResponse.append("tabla += \"<td>\" + cells[j] + \"</td>\";");
        htmlResponse.append("}");
        htmlResponse.append("tabla += \"</tr>\";");
        htmlResponse.append("}");
        htmlResponse.append("}");
        htmlResponse.append("document.getElementById(\"demo\").innerHTML = tabla;");
        htmlResponse.append("}");
        htmlResponse.append("</script>");
        htmlResponse.append("<script>");
        htmlResponse.append("fetch('https://api.example.com/data')");
        htmlResponse.append(".then(response => {");
        htmlResponse.append("if (!response.ok) {");
        htmlResponse.append("throw new Error('Network response was not ok ' + response.statusText);");
        htmlResponse.append("}");
        htmlResponse.append("return response.json();");
        htmlResponse.append("})");
        htmlResponse.append(".then(data => {");
        htmlResponse.append("console.log(data);");
        htmlResponse.append("})");
        htmlResponse.append(".catch(error => {");
        htmlResponse.append("console.error('Hubo un problema con la petición fetch:', error);");
        htmlResponse.append("});");
        htmlResponse.append("</script>");
        htmlResponse.append("</body>");
        htmlResponse.append("</html>");

        out.println(htmlResponse.toString());
    }
}