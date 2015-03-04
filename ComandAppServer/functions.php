<?php

//$affected_rows = $db->exec("UPDATE table SET field='value'");
function getConnection() {
    $dbStr1 = 'mysql:host=localhost;dbname=comandapp;charset=utf8';
    $dbStr2 = 'comandapp';
    $dbStr3 = 'comandapp';
    $dbParams = array(PDO::ATTR_EMULATE_PREPARES => false, PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION);
    return new PDO($dbStr1, $dbStr2, $dbStr3, $dbParams);
}

function sendMain($idArray) {
    $db = getConnection();
    $stmt = $db->prepare("SELECT VersionInfoBar AS VIB, VersionCarta AS VC, VersionOfertas AS VO FROM bar WHERE Id_Bar=?");

    $xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
    $xml .= "<root xmlns=\"comandappCOMUNES.xsd\""
            . " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
            . " xsi:schemaLocation=\"comandappCOMUNES.xsd comandappMAIN.xsd\">";

    foreach ($idArray as $id) {
        $stmt->bindValue(1, $id, PDO::PARAM_INT);
        $stmt->execute();

        if ($stmt->rowCount() > 0) {
            $row = $stmt->fetch(PDO::FETCH_ASSOC);
            $xml .= "<main xmlns=\"comandappMAIN.xsd\" id=\"" . $id . "\">";
            $xml .= "<local xmlns=\"\">" . $row['VIB'] . "</local>";
            $xml .= "<carta xmlns=\"\">" . $row['VC'] . "</carta>";
            $xml .= "<ofertas xmlns=\"\">" . $row['VO'] . "</ofertas>";
            $xml .= "</main>";
        } else {
            $xml .= "<error xmlns=\"comandappCOMUNES.xsd\" id=\"".$id."\">404</error>";
        }
    }

    $xml .= "</root>";

    $stmt = null;
    $dbh = null;
    return $xml;
}

function sendInfoLocal($idArray) {
    
    $db = getConnection();
    $stmt = $db->prepare("SELECT Nombre,Direccion,Telefono,Longitud,Latitud,Provincia,Municipio,Correo,VersionInfoBar FROM bar WHERE Id_Bar=?");

    $xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
    $xml .= "<root xmlns=\"comandappCOMUNES.xsd\""
            . " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
            . " xsi:schemaLocation=\"comandappCOMUNES.xsd comandappLOCAL.xsd\">";

    foreach ($idArray as $id) {
        $stmt->bindValue(1, $id, PDO::PARAM_INT);
        $stmt->execute();

        if ($stmt->rowCount() > 0) {
            $row = $stmt->fetch(PDO::FETCH_ASSOC);
            $xml .= "<local xmlns=\"comandappLOCAL.xsd\" id=\"".$id."\" version=\"".$row['VersionInfoBar']."\">";
            $xml .= "<nombre xmlns=\"\">" . $row['Nombre'] . "</nombre>";
            $xml .= "<direccion xmlns=\"\">" . $row['Direccion'] . "</direccion>";
            $xml .= "<telefono xmlns=\"\">" . $row['Telefono'] . "</telefono>";
            $xml .= "<correo xmlns=\"\">" . $row['Correo'] . "</correo>";
            $xml .= "<latitud xmlns=\"\">" . $row['Latitud'] . "</latitud>";
            $xml .= "<longitud xmlns=\"\">" . $row['Longitud'] . "</longitud>";
            $xml .= "<provincia xmlns=\"\">" . $row['Provincia'] . "</provincia>";
            $xml .= "<municipio xmlns=\"\">" . $row['Municipio'] . "</municipio>";
            $xml .= "</local>";
        } else {
            $xml .= "<error xmlns=\"comandappCOMUNES.xsd\" id=\"".$id."\">404</error>";
        }
    }
    
    $xml .= "</root>";
    
    $stmt = null;
    $dbh = null;
    return $xml;
}

function sanitazeIdString($idString) {
    return split(",", ereg_replace("[^0-9,]", "", $idString));
}

function sendCarta($idArray) {
    $db = getConnection();
    $sql = "SELECT producto.Id_Producto AS idProducto," .
            "producto.Nombre AS nomProducto," .
            "carta.Descripcion AS desProducto," .
            "producto.Categoria AS catProducto," .
            "carta.Precio AS precio," .
            "carta.Foto AS foto," .
            "bar.VersionCarta AS vCarta " .
            "FROM carta INNER JOIN producto ON carta.Id_Producto = producto.Id_Producto " .
            "INNER JOIN bar ON carta.Id_Bar = bar.Id_Bar " .
            "WHERE bar.Id_Bar=?";
    
    $xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
    $xml .= "<root xmlns=\"comandappCOMUNES.xsd\""
            . " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
            . " xsi:schemaLocation=\"comandappCOMUNES.xsd comandappCARTA.xsd\">";
    
    foreach($idArray as $id) {
        $stmt = $db->prepare($sql);
        $stmt->bindValue(1, $id, PDO::PARAM_INT);
        $stmt->execute();

        if ($stmt->rowCount() > 0) {
            $i = 0;
            while($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
                if($i == 0) {
                    $xml .= "<carta xmlns=\"comandappCARTA.xsd\" id=\"".$id."\" version=\"".$row['vCarta']."\">";
                }
                $xml .= "<entrada xmlns=\"comandappCARTA.xsd\">";
                $xml .= "<idProducto xmlns=\"\">" . $row['idProducto'] . "</idProducto>";
                $xml .= "<nomProducto xmlns=\"\">" . $row['nomProducto'] . "</nomProducto>";
                $xml .= "<desProducto xmlns=\"\">" . $row['desProducto'] . "</desProducto>";
                $xml .= "<catProducto xmlns=\"\">" . $row['catProducto'] . "</catProducto>";
                $xml .= "<precio xmlns=\"\">" . $row['precio'] . "</precio>";
                $xml .= "<foto xmlns=\"\">" . $row['foto'] . "</foto>";
                $xml .= "</entrada>";
                
                if($i == (($stmt->rowCount())-1)) {
                    $xml .= "</carta>";
                }
                $i++;
            }
        } else {
            $xml .= "<error xmlns=\"comandappCOMUNES.xsd\" id=\"".$id."\">404</error>";
        }
    }
    
    $xml .= "</root>";
    
    $stmt = null;
    $dbh = null;
    return $xml;
}

function sendOfertas($idArray) {
    $db = getConnection();
    $stmt = $db->prepare("SELECT oferta.Id_Oferta AS idOferta,"
            . "oferta.Precio AS Precio,"
            . "oferta.Descripcion AS Descripcion,"
            . "oferta.Foto AS Foto,"
            . "bar.VersionOfertas AS vOfertas "
            . "FROM oferta INNER JOIN bar ON oferta.Id_Bar=bar.Id_Bar "
            . "WHERE bar.Id_Bar=?");
    
    $xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
    $xml .= "<root xmlns=\"comandappCOMUNES.xsd\""
            . " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
            . " xsi:schemaLocation=\"comandappCOMUNES.xsd comandappOFERTAS.xsd\">";
    
    foreach($idArray as $id) {
        $stmt->bindValue(1, $id, PDO::PARAM_INT);
        $stmt->execute();

        if ($stmt->rowCount() > 0) {
            $i = 0;
            while($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
                if($i == 0) {
                    $xml .= "<listaofertas xmlns=\"comandappOFERTAS.xsd\" "
                            . "id=\"".$id."\" "
                            . "version=\"".$row['vOfertas']."\">";
                }
                $xml .= "<oferta xmlns=\"comandappOFERTAS.xsd\" id=\"".$row['idOferta']."\">";
                $xml .= "<descripcion xmlns=\"\">" . $row['Descripcion'] . "</descripcion>";
                $xml .= "<precio xmlns=\"\">" . $row['Precio'] . "</precio>";
                $xml .= "<foto xmlns=\"\">" . $row['Foto'] . "</foto>";
                $xml .= "</oferta>";
                if($i == (($stmt->rowCount())-1)) {
                    $xml .= "</listaofertas>";
                }
                $i++;
            }
        } else {
            $xml .= "<error xmlns=\"comandappCOMUNES.xsd\" id=\"".$id."\">404</error>";
        }

    }
        
    $xml .= "</root>";
    
    $stmt = null;
    $dbh = null;
    return $xml;
}
