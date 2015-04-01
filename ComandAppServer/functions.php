<?php

//ConexiÃ³n a la BD
function getConnection() {
    $mysql_host = "mysql4.000webhost.com";
    $mysql_database = "a2210078_comand";
    $mysql_user = "a2210078_comand";
    $mysql_password = "ComandApp7";
    $dbStr1 = 'mysql:host=' . $mysql_host . ';dbname=' . $mysql_database . ';charset=utf8';
    $dbParams = array(PDO::ATTR_EMULATE_PREPARES => false, PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION);
    return new PDO($dbStr1, $mysql_user, $mysql_password, $dbParams);
}

function sendResponse($response) {

    $xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" .
            "<root xmlns=\"comandappRESPONSE.xsd\" " .
            "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " .
            "xsi:schemaLocation=\"xml/comandappRESPONSE.xsd\">";


    try {
        $serverMain = getServerMain();
        $db = getConnection();

        $actualizados = array();
        $eliminados = array();

        if ((!(isset($response))) || (strlen($response) == 0)) {
            //Se envÃ­an todos los datos.
            $mainCliente = array();
        } else {
            //Se valida el Main contra el XSD
            //Si da error serÃ¡ igual que en el caso anterior (se envÃ­an todos los datos).
            $mainCliente = parseXMLMAIN(utf8_decode(urldecode($response)));
        }

        foreach ($serverMain as $id_BarServer => $versionServer) {            
            if (array_key_exists($id_BarServer, $mainCliente)) {
                //El bar existe en el cliente. Se comparan versiones.
                $versionCliente = $mainCliente[$id_BarServer];
                $cmpInfo = strcmp($versionServer["vLocal"], $versionCliente["vLocal"]);
                $cmpCarta = strcmp($versionServer["vCarta"], $versionCliente["vCarta"]);
                $cmpOfertas = strcmp($versionServer["vOfertas"], $versionCliente["vOfertas"]);

                if ($cmpInfo == 0 && $cmpCarta == 0 && $cmpOfertas == 0) {
                    array_push($actualizados, $id_BarServer);
                } else {
                    if ($cmpInfo > 0) {
                        //Hay que actualizar la Info del bar.
                        //Aprovechamos para meter la carta y las ofertas tambiÃ©n (si hay que actualizarlas) en el objeto bar.
                        $xml .= addInfoLocal($id_BarServer, $db);
                        if ($cmpCarta > 0) {
                            $xml .= addCarta($id_BarServer, $db);
                        } else {
                            $xml .= "<carta xmlns=\"\" id_Bar=\"" . $id_BarServer . "\" version=\"0\"></carta>";
                        }
                        if ($cmpOfertas > 0) {
                            $xml .= addOfertas($id_BarServer, $db);
                        } else {
                            $xml .= "<ofertas xmlns=\"\" id_Bar=\"" . $id_BarServer . "\" version=\"0\"></ofertas>";
                        }
                        $xml .= "</bar>";
                    } else {
                        //No hay que actualizar la info del bar.
                        //Si hay que actualizar carta u ofertas se mandarÃ¡n por separado (fuera de un objeto bar).
                        if ($cmpCarta > 0) {
                            $xml .= addCarta($id_BarServer, $db);
                        }
                        if ($cmpOfertas > 0) {
                            $xml .= addOfertas($id_BarServer, $db);
                        }
                    }
                }
            } else {
                //No existe en el cliente. Se envÃ­a todo el bar.
                $xml .= addInfoLocal($id_BarServer, $db);
                $xml .= addCarta($id_BarServer, $db);
                $xml .= addOfertas($id_BarServer, $db);
                $xml .= "</bar>";
            }
        }

        $xml .= "<actualizados xmlns=\"\">";
        foreach ($actualizados as $i) {
            $xml .= $i . ",";
        }
        $xml .= "</actualizados>";
        $xml .= "<eliminados xmlns=\"\">";
        foreach ($eliminados as $i) {
            $xml .= $i . ",";
        }
        $xml .= "</eliminados>";
    } catch (PDOException $ex) {
        $xml .= "error: " . $ex->getMessage();
    }

    $xml .= "</root>";
    echo $xml;
}

//ValidaciÃ³n de XML y construcciÃ³n del Ã¡rbol DOM para mains recibidos
function parseXMLMAIN($xml) {
    $doc = new DOMDocument();
    $doc->loadXML($xml, LIBXML_NOBLANKS);
    if (!$doc->schemaValidate("xml/comandappMAIN.xsd")) {
        return null;
    }

    $mainCliente = array();
    $bares = $doc->getElementsByTagName('main');
    foreach ($bares as $bar) {
        $mainCliente["" . $bar->getAttribute('id_Bar')] = array(
            "vLocal" => $bar->item(0)->item(0)->nodeValue,
            "vCarta" => $bar->item(1)->item(0)->nodeValue,
            "vOfertas" => $bar->item(2)->item(0)->nodeValue
        );
    }
    return $mainCliente;
}

function getServerMain() {
    $db = getConnection();
    $stmt = $db->prepare("SELECT Id_Bar AS ID,"
            . " VersionInfoBar AS VIB,"
            . " VersionCarta AS VC,"
            . " VersionOfertas AS VO "
            . "FROM bar");

    $stmt->execute();

    $main = array();
    if ($stmt->rowCount() > 0) {
        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
            $main["" . $row["ID"]] = array(
                "vLocal" => "" . $row["VersionInfoBar"],
                "vCarta" => "" . $row["VersionCarta"],
                "vOfertas" => "" . $row["VersionOfertas"]);
        }
    }
    return $main;
}

function addInfoLocal($id, $db) {
    $stmt = $db->prepare("SELECT Nombre,Direccion,Telefono,Longitud,Latitud,Provincia,Municipio,Foto,Correo,VersionInfoBar FROM bar WHERE Id_Bar=?");
    $stmt->bindValue(1, $id, PDO::PARAM_INT);
    $stmt->execute();

    $xml = "";

    if ($stmt->rowCount() > 0) {
        $row = $stmt->fetch(PDO::FETCH_ASSOC);
        $xml .= "<bar xmlns=\"comandappLOCAL.xsd\" id_Bar=\"" . $id . "\" version=\"" . $row['VersionInfoBar'] . "\">";
        $xml .= "<nombre xmlns=\"\">" . $row['Nombre'] . "</nombre>";
        $xml .= "<direccion xmlns=\"\">" . $row['Direccion'] . "</direccion>";
        $xml .= "<telefono xmlns=\"\">" . $row['Telefono'] . "</telefono>";
        $xml .= "<correo xmlns=\"\">" . $row['Correo'] . "</correo>";
        $xml .= "<latitud xmlns=\"\">" . $row['Latitud'] . "</latitud>";
        $xml .= "<longitud xmlns=\"\">" . $row['Longitud'] . "</longitud>";
        $xml .= "<provincia xmlns=\"\">" . $row['Provincia'] . "</provincia>";
        $xml .= "<municipio xmlns=\"\">" . $row['Municipio'] . "</municipio>";
        $xml .= "<foto xmlns=\"\">" . $row['Foto'] . "</foto>";
    }

    return $xml;
}

function addCarta($id, $db) {
    $stmt = $db->prepare("SELECT producto.Id_Producto AS idProducto," .
            "producto.Nombre AS nomProducto," .
            "carta.Descripcion AS desProducto," .
            "producto.Categoria AS catProducto," .
            "carta.Precio AS precio," .
            "carta.Foto AS foto," .
            "bar.VersionCarta AS vCarta " .
            "FROM carta INNER JOIN producto ON carta.Id_Producto = producto.Id_Producto " .
            "INNER JOIN bar ON carta.Id_Bar = bar.Id_Bar " .
            "WHERE bar.Id_Bar=?");
    $stmt->bindValue(1, $id, PDO::PARAM_INT);
    $stmt->execute();

    $xml = "";

    if ($stmt->rowCount() > 0) {
        $i = 0;
        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
            if ($i == 0) {
                $xml .= "<carta xmlns=\"comandappCARTA.xsd\" id_Bar=\"" . $id . "\" version=\"" . $row['vCarta'] . "\">";
                $i++;
            }
            $xml .= "<linea_carta xmlns=\"comandappCARTA.xsd\">";
            $xml .= "<idProducto xmlns=\"\">" . $row['idProducto'] . "</idProducto>";
            $xml .= "<nomProducto xmlns=\"\">" . $row['nomProducto'] . "</nomProducto>";
            $xml .= "<desProducto xmlns=\"\">" . $row['desProducto'] . "</desProducto>";
            $xml .= "<catProducto xmlns=\"\">" . $row['catProducto'] . "</catProducto>";
            $xml .= "<precio xmlns=\"\">" . $row['precio'] . "</precio>";
            $xml .= "<foto xmlns=\"\">" . $row['foto'] . "</foto>";
            $xml .= "</linea_carta>";
        }
        $xml .= "</carta>";
    }

    return $xml;
}

function addOfertas($id, $db) {
    $stmt = $db->prepare("SELECT oferta.Id_Oferta AS idOferta,"
            . "oferta.Precio AS Precio,"
            . "oferta.Descripcion AS Descripcion,"
            . "oferta.Productos AS Productos,"
            . "oferta.Foto AS Foto,"
            . "bar.VersionOfertas AS vOfertas "
            . "FROM oferta INNER JOIN bar ON oferta.Id_Bar=bar.Id_Bar "
            . "WHERE bar.Id_Bar=?");
    $stmt->bindValue(1, $id, PDO::PARAM_INT);
    $stmt->execute();

    $xml = "";

    if ($stmt->rowCount() > 0) {
        $i = 0;
        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
            if ($i == 0) {
                $xml .= "<ofertas xmlns=\"comandappOFERTAS.xsd\" "
                        . "id_Bar=\"" . $id . "\" "
                        . "version=\"" . $row['vOfertas'] . "\">";
                $i++;
            }
            $xml .= "<linea_oferta xmlns=\"comandappOFERTAS.xsd\" id_Oferta=\"" . $row['idOferta'] . "\">";
            $xml .= "<descripcion xmlns=\"\">" . $row['Descripcion'] . "</descripcion>";
            $xml .= "<productos xmlns=\"\">" . $row['Productos'] . "</productos>";
            $xml .= "<precio xmlns=\"\">" . $row['Precio'] . "</precio>";
            $xml .= "<foto xmlns=\"\">" . $row['Foto'] . "</foto>";
            $xml .= "</linea_oferta>";
        }
        $xml .= "</ofertas>";
    }

    return $xml;
}
