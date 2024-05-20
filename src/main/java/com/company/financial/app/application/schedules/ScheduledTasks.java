package com.company.financial.app.application.schedules;

import com.company.financial.app.application.servelet.XmlServlet;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;

@Component
public class ScheduledTasks {

    private final XmlServlet xmlServlet;

    public ScheduledTasks(XmlServlet xmlServlet) {
        this.xmlServlet = xmlServlet;
    }

    @Scheduled(fixedRate = 5000) // Ejecuta cada 5 segundos
    public void refreshXmlContent() {
        try {
            // Leer el contenido del archivo XML
            byte[] xmlBytes = FileCopyUtils.copyToByteArray(getClass().getClassLoader().getResourceAsStream("static/archivo.xml"));
            String xmlContent = new String(xmlBytes);

            // Actualizar el contenido del servlet
            xmlServlet.setXmlContent(xmlContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
