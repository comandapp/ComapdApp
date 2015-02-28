<?php
	//$affected_rows = $db->exec("UPDATE table SET field='value'");
function getConnection() {
	$dbStr1 = 'mysql:host=localhost;dbname=comandapp;charset=utf8';
	$dbStr2 = 'comandapp';
	$dbStr3 = 'comandapp';
	$dbParams = array(PDO::ATTR_EMULATE_PREPARES => false,PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION);
	return new PDO($dbStr1,$dbStr2,$dbStr3,$dbParams);
}

function sendMain($id) {
	$db = getConnection();
	$stmt = $db->prepare("SELECT VersionInfoBar AS VB, VersionOfertas AS VO FROM bar WHERE Id_Bar=?");
	$stmt->bindValue(1, $id, PDO::PARAM_INT);
	$stmt->execute();

	$xml = "<?xml version=\"1.0\"?>";
	if($stmt->rowCount() > 0) {
		$row = $stmt->fetch(PDO::FETCH_ASSOC);
		$xml .= "<main>";
		$xml .= "<local>".$row['VB']."</local>";
		$xml .= "<ofertas>".$row['VO']."</ofertas>";
		$xml .= "</main>";
	} else {
		$xml .= "<error>404</error>";
	}
	
	$stmt = null;
	$dbh = null;
	return $xml;
}

function sendLocal($id) {
	$db = getConnection();
	$stmt = $db->prepare("SELECT * FROM bar WHERE Id_Bar=?");
	$stmt->bindValue(1, $id, PDO::PARAM_INT);
	$stmt->execute();
	
	$xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
	if($stmt->rowCount() > 0) {
		$row = $stmt->fetch(PDO::FETCH_ASSOC);
		$xml .= "<local>";
                $xml .= "<nombre>".$row['Nombre']."</nombre>";
                $xml .= "<direccion>".$row['Direccion']."</direccion>";
                $xml .= "<telefono>".$row['Telefono']."</telefono>";
                $xml .= "<correo>".$row['Correo']."</correo>";
                $xml .= "<latitud>".$row['Latitud']."</latitud>";
                $xml .= "<longitud>".$row['Longitud']."</longitud>";
                $xml .= "<provincia>".$row['Provincia']."</provincia>";
                $xml .= "<municipio>".$row['Municipio']."</municipio>";
                $xml .= "<vBar>".$row['VersionInfoBar']."</vBar>";
                $xml .= "<vOfertas>".$row['VersionOfertas']."</vOfertas>";
                
                
               //Seleccionar también los productos y su descripción.
               $stmt2 = $db->prepare("SELECT producto.Id_Producto AS idProducto,".
					"producto.Nombre AS nomProducto,".
					"carta.Descripcion AS desProducto,".
					"producto.Categoria AS catProducto,".
					"carta.Precio AS precio, carta.Foto AS foto FROM carta INNER JOIN producto ON carta.Id_Producto = producto.Id_Producto WHERE carta.Id_Bar=?");
               $stmt2->bindValue(1, $id, PDO::PARAM_INT);
               $stmt2->execute();
               
               if($stmt2->rowCount() > 0) {
                    $xml .= "<carta>";
                    while($row = $stmt2->fetch(PDO::FETCH_ASSOC)) {
						$xml .= "<entrada>";
						$xml .= "<idProducto>".$row['idProducto']."</idProducto>";
						$xml .= "<nomProducto>".$row['nomProducto']."</nomProducto>";
						$xml .= "<desProducto>".$row['desProducto']."</desProducto>";
						$xml .= "<catProducto>".$row['catProducto']."</catProducto>";
						$xml .= "<precio>".$row['precio']."</precio>";
						$xml .= "<foto>".$row['foto']."</foto>";
						$xml .= "</entrada>";
                    }
                    $xml .= "</carta>";
               }
			   
               $xml .= "</local>";
               $stmt2 = null;
	} else {
		$xml .= "<error>404</error>";
	}
	
	$stmt = null;
	$dbh = null;
	return $xml;
}

function sendOfertas($id) {
	$db = getConnection();
	$stmt = $db->prepare("SELECT * FROM ofertas WHERE Id_Bar=?");
	$stmt->bindValue(1, $id, PDO::PARAM_INT);
	$stmt->execute();
	
	$xml = "<?xml version=\"1.0\"?>";
	
	if($stmt->rowCount() > 0) {
		$i = 0;
		while($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
			if ($i == 0) {
				$xml .= "<ofertas>";
				$i++;
			}
			$xml .= "<oferta>";
			$xml .= "<idOferta>".$row['Id_Oferta']."</idOferta>";
			$xml .= "<precio>".$row['Precio']."</precio>";
			$xml .= "<descripcion>".$row['Descripcion']."</descripcion>";
			$xml .= "</oferta>";
		}
		$xml .= "</ofertas>";
	} else {
		$xml .= "<error>404</error>";
	}
	
	$stmt = null;
	$dbh = null;
	return $xml;
}