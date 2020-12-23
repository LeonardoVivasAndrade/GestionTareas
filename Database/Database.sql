-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-12-2020 a las 19:16:11
-- Versión del servidor: 10.4.17-MariaDB
-- Versión de PHP: 8.0.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `nube`
--
CREATE DATABASE IF NOT EXISTS `nube` DEFAULT CHARACTER SET utf8mb4;
USE `nube`;
-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comentario`
--

CREATE TABLE `comentario` (
  `id_comentario` int(11) NOT NULL,
  `descripcion` varchar(200) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `id_documento` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `documento`
--

CREATE TABLE `documento` (
  `id_documento` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `id_materia` int(11) NOT NULL,
  `nombre` varchar(80) NOT NULL,
  `vistas` int(11) NOT NULL,
  `fecha_reg` date NOT NULL,
  `score` int(11) NOT NULL,
  `url` varchar(400) NOT NULL,
  `iconUrl` varchar(400) NOT NULL,
  `embedUrl` varchar(400) NOT NULL,
  `sizeBytes` varchar(200) NOT NULL,
  `idDrive` varchar(300) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `documento`
--

INSERT INTO `documento` (`id_documento`, `id_usuario`, `id_materia`, `nombre`, `vistas`, `fecha_reg`, `score`, `url`, `iconUrl`, `embedUrl`, `sizeBytes`, `idDrive`) VALUES
(1, 1, 6, 'Segundo previo SO', 0, '2020-12-22', 0, 'https://docs.google.com/document/d/1aPd9GLobCpz0_urUA3rybKBrIxcSW8oZ/view', '', '', '', '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `favorito`
--

CREATE TABLE `favorito` (
  `id_usuario` int(11) NOT NULL,
  `id_documento` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `materia`
--

CREATE TABLE `materia` (
  `id_materia` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `materia`
--

INSERT INTO `materia` (`id_materia`, `nombre`) VALUES
(6, 'CALCULO DIFERENCIAL'),
(7, 'QUIMICA'),
(8, 'EXPRESION GRAFICA'),
(9, 'INTROD. A LA INGENIERIA CIVIL'),
(10, 'INTRODUCCION A LA VIDA UNIVERSITARIA'),
(11, 'COMUNICACION I'),
(12, 'PROGRAMACION DE COMPUTADORES'),
(13, 'CALCULO INTEGRAL'),
(14, 'FISICA MECANICA'),
(15, 'ALGEBRA LINEAL'),
(16, 'GEOMETRIA DESCRIPTIVA'),
(17, 'COMUNICACION II'),
(18, 'INFORMATICA APLICADA'),
(19, 'CALCULO VECTORIAL'),
(20, 'FISICA ELECTROMAGNETICA'),
(21, 'TOPOGRAFIA'),
(22, 'GEOLOGIA'),
(23, 'CONSTITUCION Y CIVISMO'),
(24, 'ECUACIONES DIFERENCIALES'),
(25, 'ONDAS Y PARTICULAS'),
(26, 'PROBABILIDAD Y ESTADISTICA'),
(27, 'FOTOGRAMETRIA INTERPRETACION'),
(28, 'ESTATICA'),
(30, 'ANALISIS NUMERICO'),
(31, 'MATERIALES'),
(32, 'MECANICA DE SOLIDO'),
(33, 'MECANICA DE FLUIDOS'),
(34, 'TRANSITO'),
(35, 'METODOLOGIA DE LA INVESTIGACION'),
(36, 'ANALISIS ESTRUCTURAL I'),
(37, 'HIDRAULICA'),
(38, 'CONSTRUCCION I'),
(39, 'GEOTECNIA I'),
(40, 'TRANSPORTE URBANO'),
(41, 'ORG. Y ADMINISTRACION EMPRESAS'),
(42, 'PROYECTO INTEGRADOR I'),
(43, 'ANALISIS ESTRUCTURAL II'),
(44, 'HIDROLOGIA'),
(45, 'CONSTRUCCION II'),
(46, 'GEOTECNIA II'),
(47, 'VIAS TERRESTRES'),
(48, 'ECONOMIA Y FINANZAS PARA INGENIERIA'),
(49, 'DISEÑO ESTRUCTURAL I'),
(50, 'SISTEMAS DE ACUEDUCTOS'),
(51, 'ELECTIVA PROFUNDIZACION I'),
(52, 'GEOTECNIA III'),
(53, 'ETICA'),
(54, 'PAVIMENTOS'),
(55, 'COSTOS, PRESUPUESTO Y PROGRAMACION'),
(56, 'DISEÑO ESTRUCTURAL II'),
(57, 'PROCESOS SANITARIOS'),
(58, 'ELECTIVA PROFUNDIZACION II (Electiva)'),
(59, 'FORMULACION Y EVALUACION DE PROYECTOS'),
(60, 'ELECTIVA HUMANIDADES I'),
(61, 'PROYECTO INTEGRADOR II'),
(63, 'PROYECTO DE GRADO'),
(64, 'PRACTICA PROFESIONAL'),
(65, 'ELECTIVA PROFUNDIZACION III'),
(66, 'ELECTIVA HUMANIDADES II'),
(67, 'MATEMATICAS DISCRETAS'),
(68, 'FUNDAMENTOS DE PROGRAMACION'),
(69, 'INTRO A INGENIERIA DE SISTEMAS'),
(70, 'PROGR ORIENTADA A OBJETOS  I'),
(71, 'SEMINARIO INTEGRADOR I'),
(72, 'ESTRUCTURA DE DATOS'),
(73, 'PROGR ORIENTADA A OBJETOS  II'),
(74, 'SEMINARIO DE INVESTIGACION I'),
(75, 'ANALISIS DE ALGORITMOS'),
(76, 'TEORIA DE LA COMPUTACION'),
(77, 'INVESTIGACIÓN DE OPERACIONES'),
(78, 'ELECTRONICA'),
(79, 'ARQUITECTURA DE COMPUTADORES'),
(80, 'SEMINARIO DE INVESTIGACIÓN II'),
(81, 'SISTEMAS OPERATIVOS'),
(82, 'BASES DE DATOS'),
(83, 'PROGRAMACION WEB'),
(84, 'PLANEACION ESTRATEGICA DE SI'),
(85, 'SEMINARIO INTEGRADOR  II'),
(86, 'TEORÍA GENERAL DE LAS COMUNICACIONES'),
(87, 'ANÁLISIS Y DISEÑO DE SISTEMAS'),
(88, 'SEMINARIO DE INVESTIGACION III'),
(89, 'ETICA PROFESIONAL'),
(90, 'ADMINISTACION DE PROYECTOS INFORMATICOS'),
(91, 'REDES DE COMPUTADORES'),
(92, 'INGENIERÍA DE SOFTWARE'),
(93, 'SEMINARIO INTEGRADOR III'),
(94, 'GESTION DE TIC'),
(95, 'ARQUITECTURA DE SOFTWARE'),
(96, 'INTRODUCCION A LA INGENIERIA ELECTRONICA'),
(97, 'TECNICAS DE COMUNICACION ORAL'),
(98, 'TECNICAS DE COMUNICACION ESCRITA'),
(99, 'VARIABLE COMPLEJA'),
(100, 'ANALISIS DE CIRCUITOS EN C.D.'),
(101, 'TEORIA ELECTROMAGNETICA'),
(102, 'ANTROPOLOGIA SOCIAL Y CULTURAL'),
(103, 'ANALISIS DE CIRCUITOS EN C.A.'),
(104, 'MEDICION ELECTRONICA'),
(105, 'ORGANIZACION Y ADMINISTRACION DE EMPRESAS'),
(106, 'TEORIA DE SEÑALES Y SISTEMAS'),
(107, 'ELECTRONICA II'),
(108, 'DISEÑO DIGITAL'),
(109, 'MEDIOS DE TRANSMISION'),
(110, 'ELECTRONICA III'),
(111, 'INSTRUMENTACION ELECTRONICA'),
(112, 'SISTEMAS DE COMUNICACIONES I'),
(113, 'CONTROL DIGITAL'),
(114, 'ELECTRONICA DE POTENCIA'),
(115, 'SISTEMAS DE COMUNICACIONES II');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `programa`
--

CREATE TABLE `programa` (
  `id_programa` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `semestre` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `programa`
--

INSERT INTO `programa` (`id_programa`, `nombre`, `semestre`) VALUES
(1, 'Ingeniería Civil', 10),
(2, 'Ingeniería de Sistemas', 10),
(3, 'Ingeniería Electrónica', 10),
(4, 'Ingeniería Industrial', 10),
(5, 'Ingeniería de Minas', 10),
(6, 'Ingeniería Mecánica', 10),
(7, 'Ingeniería Electromecánica', 10);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `programa_materia`
--

CREATE TABLE `programa_materia` (
  `id_materia` int(11) NOT NULL,
  `id_programa` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `programa_materia`
--

INSERT INTO `programa_materia` (`id_materia`, `id_programa`) VALUES
(6, 1),
(6, 2),
(6, 3),
(6, 7),
(7, 1),
(7, 7),
(8, 1),
(9, 1),
(10, 1),
(10, 2),
(10, 3),
(10, 7),
(11, 1),
(11, 2),
(12, 1),
(13, 1),
(13, 2),
(13, 3),
(13, 7),
(14, 1),
(14, 2),
(14, 3),
(14, 7),
(15, 1),
(15, 2),
(15, 3),
(15, 7),
(16, 1),
(16, 3),
(17, 1),
(17, 2),
(18, 1),
(19, 1),
(19, 2),
(19, 3),
(19, 7),
(20, 1),
(20, 2),
(20, 3),
(20, 7),
(21, 1),
(22, 1),
(23, 1),
(23, 2),
(23, 3),
(24, 1),
(24, 2),
(24, 3),
(24, 7),
(25, 1),
(25, 2),
(25, 3),
(26, 1),
(26, 2),
(26, 3),
(26, 7),
(27, 1),
(28, 1),
(30, 1),
(30, 2),
(30, 3),
(30, 7),
(31, 1),
(32, 1),
(33, 1),
(34, 1),
(35, 1),
(35, 3),
(35, 7),
(36, 1),
(37, 1),
(38, 1),
(39, 1),
(40, 1),
(41, 1),
(42, 1),
(42, 7),
(43, 1),
(44, 1),
(45, 1),
(46, 1),
(47, 1),
(48, 1),
(48, 3),
(48, 7),
(49, 1),
(50, 1),
(51, 1),
(52, 1),
(53, 1),
(54, 1),
(55, 1),
(56, 1),
(57, 1),
(58, 1),
(59, 1),
(59, 2),
(59, 3),
(60, 1),
(61, 1),
(61, 7),
(63, 1),
(64, 1),
(65, 1),
(66, 1),
(67, 2),
(68, 2),
(68, 3),
(69, 2),
(70, 2),
(70, 3),
(71, 2),
(75, 2),
(76, 2),
(78, 2),
(78, 3),
(78, 7),
(79, 2),
(79, 3),
(80, 2),
(81, 2),
(82, 2),
(83, 2),
(84, 2),
(85, 2),
(85, 3),
(86, 2),
(87, 2),
(88, 2),
(89, 2),
(89, 3),
(89, 7),
(90, 2),
(91, 2),
(94, 2),
(95, 2),
(96, 3),
(97, 3),
(98, 3),
(99, 3),
(100, 3),
(100, 7),
(101, 3),
(102, 3),
(103, 3),
(103, 7),
(104, 3),
(104, 7),
(105, 3),
(105, 7),
(106, 3),
(106, 7),
(107, 3),
(107, 7),
(108, 3),
(108, 7),
(109, 3),
(110, 3),
(111, 3),
(112, 3),
(113, 3),
(114, 3),
(115, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `registro`
--

CREATE TABLE `registro` (
  `id_registro` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `fecha_hora` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `correo` varchar(50) NOT NULL,
  `score` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `nombre`, `correo`, `score`) VALUES
(1, 'Gabriela Cárdenas ', 'anagabrielacaom@ufps.edu.co', 0);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `comentario`
--
ALTER TABLE `comentario`
  ADD PRIMARY KEY (`id_comentario`),
  ADD KEY `Fk_comentario_usuario` (`id_usuario`),
  ADD KEY `Fk_comentario_documento` (`id_documento`);

--
-- Indices de la tabla `documento`
--
ALTER TABLE `documento`
  ADD PRIMARY KEY (`id_documento`),
  ADD UNIQUE KEY `idDrive` (`idDrive`),
  ADD KEY `Fk_documento_materia` (`id_materia`),
  ADD KEY `id_usuario` (`id_usuario`) USING BTREE;

--
-- Indices de la tabla `favorito`
--
ALTER TABLE `favorito`
  ADD PRIMARY KEY (`id_documento`,`id_usuario`) USING BTREE,
  ADD KEY `id_usuario` (`id_usuario`),
  ADD KEY `id_documento` (`id_documento`);

--
-- Indices de la tabla `materia`
--
ALTER TABLE `materia`
  ADD PRIMARY KEY (`id_materia`);

--
-- Indices de la tabla `programa`
--
ALTER TABLE `programa`
  ADD PRIMARY KEY (`id_programa`);

--
-- Indices de la tabla `programa_materia`
--
ALTER TABLE `programa_materia`
  ADD PRIMARY KEY (`id_materia`,`id_programa`) USING BTREE,
  ADD KEY `id_programa` (`id_programa`),
  ADD KEY `id_materia` (`id_materia`) USING BTREE;

--
-- Indices de la tabla `registro`
--
ALTER TABLE `registro`
  ADD PRIMARY KEY (`id_registro`),
  ADD KEY `id_usuario` (`id_usuario`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `comentario`
--
ALTER TABLE `comentario`
  MODIFY `id_comentario` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `documento`
--
ALTER TABLE `documento`
  MODIFY `id_documento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `materia`
--
ALTER TABLE `materia`
  MODIFY `id_materia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=116;

--
-- AUTO_INCREMENT de la tabla `programa`
--
ALTER TABLE `programa`
  MODIFY `id_programa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `registro`
--
ALTER TABLE `registro`
  MODIFY `id_registro` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `comentario`
--
ALTER TABLE `comentario`
  ADD CONSTRAINT `Fk_comentario_documento` FOREIGN KEY (`id_documento`) REFERENCES `documento` (`id_documento`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Fk_comentario_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `documento`
--
ALTER TABLE `documento`
  ADD CONSTRAINT `Fk_documento_materia` FOREIGN KEY (`id_materia`) REFERENCES `materia` (`id_materia`),
  ADD CONSTRAINT `Fk_documento_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `favorito`
--
ALTER TABLE `favorito`
  ADD CONSTRAINT `Fk_favorito_documento` FOREIGN KEY (`id_documento`) REFERENCES `documento` (`id_documento`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Fk_favorito_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `programa_materia`
--
ALTER TABLE `programa_materia`
  ADD CONSTRAINT `Fk_programa_materia_mat` FOREIGN KEY (`id_materia`) REFERENCES `materia` (`id_materia`) ON UPDATE CASCADE,
  ADD CONSTRAINT `Fk_programa_materia_pro` FOREIGN KEY (`id_programa`) REFERENCES `programa` (`id_programa`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `registro`
--
ALTER TABLE `registro`
  ADD CONSTRAINT `Fk_registro_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
