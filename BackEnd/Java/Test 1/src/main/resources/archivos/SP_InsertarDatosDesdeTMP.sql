DELIMITER //
-- Borrar el procedimiento si existe
DROP PROCEDURE IF EXISTS InsertarDatosDesdeTMP //
CREATE PROCEDURE InsertarDatosDesdeTMP()
BEGIN
    -- Variables para los datos de TMP_LLENAR_CAMPOS
    DECLARE v_app_code VARCHAR(20);
    DECLARE v_app_name VARCHAR(50);
    DECLARE v_app_description VARCHAR(200);
    DECLARE v_version VARCHAR(20);
    DECLARE v_version_description VARCHAR(200);
    DECLARE v_company_code VARCHAR(20);
    DECLARE v_company_name VARCHAR(50);
    DECLARE v_company_description VARCHAR(200);
    DECLARE v_version_company_description VARCHAR(200);
    
    -- Variables para las llaves primarias
    DECLARE v_app_id INT;
    DECLARE v_company_id INT;
    DECLARE v_version_id INT;

    -- Variable para el estado del cursor
    DECLARE done INT DEFAULT FALSE;

    -- Cursor para recorrer TMP_LLENAR_CAMPOS
    DECLARE CTemporal CURSOR FOR
        SELECT app_code, app_name, app_description,
            version, version_description,
            codigo_company, name_company, description_company,
            version_company_description
        FROM TMP_LLENAR_CAMPOS;

    -- Manejador para finalizar el bucle
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    -- Abrir el cursor
    OPEN CTemporal;

    -- Bucle para recorrer el cursor
    read_loop: LOOP
        -- Obtener los valores del cursor
        FETCH CTemporal INTO v_app_code, v_app_name, v_app_description,
                            v_version, v_version_description,
                            v_company_code, v_company_name, v_company_description,
                            v_version_company_description;

        -- Si se ha llegado al final, salir del bucle
        IF done THEN
            LEAVE read_loop;
        END IF;

        -- Validar si la aplicación existe
        SELECT app_id INTO v_app_id
        FROM application
        WHERE app_code = v_app_code;

        -- Si no existe, insertarla
        IF v_app_id IS NULL THEN
            INSERT INTO application (app_code, app_name, app_description)
            VALUES (v_app_code, v_app_name, v_app_description);
            SET v_app_id = LAST_INSERT_ID();
            SELECT CONCAT('Aplicación agregada: ', v_app_code) AS message;
        ELSE
            SELECT CONCAT('Aplicación existente: ', v_app_id, " - ",v_app_code) AS message;
        END IF;

        -- Validar si la empresa existe
        SELECT id_company INTO v_company_id
        FROM company
        WHERE codigo_company = v_company_code;

        -- Si no existe, insertarla
        IF v_company_id IS NULL THEN
            INSERT INTO company (codigo_company, name_company, description_company)
            VALUES (v_company_code, v_company_name, v_company_description);
            SET v_company_id = LAST_INSERT_ID();
            SELECT CONCAT('Compañía agregada: ', v_company_code) AS message;
        ELSE
            SELECT CONCAT('Compañía existente: ', v_company_id, " - ", v_company_code) AS message;
        END IF;

        -- Validar si la versión existe
        SELECT version_id INTO v_version_id
        FROM version
        WHERE app_id = v_app_id AND version = v_version;

        -- Si no existe, insertarla
        IF v_version_id IS NULL THEN
            INSERT INTO version (app_id, version, version_description)
            VALUES (v_app_id, v_version, v_version_description);
            SET v_version_id = LAST_INSERT_ID();
            SELECT CONCAT('Versión agregada: ', v_version) AS message;
        ELSE
            SELECT CONCAT('Versión existente: ', v_version_id, " - ", v_version) AS message;
        END IF;

        -- Insertar en la tabla `version_company`, evitando duplicados
        INSERT IGNORE INTO version_company (company_id, version_id, version_company_description)
        VALUES (v_company_id, v_version_id, v_version_company_description);
        SELECT CONCAT('Registro en version_company procesado: ', v_company_id, ', ', v_version_id) AS message;
    END LOOP;

    -- Cerrar el cursor
    CLOSE CTemporal;

    SELECT 'Proceso completado' AS message;
END //

DELIMITER ;