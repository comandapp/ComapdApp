package comandapp.comandappcliente.logicanegocio.utilidades;

import android.content.Context;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import comandapp.comandappcliente.logicanegocio.objetos.Bar;
import comandapp.comandappcliente.persistencia.Persistencia;
import comandapp.comandappcliente.logicanegocio.utilidades.ImgCodec;

/**
 * Created by G62 on 17-Mar-15.
 */
public class DOMParser implements Runnable {

    private Persistencia persistencia = Persistencia.getInstancia();
    private Context context;
    private Future<Document> response;
    private ArrayList<Bar> bares;

    public DOMParser(Context context, Future<Document> response, ArrayList<Bar> bares) {
        this.context = context;
        this.response = response;
        this.bares = bares;
    }

    public void run() {
        try {
            Document doc = response.get();
            if(doc != null) {
                int[] idActualizados = DOMParser.getIdBaresActualizados(doc);
                if(idActualizados != null) {
                    persistencia.getBaresIntoArray(context,idActualizados,bares,0);
                }
                DOMParser.rellenaArrayBaresLight(bares,doc);

                Log.d("MYAPP", "He terminado: "+bares.size());
            } else {

            }
        } catch(InterruptedException e) {
            Log.e("MYAPP", e.getMessage(), e);
        } catch(ExecutionException e) {
            Log.e("MYAPP", e.getCause().getMessage(), e);
        }


    }

    public static int[] getIdBaresActualizados(Document response) {
        NodeList listaActualizados = response.getElementsByTagName("actualizados");
        if(listaActualizados.getLength() == 1) {
            String raw = listaActualizados.item(0).getFirstChild().getNodeValue();
            String[] array = raw.split(",");
            int[] ret = new int[array.length];
            for(int i=0;i<array.length;i++) {
                ret[i] = Integer.parseInt(array[i]);
            }
            return ret;
        }
        return null;
    }

    public static int[] getIdBaresEliminados(Document response) {
        NodeList listaEliminados = response.getElementsByTagName("eliminados");
        if(listaEliminados.getLength() == 1) {
            String raw = listaEliminados.item(0).getFirstChild().getNodeValue();
            String[] array = raw.split(",");
            int[] ret = new int[array.length];
            for(int i=0;i<array.length;i++) {
                ret[i] = Integer.parseInt(array[i]);
            }
            return ret;
        }
        return null;
    }

    public static void rellenaArrayBaresLight(ArrayList<Bar> bares, Document response) {
        NodeList listaBares = response.getElementsByTagName("bar");
        if(listaBares.getLength() > 0) {
            Bar b;
            Element barE;
            for (int i = 0; i < listaBares.getLength(); i++) {
                barE = (Element) listaBares.item(i);
                b = parseBarLight(barE);
                bares.add(b);
            }
        }
    }

    public static Bar parseBarLight(Element element) {
        NodeList content = element.getChildNodes();
        String [] argum = new String[9];
        for (int j = 0; j < 9; j++) {
            argum[j + 2] = content.item(j).getFirstChild().getNodeValue();
        }

        return new Bar(Integer.parseInt(element.getAttribute("id")),
                argum[0],//Nombre
                argum[1],//Dirección
                Integer.parseInt(argum[2]),//Tlf
                argum[3],//Correo
                Double.parseDouble(argum[4]),//Latitud
                Double.parseDouble(argum[5]),//Longitud
                argum[6],//Provincia
                argum[7],//Municipio
                ImgCodec.base64ToBitmap(argum[8]),//Foto
                Integer.parseInt(element.getAttribute("version")));//VersionInfoBar
    }

//    public static HashMap<Integer,String> actualizarOfertas(ArrayList<Bar> bares) {
//        Document doc = getXMLDocument("GOFE", getIdArray(bares));
//        if (doc == null) return null;
//
//        NodeList listaOfertas = doc.getElementsByTagName("listaofertas");
//
//        if (listaOfertas.getLength() > 0) {
//            //Cada fila contiene 3 columnas(idBar,version y ArrayList<Oferta>).
//            Object[][] actualizada = new Object[listaOfertas.getLength()][3];
//            Bar original;
//            Element listaofertasElement, ofertaElement;
//            NodeList ofertas,content;
//            Oferta oferta;
//            String[] idProdArray;
//            Producto prod;
//            for (int i = 0; i < listaOfertas.getLength(); i++) {//ListaOfertas
//                listaofertasElement = (Element) listaOfertas.item(i);
//                actualizada[i][0] = Integer.parseInt(listaofertasElement.getAttribute("id"));
//                actualizada[i][1] = Integer.parseInt(listaofertasElement.getAttribute("version"));
//                actualizada[i][2] = new ArrayList<Oferta>();
//                original = getBar(bares, (int)actualizada[i][0]);
//
//                ofertas = listaofertasElement.getChildNodes();
//                for(int j=0;j<ofertas.getLength();j++) {//Ofertas dentro de la lista de cada bar (id oferta)
//                    ofertaElement = (Element)ofertas.item(j);
//                    content = ofertaElement.getChildNodes();
//
//                    oferta = new Oferta(Integer.parseInt(ofertaElement.getAttribute("id")),
//                            Double.parseDouble(content.item(2).getFirstChild().getNodeValue()),
//                            content.item(0).getFirstChild().getNodeValue(),
//                            comandapp.comandappuser.utilidades.ImgCodec.base64ToBitmap(content.item(3).getFirstChild().getNodeValue()));
//
//                    idProdArray = content.item(1).getFirstChild().getNodeValue().split((","));
//
//                    //El XSD no valida los productos.
//                    //En el XML vienen sus id en texto plano separados por comas.
//                    //Por lo tanto deben existir en la carta del bar correspondiente.
//                    if(idProdArray.length > 0) {
//                        for(String id : idProdArray) {
//                            prod = original.getCarta().getProductById(Integer.parseInt(id));
//                            if(prod != null) oferta.getProductos().add(prod);
//                            else {}//Producto no existe en el bar
//                        }
//                    } else {
//                        //Oferta sin productos TODO: Lo valida el XSD?
//                    }
//
//                    ((ArrayList<Oferta>)actualizada[i][2]).add(oferta);
//                }
//
//                original.importarOfertas((ArrayList<Oferta>)actualizada[i][2], (int)actualizada[i][1]);
//            }
//        } else {
//            //No hay elementos oferta
//        }
//
//
//        HashMap<Integer,String> errores = new HashMap<Integer,String>();
//        NodeList errorList = doc.getElementsByTagName("error");
//
//        if(errorList.getLength() > 0) {
//            Element error;
//            for (int i = 0; i < errorList.getLength(); i++) {
//                error = (Element)errorList.item(i);
//                errores.put(Integer.parseInt(error.getAttribute("id")), errorList.item(i).getFirstChild().getNodeValue());
//            }
//        }
//
//        return errores;
//    }


//    public static HashMap<Integer,String> procesaCarta(Document doc) {
//        NodeList listaCartas = doc.getElementsByTagName("carta");
//
//        if (listaCartas.getLength() > 0) {
//            //Cada fila contiene 3 columnas(idBar,version y carta).
//            Object[][] actualizada = new Object[listaCartas.getLength()][3];
//            Bar original;
//            Element cartaElement;
//            NodeList listaEntradas,listaContenido;
//            Linea_Carta e;
//            Producto p;
//            String[] argum;
//            for(int j=0;j<listaCartas.getLength();j++) {
//                cartaElement = (Element)listaCartas.item(j);
//                actualizada[j][0] = Integer.parseInt(cartaElement.getAttribute("id"));
//                actualizada[j][1] = Integer.parseInt(cartaElement.getAttribute("version"));
//                actualizada[j][2] = new Carta();
//                listaEntradas = cartaElement.getChildNodes();
//                for (int i = 0; i < listaEntradas.getLength(); i++) {
//                    listaContenido = listaEntradas.item(i).getChildNodes();
//                    argum = new String[6];
//                    for (int k = 0; k < 6; k++) {
//                        argum[k] = listaContenido.item(k).getFirstChild().getNodeValue();
//                    }
//
//                    p = new Producto(
//                            Integer.parseInt(argum[0]),
//                            argum[1], argum[3]);
//
//                    e = new Linea_Carta(
//                            p,
//                            Double.parseDouble(argum[4]),
//                            argum[2],
//                            argum[5]);
//
//                    ((Carta)actualizada[j][2]).aniadeEntrada(e);
//                }
//
//                original = getBar(bares, (int)actualizada[j][0]);
//                if(original != null) original.importarCarta((Carta)actualizada[j][2], (int)actualizada[j][1]);
//                else {} //Se ha enviado la info de un bar que no está en la lista de parámetros. (Poco probable)
//            }
//        }
//
//
//    }


}
