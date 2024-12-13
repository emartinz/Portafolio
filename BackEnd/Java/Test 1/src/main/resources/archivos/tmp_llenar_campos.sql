-- Elimina la tabla si existe
DROP TABLE IF EXISTS `tmp_llenar_campos`;

-- Crea la tabla
CREATE TABLE `tmp_llenar_campos` (
  `id_company` int(11) DEFAULT NULL,
  `codigo_company` varchar(20) DEFAULT NULL,
  `name_company` varchar(50) DEFAULT NULL,
  `description_company` varchar(200) DEFAULT NULL,
  `version_id` int(11) DEFAULT NULL,
  `version_app_id` int(11) DEFAULT NULL,
  `version` varchar(20) DEFAULT NULL,
  `version_description` varchar(20) DEFAULT NULL,
  `version_company_id` int(11) DEFAULT NULL,
  `version_company_company_id` int(11) DEFAULT NULL,
  `version_company_version_id` int(11) DEFAULT NULL,
  `version_company_description` varchar(200) DEFAULT NULL,
  `app_id` int(11) DEFAULT NULL,
  `app_code` varchar(20) DEFAULT NULL,
  `app_name` varchar(50) DEFAULT NULL,
  `app_description` varchar(200) DEFAULT NULL
);

-- Inserta los datos
INSERT INTO `tmp_llenar_campos` (`id_company`, `codigo_company`, `name_company`, `description_company`, `version_id`, `version_app_id`, `version`, `version_description`, `version_company_id`, `version_company_company_id`, `version_company_version_id`, `version_company_description`, `app_id`, `app_code`, `app_name`, `app_description`) VALUES
(1, 'COMP001', 'Empresa 1', 'Descripcion C1', 1, 1, 'v1.0', 'Primera Version', 1, 1, 1, 'Primera Version para Empresa 1', 1, 'APP001', 'Aplicacion 1', 'Descripcion Aplicacion 1'),
(2, 'COMP002', 'Empresa 2', 'Descripcion C2', 2, 2, 'v1.0', 'Primera Version', 2, 2, 1, 'Primera Version para Empresa 2', 2, 'APP002', 'Aplicacion 2', 'Descripcion Aplicacion 2'),
(3, 'COMP001', 'Empresa 1', 'Descripcion C1_2', 3, 3, 'v2.0', 'Segunda Version', 2, 2, 1, 'Segunda Version para Empresa 1', 3, 'APP001', 'Aplicacion 1', 'Descripcion Aplicacion 1');
