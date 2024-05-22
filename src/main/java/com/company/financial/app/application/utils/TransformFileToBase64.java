package com.company.financial.app.application.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;


public final class TransformFileToBase64 {

    public static String transformBase64(String filePath) {
        try {
            // Cargar el archivo  desde el directorio de recursos
            Resource resource = new ClassPathResource(filePath);
            InputStream inputStream = resource.getInputStream();

            // Leer el archivo en un array de bytes
            byte[] bytes = inputStream.readAllBytes();

            return Base64.getEncoder().encodeToString(bytes);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
