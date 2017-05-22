-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         5.7.16-log - MySQL Community Server (GPL)
-- SO del servidor:              Win64
-- HeidiSQL Versión:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Volcando estructura de base de datos para tempusfugit
CREATE DATABASE IF NOT EXISTS `tempusfugit` DEFAULT CHARACTER SET utf8;
USE `tempusfugit`;

-- Volcando estructura para tabla tempusfugit.categorias
CREATE TABLE IF NOT EXISTS `categorias` (
  `idCategoria` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `imagen` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idCategoria`),
  KEY `FK_categoria-imagen_idx` (`imagen`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla tempusfugit.categorias: ~2 rows (aproximadamente)
DELETE FROM `categorias`;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` (`idCategoria`, `nombre`, `imagen`) VALUES
	(1, 'Construcción', 'imgEX'),
	(2, 'Limpieza', 'imgEx'),
	(3, 'Tecnología', 'imgEx');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;

-- Volcando estructura para tabla tempusfugit.ciudades
CREATE TABLE IF NOT EXISTS `ciudades` (
  `idCiudad` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `estado` set('a','p') DEFAULT 'p',
  `latitud` decimal(10,8) DEFAULT '0.00000000',
  `longitud` decimal(11,8) DEFAULT '0.00000000',
  PRIMARY KEY (`idCiudad`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla tempusfugit.ciudades: ~3 rows (aproximadamente)
DELETE FROM `ciudades`;
/*!40000 ALTER TABLE `ciudades` DISABLE KEYS */;
INSERT INTO `ciudades` (`idCiudad`, `nombre`, `estado`, `latitud`, `longitud`) VALUES
	(1, '--Elige Ciudad--', 'a', 0.00000000, 0.00000000),
	(2, 'Mérida', 'a', 38.92414370, -6.33839820),
	(3, 'Cáceres', 'a', 39.47529340, -6.37250000),
	(4, 'Badajoz', 'a', 38.87944950, -6.97065350);
/*!40000 ALTER TABLE `ciudades` ENABLE KEYS */;

-- Volcando estructura para tabla tempusfugit.horarios
CREATE TABLE IF NOT EXISTS `horarios` (
  `idHorario` int(11) NOT NULL AUTO_INCREMENT,
  `horaInicio` int(2) NOT NULL,
  `horaFin` int(2) NOT NULL,
  `estado` set('o','l') NOT NULL DEFAULT 'l',
  `idOferta` int(11) NOT NULL,
  `fecha` date NOT NULL,
  PRIMARY KEY (`idHorario`),
  KEY `FK_horarios-servicios_idx` (`idOferta`),
  CONSTRAINT `FK_horarios-servicios` FOREIGN KEY (`idOferta`) REFERENCES `ofertas` (`idOferta`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla tempusfugit.horarios: ~0 rows (aproximadamente)
DELETE FROM `horarios`;
/*!40000 ALTER TABLE `horarios` DISABLE KEYS */;
/*!40000 ALTER TABLE `horarios` ENABLE KEYS */;

-- Volcando estructura para tabla tempusfugit.mensajes
CREATE TABLE IF NOT EXISTS `mensajes` (
  `idMensaje` int(11) NOT NULL AUTO_INCREMENT,
  `idUsuario` int(11) NOT NULL,
  `idOferta` int(11) NOT NULL,
  `tipo` set('pu','pri') NOT NULL DEFAULT 'pu',
  `asunto` varchar(45) DEFAULT NULL,
  `contenido` varchar(200) NOT NULL,
  `fecha` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `leido` set('s','n') DEFAULT 'n',
  PRIMARY KEY (`idMensaje`),
  KEY `FK_emisor-usuario_idx` (`idUsuario`),
  KEY `FK_servicioReceptor_idx` (`idOferta`),
  CONSTRAINT `FK_emisor-usuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`idUsuario`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_servicioReceptor` FOREIGN KEY (`idOferta`) REFERENCES `ofertas` (`idOferta`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla tempusfugit.mensajes: ~1 rows (aproximadamente)
DELETE FROM `mensajes`;
/*!40000 ALTER TABLE `mensajes` DISABLE KEYS */;
INSERT INTO `mensajes` (`idMensaje`, `idUsuario`, `idOferta`, `tipo`, `asunto`, `contenido`, `fecha`, `leido`) VALUES
	(20, 34, 17, 'pu', 'Prueba de mensaje', 'mensaje de prueba', '2017-05-23 00:42:45', 'n');
/*!40000 ALTER TABLE `mensajes` ENABLE KEYS */;

-- Volcando estructura para tabla tempusfugit.multimedia
CREATE TABLE IF NOT EXISTS `multimedia` (
  `idMultimedia` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `tipo` set('i','v') NOT NULL,
  `idOferta` int(11) NOT NULL,
  PRIMARY KEY (`idMultimedia`),
  KEY `FK_servicio_idx` (`idOferta`),
  CONSTRAINT `FK_servicio` FOREIGN KEY (`idOferta`) REFERENCES `ofertas` (`idOferta`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla tempusfugit.multimedia: ~0 rows (aproximadamente)
DELETE FROM `multimedia`;
/*!40000 ALTER TABLE `multimedia` DISABLE KEYS */;
/*!40000 ALTER TABLE `multimedia` ENABLE KEYS */;

-- Volcando estructura para tabla tempusfugit.ofertas
CREATE TABLE IF NOT EXISTS `ofertas` (
  `idOferta` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `descripcion` varchar(200) NOT NULL,
  `idSubcategoria` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `fechaInicio` date NOT NULL,
  `fechaFin` date NOT NULL,
  `estado` set('p','a') DEFAULT 'p',
  `imgPrincipal` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idOferta`),
  KEY `FK_servicio-subcategoria_idx` (`idSubcategoria`),
  KEY `FK_oferta-usuario_idx` (`idUsuario`),
  CONSTRAINT `FK_oferta-usuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_servicio-subcategoria` FOREIGN KEY (`idSubcategoria`) REFERENCES `subcategorias` (`idSubcategoria`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla tempusfugit.ofertas: ~3 rows (aproximadamente)
DELETE FROM `ofertas`;
/*!40000 ALTER TABLE `ofertas` DISABLE KEYS */;
INSERT INTO `ofertas` (`idOferta`, `nombre`, `descripcion`, `idSubcategoria`, `idUsuario`, `fechaInicio`, `fechaFin`, `estado`, `imgPrincipal`) VALUES
	(15, 'Reparacion de baldosas', 'Reparo baldosas', 1, 31, '2017-01-22', '2017-01-29', 'a', '15.jpg'),
	(16, 'Instalacion de Red Eléctrica', 'Me ofrezco a instalar la red electrica de un domicilio', 4, 30, '2017-01-23', '2017-01-29', 'a', '16.jpg'),
	(17, 'Reparacion de Portátiles', 'Reparo ordenadores portátiles', 3, 33, '2017-01-23', '2017-01-29', 'a', '17.jpg');
/*!40000 ALTER TABLE `ofertas` ENABLE KEYS */;

-- Volcando estructura para tabla tempusfugit.promocion
CREATE TABLE IF NOT EXISTS `promocion` (
  `idPromocion` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(45) NOT NULL,
  `saldo` int(11) NOT NULL,
  PRIMARY KEY (`idPromocion`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla tempusfugit.promocion: ~1 rows (aproximadamente)
DELETE FROM `promocion`;
/*!40000 ALTER TABLE `promocion` DISABLE KEYS */;
INSERT INTO `promocion` (`idPromocion`, `codigo`, `saldo`) VALUES
	(1, 'TempusFugit', 300);
/*!40000 ALTER TABLE `promocion` ENABLE KEYS */;

-- Volcando estructura para tabla tempusfugit.servicios
CREATE TABLE IF NOT EXISTS `servicios` (
  `idServicio` int(11) NOT NULL AUTO_INCREMENT,
  `idOferta` int(11) NOT NULL,
  `idCliente` int(11) NOT NULL,
  `estado` set('a','p','f') NOT NULL DEFAULT 'p',
  `puntuacion` int(11) NOT NULL,
  `idHorario` int(11) NOT NULL,
  `comentario` varchar(140) DEFAULT NULL,
  PRIMARY KEY (`idServicio`),
  KEY `FK_servicio-oferta_idx` (`idOferta`),
  KEY `FK_servicio-cliente_idx` (`idCliente`),
  KEY `FK_servicio-horario_idx` (`idHorario`),
  CONSTRAINT `FK_servicio-cliente` FOREIGN KEY (`idCliente`) REFERENCES `usuarios` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_servicio-horario` FOREIGN KEY (`idHorario`) REFERENCES `horarios` (`idHorario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_servicio-oferta` FOREIGN KEY (`idOferta`) REFERENCES `ofertas` (`idOferta`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla tempusfugit.servicios: ~0 rows (aproximadamente)
DELETE FROM `servicios`;
/*!40000 ALTER TABLE `servicios` DISABLE KEYS */;
/*!40000 ALTER TABLE `servicios` ENABLE KEYS */;

-- Volcando estructura para tabla tempusfugit.subcategorias
CREATE TABLE IF NOT EXISTS `subcategorias` (
  `idSubcategoria` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `idCategoria` int(11) NOT NULL,
  `imagen` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idSubcategoria`),
  KEY `FK_subcategoria-categoria_idx` (`idCategoria`),
  KEY `FK_subcategoria-imagen_idx` (`imagen`),
  CONSTRAINT `FK_subcategoria-categoria` FOREIGN KEY (`idCategoria`) REFERENCES `categorias` (`idCategoria`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla tempusfugit.subcategorias: ~4 rows (aproximadamente)
DELETE FROM `subcategorias`;
/*!40000 ALTER TABLE `subcategorias` DISABLE KEYS */;
INSERT INTO `subcategorias` (`idSubcategoria`, `nombre`, `idCategoria`, `imagen`) VALUES
	(1, 'Reparación', 1, 'imgEx'),
	(2, 'Limpieza del hogar', 2, 'imgEx'),
	(3, 'Aparatos Electrónicos', 3, 'imgEx'),
	(4, 'Instalaciónes Eléctricas', 1, 'imgEx');
/*!40000 ALTER TABLE `subcategorias` ENABLE KEYS */;

-- Volcando estructura para tabla tempusfugit.usuarios
CREATE TABLE IF NOT EXISTS `usuarios` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `apellidos` varchar(60) NOT NULL,
  `clave` varchar(45) NOT NULL,
  `email` varchar(60) NOT NULL,
  `idCiudad` int(11) DEFAULT NULL,
  `saldo` int(11) NOT NULL DEFAULT '0',
  `tipo` set('n','a','b') NOT NULL DEFAULT 'n',
  `fechaAlta` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `avatar` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idUsuario`),
  UNIQUE KEY `email` (`email`),
  KEY `FK_usuarios-ciudad_idx` (`idCiudad`),
  CONSTRAINT `FK_usuarios-ciudad` FOREIGN KEY (`idCiudad`) REFERENCES `ciudades` (`idCiudad`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla tempusfugit.usuarios: ~6 rows (aproximadamente)
DELETE FROM `usuarios`;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` (`idUsuario`, `nombre`, `apellidos`, `clave`, `email`, `idCiudad`, `saldo`, `tipo`, `fechaAlta`, `avatar`) VALUES
	(28, 'William', 'Shakespeare', '6jWWE5UwsqvnCJCCq1fsvQ==', 'william@mail.com', 2, 0, 'n', '2017-05-22 00:32:43', '28.jpg'),
	(30, 'Prueba', 'Mérida', '8CCSeUZ8LdrRBMKP29JMvg==', 'pruebam@mail.com', 2, 300, 'n', '2017-05-22 15:42:33', '30.jpg'),
	(31, 'Prueba', 'Badajoz', 'qqmH8ZulBdM//2+ruaDKVg==', 'pruebab@mail.com', 4, 0, 'n', '2017-05-22 15:57:21', '31.jpg'),
	(32, 'Albert', 'Einstein', 'sxoKNJaJxUBsAjHtHKAiBA==', 'albert@mail.com', 4, 0, 'n', '2017-05-23 00:37:03', '32.jpg'),
	(33, 'Prueba', 'Cáceres', '96VLb4m9D4tqe7bYtpyTUA==', 'pruebac@mail.com', 3, 0, 'n', '2017-05-23 00:38:49', '33.jpg'),
	(34, 'Friedrich Wilhem', 'Nietzsche', 'gmIBzZi2glp9hxCfIjK/IA==', 'nitz@mail.com', 3, 300, 'n', '2017-05-23 00:41:36', '34.jpg');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
