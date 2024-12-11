package com.test.t1.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.test.t1.application.utils.SqlScriptUtil;

import jakarta.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class DatabaseInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);
    private final DataSource dataSource;

    @Value("${spring.config.custom.database.execute-fill-elements}")
    private String executeFillElements;

    @Value("classpath:${spring.config.custom.database.create-fill-table-schema-location}")
    private Resource fillTableSchemaLocation;

    @Value("classpath:${spring.config.custom.database.create-fill-sp-location}")
    private Resource fillSpLocation;

    public DatabaseInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void init() {
        if(Boolean.parseBoolean(executeFillElements)){
            try (Connection connection = dataSource.getConnection()) {
                // Ejecutar script para creacion de tabla de llenado
                SqlScriptUtil.executeSqlScriptFile(connection, fillTableSchemaLocation);
    
                // Crear SP InsertarDatosDesdeTMP
                SqlScriptUtil.processSqlScript(connection, fillSpLocation, "//");
            } catch (Exception e) {
                logger.error("Error ejecutando scripts SQL: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}