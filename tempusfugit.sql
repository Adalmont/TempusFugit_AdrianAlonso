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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla tempusfugit.categorias: ~3 rows (aproximadamente)
DELETE FROM `categorias`;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` (`idCategoria`, `nombre`, `imagen`) VALUES
	(1, 'Construcción', 'imgEX'),
	(3, 'Tecnología', 'imgEx'),
	(4, 'Limpieza', 'imgEX');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;

-- Volcando estructura para tabla tempusfugit.ciudades
CREATE TABLE IF NOT EXISTS `ciudades` (
  `idCiudad` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `estado` set('a','p') DEFAULT 'p',
  `latitud` decimal(10,8) DEFAULT '0.00000000',
  `longitud` decimal(11,8) DEFAULT '0.00000000',
  PRIMARY KEY (`idCiudad`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla tempusfugit.ciudades: ~10 rows (aproximadamente)
DELETE FROM `ciudades`;
/*!40000 ALTER TABLE `ciudades` DISABLE KEYS */;
INSERT INTO `ciudades` (`idCiudad`, `nombre`, `estado`, `latitud`, `longitud`) VALUES
	(1, '--Elegir Ciudad--', 'a', 0.00000000, 0.00000000),
	(2, '--Nueva Ciudad--', 'a', 0.00000000, 0.00000000),
	(3, 'Mérida', 'a', 38.92414370, -6.33839820),
	(4, 'Cáceres', 'a', 39.47529340, -6.37250000),
	(5, 'Badajoz', 'a', 38.87944950, -6.97065350),
	(6, 'Zaragoza', 'a', 41.64882260, -0.88908530),
	(7, 'Madrid', 'a', 40.41677540, -3.70379020),
	(8, 'Cuenca', 'a', 21.52175700, -77.78116700),
	(11, 'Malaga', 'a', 36.72127370, -4.42139880),
	(12, 'Cádiz', 'a', 36.52706120, -6.28859620);
/*!40000 ALTER TABLE `ciudades` ENABLE KEYS */;

-- Volcando estructura para tabla tempusfugit.horarios
CREATE TABLE IF NOT EXISTS `horarios` (
  `idHorario` int(11) NOT NULL AUTO_INCREMENT,
  `horaInicio` int(4) NOT NULL,
  `horaFin` int(4) NOT NULL,
  `estado` set('o','l','s') NOT NULL DEFAULT 'l',
  `idOferta` int(11) NOT NULL,
  `fecha` date NOT NULL,
  PRIMARY KEY (`idHorario`),
  KEY `FK_horarios-servicios_idx` (`idOferta`),
  CONSTRAINT `FK_horarios-servicios` FOREIGN KEY (`idOferta`) REFERENCES `ofertas` (`idOferta`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla tempusfugit.horarios: ~18 rows (aproximadamente)
DELETE FROM `horarios`;
/*!40000 ALTER TABLE `horarios` DISABLE KEYS */;
INSERT INTO `horarios` (`idHorario`, `horaInicio`, `horaFin`, `estado`, `idOferta`, `fecha`) VALUES
	(95, 1020, 1140, 'l', 42, '2017-01-19'),
	(96, 1020, 1140, 'l', 42, '2017-01-20'),
	(97, 1020, 1140, 'l', 42, '2017-01-21'),
	(98, 660, 750, 'l', 43, '2017-01-21'),
	(99, 660, 750, 'l', 43, '2017-01-22'),
	(100, 660, 750, 'l', 43, '2017-01-23'),
	(101, 660, 750, 'l', 43, '2017-01-24'),
	(102, 660, 750, 'l', 43, '2017-01-25'),
	(103, 660, 750, 'l', 43, '2017-01-26'),
	(104, 660, 750, 'l', 43, '2017-01-27'),
	(105, 660, 750, 'l', 43, '2017-01-28'),
	(106, 660, 750, 'l', 43, '2017-01-29'),
	(107, 660, 750, 'l', 43, '2017-01-30'),
	(108, 1050, 1140, 'l', 44, '2017-01-24'),
	(109, 1050, 1140, 'l', 44, '2017-01-25'),
	(110, 1050, 1140, 'l', 44, '2017-01-26'),
	(111, 1050, 1140, 'l', 44, '2017-01-27'),
	(112, 1050, 1140, 'l', 44, '2017-01-28');
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
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla tempusfugit.mensajes: ~1 rows (aproximadamente)
DELETE FROM `mensajes`;
/*!40000 ALTER TABLE `mensajes` DISABLE KEYS */;
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
  CONSTRAINT `FK_oferta-usuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`idUsuario`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_servicio-subcategoria` FOREIGN KEY (`idSubcategoria`) REFERENCES `subcategorias` (`idSubcategoria`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla tempusfugit.ofertas: ~3 rows (aproximadamente)
DELETE FROM `ofertas`;
/*!40000 ALTER TABLE `ofertas` DISABLE KEYS */;
INSERT INTO `ofertas` (`idOferta`, `nombre`, `descripcion`, `idSubcategoria`, `idUsuario`, `fechaInicio`, `fechaFin`, `estado`, `imgPrincipal`) VALUES
	(42, 'Reparacion de Puertas', 'Reparo puertas de todo tipo', 1, 38, '2017-01-19', '2017-01-21', 'a', '42.jpg'),
	(43, 'Arreglo de Portatiles', 'Reparo ordenadores portatiles de cualquier marca', 3, 34, '2017-01-21', '2017-01-30', 'a', '43.jpg'),
	(44, 'Instalacion de redes electricas', 'Instalo redes electricas en viviendas', 4, 32, '2017-01-24', '2017-01-28', 'a', '44.jpg');
/*!40000 ALTER TABLE `ofertas` ENABLE KEYS */;

-- Volcando estructura para tabla tempusfugit.promocion
CREATE TABLE IF NOT EXISTS `promocion` (
  `idPromocion` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(45) NOT NULL,
  `saldo` int(11) NOT NULL,
  PRIMARY KEY (`idPromocion`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla tempusfugit.promocion: ~3 rows (aproximadamente)
DELETE FROM `promocion`;
/*!40000 ALTER TABLE `promocion` DISABLE KEYS */;
INSERT INTO `promocion` (`idPromocion`, `codigo`, `saldo`) VALUES
	(1, 'TempusFugit', 300),
	(2, 'Prueba', 200),
	(3, 'Codigo', 300);
/*!40000 ALTER TABLE `promocion` ENABLE KEYS */;

-- Volcando estructura para tabla tempusfugit.servicios
CREATE TABLE IF NOT EXISTS `servicios` (
  `idServicio` int(11) NOT NULL AUTO_INCREMENT,
  `idOferta` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `idCreador` int(11) NOT NULL,
  `estado` set('a','p','f') NOT NULL DEFAULT 'p',
  `puntuacion` int(11) DEFAULT NULL,
  `idHorario` int(11) NOT NULL,
  `comentario` varchar(140) DEFAULT NULL,
  PRIMARY KEY (`idServicio`),
  KEY `FK_servicio-oferta_idx` (`idOferta`),
  KEY `FK_servicio-cliente_idx` (`idUsuario`),
  KEY `FK_servicio-horario_idx` (`idHorario`),
  KEY `FK_servicio-creador` (`idCreador`),
  CONSTRAINT `FK_servicio-cliente` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`idUsuario`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_servicio-creador` FOREIGN KEY (`idCreador`) REFERENCES `usuarios` (`idUsuario`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_servicio-horario` FOREIGN KEY (`idHorario`) REFERENCES `horarios` (`idHorario`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_servicio-oferta` FOREIGN KEY (`idOferta`) REFERENCES `ofertas` (`idOferta`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla tempusfugit.subcategorias: ~4 rows (aproximadamente)
DELETE FROM `subcategorias`;
/*!40000 ALTER TABLE `subcategorias` DISABLE KEYS */;
INSERT INTO `subcategorias` (`idSubcategoria`, `nombre`, `idCategoria`, `imagen`) VALUES
	(1, 'Reparación', 1, 'imgEx'),
	(3, 'Aparatos Electrónicos', 3, 'imgEx'),
	(4, 'Instalaciónes Eléctricas', 1, 'imgEx'),
	(5, 'Limpieza del Hogar', 4, 'imgEX');
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
  `estado` set('r','e') NOT NULL DEFAULT 'r',
  PRIMARY KEY (`idUsuario`),
  UNIQUE KEY `email` (`email`),
  KEY `FK_usuarios-ciudad_idx` (`idCiudad`),
  CONSTRAINT `FK_usuarios-ciudad` FOREIGN KEY (`idCiudad`) REFERENCES `ciudades` (`idCiudad`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla tempusfugit.usuarios: ~7 rows (aproximadamente)
DELETE FROM `usuarios`;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` (`idUsuario`, `nombre`, `apellidos`, `clave`, `email`, `idCiudad`, `saldo`, `tipo`, `fechaAlta`, `avatar`, `estado`) VALUES
	(31, 'Prueba', 'Badajoz', 'qqmH8ZulBdM//2+ruaDKVg==', 'pruebab@mail.com', 5, 0, 'n', '2017-05-22 15:57:21', '31.jpg', 'r'),
	(32, 'Albert', 'Einstein', 'sxoKNJaJxUBsAjHtHKAiBA==', 'albert@mail.com', 5, 0, 'n', '2017-05-23 00:37:03', '32.jpg', 'r'),
	(33, 'Prueba', 'Cáceres', '96VLb4m9D4tqe7bYtpyTUA==', 'pruebac@mail.com', 4, 0, 'n', '2017-05-23 00:38:49', '33.jpg', 'r'),
	(34, 'Friedrich Wilhem', 'Nietzsche', 'gmIBzZi2glp9hxCfIjK/IA==', 'nitz@mail.com', 4, 300, 'n', '2017-05-23 00:41:36', '34.jpg', 'r'),
	(38, 'William', 'Shakespeare', '6jWWE5UwsqvnCJCCq1fsvQ==', 'william@mail.com', 3, 120, 'n', '2017-06-06 01:18:40', '38.jpg', 'r'),
	(39, 'Prueba', 'Mérida', '8CCSeUZ8LdrRBMKP29JMvg==', 'pruebam@mail.com', 3, 420, 'n', '2017-06-06 01:22:02', '39.jpg', 'r'),
	(40, 'Administrador', 'del Sistema', 'ISMvKXpXpadDiUoOSoAfww==', 'admin@mail.com', 3, 0, 'a', '2017-06-06 17:00:55', '40.jpg', 'r');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
