-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 02-03-2015 a las 23:49:08
-- Versión del servidor: 5.6.21
-- Versión de PHP: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `comandapp`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `bar`
--

CREATE TABLE IF NOT EXISTS `bar` (
  `Id_Bar` int(10) NOT NULL,
  `Nombre` text NOT NULL,
  `Direccion` text NOT NULL,
  `Telefono` int(9) NOT NULL DEFAULT '0',
  `Longitud` double NOT NULL DEFAULT '0',
  `Latitud` double NOT NULL DEFAULT '0',
  `Provincia` text NOT NULL,
  `Municipio` text NOT NULL,
  `Correo` text NOT NULL,
  `Contraseña` text NOT NULL,
  `VersionInfoBar` int(11) NOT NULL,
  `VersionCarta` int(11) NOT NULL,
  `VersionOfertas` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `bar`
--

INSERT INTO `bar` (`Id_Bar`, `Nombre`, `Direccion`, `Telefono`, `Longitud`, `Latitud`, `Provincia`, `Municipio`, `Correo`, `Contraseña`, `VersionInfoBar`, `VersionCarta`, `VersionOfertas`) VALUES
(1, 'Bar Paco', 'Calle Elefante', 12345678, 5.5, 5.5, 'Málaga', 'Estpona', 'uno@dos.com', 'algo', 1, 0, 1),
(2, 'Restaurante Chino', 'Calle Caniche', 654321, 8.8, 8.8, 'Shangay', 'Tai', 'dos@uno.xcom', 'asdf', 1, 0, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `camarero`
--

CREATE TABLE IF NOT EXISTS `camarero` (
  `Id_Bar` int(10) NOT NULL,
  `Nombre` text NOT NULL,
  `Apellidos` text NOT NULL,
  `Contraseña` text NOT NULL,
  `Id_Camarero` int(10) NOT NULL,
  `nick` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `camarero`
--

INSERT INTO `camarero` (`Id_Bar`, `Nombre`, `Apellidos`, `Contraseña`, `Id_Camarero`, `nick`) VALUES
(1, 'Jesus', 'Allona', '123456', 1, 'JA');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carta`
--

CREATE TABLE IF NOT EXISTS `carta` (
  `Id_Producto` int(10) NOT NULL,
  `Id_Bar` int(10) NOT NULL,
  `Precio` double NOT NULL,
  `Descripcion` text NOT NULL,
  `Foto` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `carta`
--

INSERT INTO `carta` (`Id_Producto`, `Id_Bar`, `Precio`, `Descripcion`, `Foto`) VALUES
(2, 1, 1.5, 'CocaCola Lata 33 cl', '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');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comanda`
--

CREATE TABLE IF NOT EXISTS `comanda` (
  `Id_bar` int(10) NOT NULL,
  `Id_Comanda` int(10) NOT NULL,
  `Fecha` date NOT NULL,
  `Mesa` int(2) NOT NULL,
  `Coste_Total` double NOT NULL,
  `Id_camarero` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `comanda`
--

INSERT INTO `comanda` (`Id_bar`, `Id_Comanda`, `Fecha`, `Mesa`, `Coste_Total`, `Id_camarero`) VALUES
(1, 1, '3915-03-23', 1, 32.5, 1),
(1, 2, '2015-02-23', 1, 43, 1),
(1, 3, '3915-03-23', 1, 32.5, 1),
(1, 4, '1915-03-23', 1, 32.5, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `linea_comanda`
--

CREATE TABLE IF NOT EXISTS `linea_comanda` (
  `Id_Comanda` int(10) NOT NULL,
  `Id_Producto` int(10) NOT NULL,
  `cantidad` int(3) NOT NULL,
  `Id_Bar` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `oferta`
--

CREATE TABLE IF NOT EXISTS `oferta` (
  `Id_Oferta` int(10) NOT NULL,
  `Id_Bar` int(11) NOT NULL,
  `Precio` double NOT NULL,
  `Descripcion` varchar(500) NOT NULL,
  `Foto` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE IF NOT EXISTS `producto` (
  `Id_Producto` int(10) NOT NULL,
  `Nombre` text NOT NULL,
  `Categoria` varchar(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`Id_Producto`, `Nombre`, `Categoria`) VALUES
(1, 'Huevos', 'A'),
(2, 'CocaCola Lata', 'B'),
(3, 'CocaCola 1L', 'B');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `bar`
--
ALTER TABLE `bar`
 ADD PRIMARY KEY (`Id_Bar`);

--
-- Indices de la tabla `camarero`
--
ALTER TABLE `camarero`
 ADD PRIMARY KEY (`Id_Camarero`);

--
-- Indices de la tabla `carta`
--
ALTER TABLE `carta`
 ADD PRIMARY KEY (`Id_Producto`,`Id_Bar`);

--
-- Indices de la tabla `comanda`
--
ALTER TABLE `comanda`
 ADD PRIMARY KEY (`Id_Comanda`);

--
-- Indices de la tabla `linea_comanda`
--
ALTER TABLE `linea_comanda`
 ADD PRIMARY KEY (`Id_Comanda`,`Id_Producto`,`Id_Bar`);

--
-- Indices de la tabla `oferta`
--
ALTER TABLE `oferta`
 ADD PRIMARY KEY (`Id_Oferta`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
 ADD PRIMARY KEY (`Id_Producto`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
