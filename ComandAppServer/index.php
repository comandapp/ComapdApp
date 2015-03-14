<?php

/*
  PROTOCOLO DE COMUNICACIÓN
  HTTP mediante GET únicamente.

  1. Cada comunicación deberá tener al menos un parámetro "COM".
  Éste toma el valor de alguna de las siguientes opciones:

    MAIN - Devuelve el estado actual de la base de datos del servidor.
    No tiene parámetros.

    GLOC - Datos sobre el local. No de su carta ni de sus ofertas.
    Parámetros:
    ID - Identificador/Identificadores(seguidos de una coma) del local.
    Salida : Documento XML con los datos de local. Si hay más de uno se concatenan.

    GCAR - La carta del local con sus entradas y productos..
    Parámetros:
    ID - Identificador/Identificadores(seguidos de una coma) del local.
    Salida : Documento XML con los datos de carta. Si hay más de uno se concatenan.

    GOFE - Datos sobre ofertas de un local con sus productos.
    Parámetros:
    ID - Identificador/Identificadores(seguidos de una coma) del local.
    Salida : Documento XML con los datos de las ofertas actuales. Si hay más de uno se concatenan.
 */

if (isset($_GET["COM"]) && isset($_GET["ID"])) {
    require("functions.php");
    header('Content-type: application/xml');

    $com = substr($_GET["COM"], 0, 4);
    $idString = substr($_GET["ID"], 0, 100);
    $idArray = sanitazeIdString($idString);

    try {
        if (strcmp($com, "GLOC") == 0) {
            echo sendInfoLocal($idArray);
        } elseif (strcmp($com, "GCAR") == 0) {
            echo sendCarta($idArray);
        } elseif (strcmp($com, "GOFE") == 0) {
            echo sendOfertas($idArray);
        } else {
            exit;
        }
    } catch (PDOException $ex) {
        echo "error: " . $ex->getMessage();
    }
} else if(isset($_GET["COM"]) && !isset($_GET["ID"])) {
    require("functions.php");
    header('Content-type: application/xml');

    $com = substr($_GET["COM"], 0, 4);
    if (strcmp($com, "MAIN") == 0) echo sendMain();
} else exit;