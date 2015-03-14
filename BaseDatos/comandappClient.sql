SET FOREIGN_KEY_CHECKS=0;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

CREATE TABLE IF NOT EXISTS `bar` (
  `Id_Bar` int(10) NOT NULL,
  `Nombre` text CHARACTER SET utf8 NOT NULL,
  `Direccion` text CHARACTER SET utf8 NOT NULL,
  `Telefono` int(9) NOT NULL DEFAULT '0',
  `Longitud` double NOT NULL DEFAULT '0',
  `Latitud` double NOT NULL DEFAULT '0',
  `Provincia` text CHARACTER SET utf8 NOT NULL,
  `Municipio` text CHARACTER SET utf8 NOT NULL,
  `Foto` longtext CHARACTER SET utf8 NOT NULL,
  `Correo` text CHARACTER SET utf8 NOT NULL,
  `VersionInfoBar` int(11) NOT NULL,
  `VersionCarta` int(11) NOT NULL,
  `VersionOfertas` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `carta` (
  `Id_Producto` int(10) NOT NULL,
  `Id_Bar` int(10) NOT NULL,
  `Precio` double NOT NULL,
  `Descripcion` text CHARACTER SET utf8 NOT NULL,
  `Foto` longtext CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `oferta` (
  `Id_Oferta` int(10) NOT NULL,
  `Id_Bar` int(11) NOT NULL,
  `Precio` double NOT NULL,
  `Descripcion` text CHARACTER SET utf8 NOT NULL,
  `Productos` text CHARACTER SET utf8 NOT NULL,
  `Foto` longtext CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `producto` (
  `Id_Producto` int(10) NOT NULL,
  `Nombre` text CHARACTER SET utf8 NOT NULL,
  `Categoria` varchar(1) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


ALTER TABLE `bar`
 ADD PRIMARY KEY (`Id_Bar`);

ALTER TABLE `carta`
 ADD PRIMARY KEY (`Id_Producto`,`Id_Bar`);

ALTER TABLE `oferta`
 ADD PRIMARY KEY (`Id_Oferta`);

ALTER TABLE `producto`
 ADD PRIMARY KEY (`Id_Producto`);
SET FOREIGN_KEY_CHECKS=1;
COMMIT;