package com.company.financial.app.application.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;


public final class TransformFileToBase64 {

    public static String transformBase64(String fileName) {
        try {
            // Obtener el directorio actual del usuario
            String userDir = System.getProperty("user.dir");

            // Construir la ruta completa del archivo
            String filePath = userDir + File.separator + fileName;

            // Crear un flujo de entrada para el archivo
            File file = new File(filePath);
            InputStream inputStream = new FileInputStream(file);

            // Leer el archivo en un array de bytes
            byte[] bytes = inputStream.readAllBytes();

            // Convertir los bytes a Base64
            return Base64.getEncoder().encodeToString(bytes);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}