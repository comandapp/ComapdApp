<?php

/*
  PROTOCOLO DE COMUNICACIÓN
  HTTP mediante POST únicamente.

  1. Cada comunicación deberá tener al menos un parámetro "COM".
  Éste toma el valor de alguna de las siguientes opciones:

    GBAR - Todos los datos sobre un bar o bares.
    Parámetros:
    ID - Identificador/Identificadores(seguidos de una coma).
    Salida : Documento XML con los datos de local. Si hay más de uno se concatenan.

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

if (isset($_POST["COM"])) {
echo "1";
    include("functions.php");
echo "Cargado";
    $com = substr($_POST["COM"], 0, 4); 

    if(strcmp($com,"GLOC") == 0) {
        sendResponse($_POST["MAIN"]);
    }
}