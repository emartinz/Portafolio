package com.test.t1.application.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

public class SqlScriptUtil {
    private static final Logger logger = LoggerFactory.getLogger(SqlScriptUtil.class);

    /**
     * Metodo para ejecutar un script sql desde un archivo
     * @param connection
     * @param resource
     * @throws IOException
     */
    public static void executeSqlScriptFile(Connection connection, Resource resource) throws IOException {
        if (resource.exists()) {
            logger.info("Ejecutando script: " + resource.getFilename());
            ScriptUtils.executeSqlScript(connection, resource);
            logger.info("Script ejecutado exitosamente: " + resource.getFilename());
        } else {
            logger.error("Archivo SQL no encontrado: " + resource.getFilename());
        }
    }

    /**
     * Metodo para ejecutar un archivo SQL el cual contenga multiples lineas o Delimiter especifico
     * 
     * @param connection Coneccion SQL
     * @param resource Ruta dentro de resources
     * @param delimiter Delimitador
     * @throws Exception
     */
    public static void processSqlScript(Connection connection, Resource resource, String delimiter) throws Exception {
        if (resource.exists()) {
            logger.info("Cargando Script SQL: " + resource.getFilename());
    
            // Leer el archivo SQL completo
            StringBuilder scriptBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    scriptBuilder.append(line).append(System.lineSeparator());
                }
            }
    
            // Obtener el script como String
            String script = scriptBuilder.toString();
    
            // Eliminar las líneas de DELIMITER si existen
            script = script.replaceAll("([\\s]+" + delimiter + "([\\n\\r]+)?)?DELIMITER([\\s]+)?(" + delimiter + "([\\s]+)?)?", "").trim();
    
            // Establecer el delimitador en la conexión antes de ejecutar el script
            try (Statement statement = connection.createStatement()) {
                // Dividir el script de acuerdo al delimitador y ejecutar cada parte
                String[] statements = script.split(delimiter);
    
                for (String statementText : statements) {
                    logger.info("procesando: \n" + statementText);

                    // Se verifica que la sentencia no este vacia
                    if (!statementText.trim().isEmpty()) {
                        statement.execute(statementText.trim());
                        logger.info("Sentencia ejecutada correctamente.");
                    }
                }
                logger.info("Proceso SQL ejecutado correctamente.");
            }
        } else {
            throw new IllegalArgumentException("No se encontró el archivo SQL: " + resource.getFilename());
        }
    }
}
