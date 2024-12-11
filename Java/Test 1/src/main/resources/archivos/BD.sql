-- Crear base de datos
DROP DATABASE IF EXISTS bd_test_ada 
CREATE DATABASE bd_test_ada;
-- CREATE DATABASE IF NOT EXISTS bd_test_ada;

USE bd_test_ada;

-- Crear tabla `application`
CREATE TABLE IF NOT EXISTS application (
    app_id INT AUTO_INCREMENT PRIMARY KEY,
    app_code VARCHAR(20) NOT NULL UNIQUE,
    app_name VARCHAR(50) NOT NULL,
    app_description VARCHAR(200) NULL
);

-- Crear tabla `company`
CREATE TABLE IF NOT EXISTS company (
    id_company INT AUTO_INCREMENT PRIMARY KEY,
    codigo_company VARCHAR(20) NOT NULL UNIQUE,
    name_company VARCHAR(50) NOT NULL,
    description_company VARCHAR(200) NULL
);

-- Crear tabla `version`
CREATE TABLE IF NOT EXISTS version (
    version_id INT AUTO_INCREMENT PRIMARY KEY,
    app_id INT NOT NULL UNIQUE,
    version VARCHAR(20) NOT NULL,
    version_description VARCHAR(200) NULL,
    FOREIGN KEY (app_id) REFERENCES application(app_id)
);

-- Crear tabla `version_company`
CREATE TABLE IF NOT EXISTS version_company (
    version_company_id INT AUTO_INCREMENT PRIMARY KEY,
    company_id INT NOT NULL,
    version_id INT NOT NULL,
    version_company_description VARCHAR(200) NULL,
    FOREIGN KEY (company_id) REFERENCES company(id_company),
    FOREIGN KEY (version_id) REFERENCES version(version_id),
    UNIQUE (company_id, version_id) -- Restricción para evitar duplicados
);

