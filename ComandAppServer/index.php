<?php

/*
PROTOCOLO DE COMUNICACIÓN
HTTP mediante GET únicamente.

1. Cada comunicación deberá tener al menos un parámetro "COM".
   Éste toma el valor de alguna de las siguientes opciones:
   
MAIN - Información de las últimas versiones para actualizar la base de datos local.
	Parámetros:
		ID - Identificador/Identificadores(seguidos de una coma) del local.
	Salida : Documento XML con información sobre la versión de las cartas en el servidor.

GLOC - Datos sobre un local y su carta
	Parámetros:
		ID - Identificador/Identificadores(seguidos de una coma) del local.
	Salida : Documento XML con los datos de local y carta. Si hay más de uno se concatenan.

GOFE - Datos sobre ofertas de un local
	Parámetros:
		ID - Identificador/Identificadores(seguidos de una coma) del local.
	Salida : Documento XML con los datos de las ofertas actuales. Si hay más de uno se concatenan.
*/

if(isset($_GET["COM"]) && isset($_GET["ID"]) && intval($_GET["ID"])) {
	require("functions.php");	
	header('Content-type: application/xml');
	
	$com = substr($_GET["COM"],0,4);
	$id = substr($_GET["ID"],0,10);

	try {
		if(strcmp($com, "MAIN") == 0) {
			echo sendMain($id);
		} elseif (strcmp($com, "GLOC") == 0) {
			echo sendLocal($id);
		} elseif (strcmp($com, "GOFE") == 0) {
			echo sendOfertas($id);
		} else { exit; }
	} catch(PDOException $ex) {
		echo "error: ".$ex->getMessage();
	}
}