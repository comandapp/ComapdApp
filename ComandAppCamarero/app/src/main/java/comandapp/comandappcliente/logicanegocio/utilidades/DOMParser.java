package comandapp.comandappcliente.logicanegocio.utilidades;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import comandapp.comandappcliente.logicanegocio.objetos.Bar;
import comandapp.comandappcliente.logicanegocio.objetos.LineaCarta;
import comandapp.comandappcliente.logicanegocio.objetos.Oferta;
import comandapp.comandappcliente.logicanegocio.objetos.Producto;
import comandapp.comandappcliente.persistencia.Persistencia;
import comandapp.comandappcliente.logicanegocio.utilidades.ImgCodec;
import comandapp.comandappcliente.persistencia.SQLHelper;

/**
 * Created by G62 on 17-Mar-15.
 * Métodos de parseo para las distintas partes de los árboles DOM provenientes de HTTPServerRequest.
 */
public class DOMParser {

    private Persistencia persistencia = Persistencia.getInstancia();

    //Devuelve los identificadores de los bares a eliminar a partir del elemento <eliminados>
    public static int[] getIdBaresEliminados(Document response) {
        NodeList listaEliminados = response.getElementsByTagName("eliminados");
        if(listaEliminados.getLength() == 1) {
            Node elim = listaEliminados.item(0);
            if (elim.getFirstChild() != null && elim.getFirstChild().getNodeValue().length() > 0) {
                String raw = elim.getFirstChild().getNodeValue();
                String[] array = raw.split(",");
                int[] ret = new int[array.length];
                for (int i = 0; i < array.length; i++) {
                    ret[i] = Integer.parseInt(array[i]);
                }
                return ret;
            }
        }
        return null;
    }

    public static Bar parseInfoBar(Element element) {
        NodeList content = element.getChildNodes();
        return new Bar(Integer.parseInt(element.getAttribute("id_Bar")),
                content.item(0).getFirstChild().getNodeValue(),//Nombre
                content.item(1).getFirstChild().getNodeValue(),//Dirección
                content.item(2).getFirstChild().getNodeValue(),//Tlf
                content.item(3).getFirstChild().getNodeValue(),//Correo
                Double.parseDouble(content.item(4).getFirstChild().getNodeValue()),//Latitud
                Double.parseDouble(content.item(5).getFirstChild().getNodeValue()),//Longitud
                content.item(6).getFirstChild().getNodeValue(),//Provincia
                content.item(7).getFirstChild().getNodeValue(),//Municipio
                ImgCodec.base64ToBitmap(content.item(8).getFirstChild().getNodeValue()),//Foto
                false, //favorito
                Integer.parseInt(element.getAttribute("version")));//VersionInfoBar;
    }

    public static ArrayList<LineaCarta> parseCarta(Context con, Element element) {
        ArrayList<LineaCarta> lineas = new ArrayList<LineaCarta>();
        NodeList content = element.getChildNodes();

        if (content.getLength() > 0) {
            for(int j=0;j<content.getLength();j++) {
                NodeList linea = content.item(j).getChildNodes();
                Producto p = new Producto(
                        Integer.parseInt(linea.item(0).getFirstChild().getNodeValue()),//ID producto
                        linea.item(1).getFirstChild().getNodeValue(),//Nombre producto
                        ImgCodec.base64ToBitmap(linea.item(4).getFirstChild().getNodeValue()));//Foto producto
                lineas.add(new LineaCarta(
                        p,
                        Double.parseDouble(linea.item(3).getFirstChild().getNodeValue()),//Precio linea
                        linea.item(2).getFirstChild().getNodeValue()));//Descripción linea

                if(!Persistencia.getInstancia().existeProducto(con, p.getId())) Persistencia.getInstancia().insertaProducto(con, p);
            }
        }
        return lineas;
    }

    public static ArrayList<Oferta> parseOfertas(Context con, Element element) {
        ArrayList<Oferta> lineas = new ArrayList<Oferta>();
        NodeList content = element.getChildNodes();

        if (content.getLength() > 0) {
            for(int j=0;j<content.getLength();j++) {
                NodeList linea = content.item(j).getChildNodes();
                lineas.add(new Oferta(Integer.parseInt(element.getAttribute("id_Bar")),//ID Bar
                        Persistencia.getInstancia().getProducto(con, Integer.parseInt(linea.item(0).getFirstChild().getNodeValue())),//Producto ya existente
                        "",//Descripción. Vacío siempre, se mantiene por falta de tiempo
                        Double.parseDouble(linea.item(1).getFirstChild().getNodeValue())));//Precio
            }
        }
        return lineas;
    }

}
