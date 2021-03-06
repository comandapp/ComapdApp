package comandapp.comandappcliente.logicanegocio.webclient;

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpStatus;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import comandapp.comandappcliente.logicanegocio.objetos.Version;
import comandapp.comandappcliente.logicanegocio.utilidades.XSDValidator;

/**
 * Created by G62 on 15-Mar-15.
 */
public class HTTPServerRequest implements Callable<Document> {

    //Clase encargada de realizar peticiones al servidor y obtener el documento XML resultante.
    //No se pueden realizar peticiones HTTP desde la UI Thread, por lo que se implementa como un callable.
    //El Future resultante de la llamada a call() será el objeto Document ya validado o null en caso de error.

    private static final String HOST_URL = "http://193.146.250.82:80/osfm/files/server.php";

    private String queryString;
    private Context context;

    //- com puede tomar los valores del protocolo de comunicación con el servidor:
    //      GLOC
    //- mainLocal contiene para cada id_Bar sus versiones almacenadas en la BD local.
    public HTTPServerRequest(Context c, String com, HashMap<Integer, Version> mainLocal) {
        this.context = c;
        queryString = "COM="+com;
        String aux = parseMainUTF8(mainLocal);
        if(mainLocal != null && mainLocal.size() > 0 && aux != null) {
            queryString += "&MAIN="+aux;
        }
    }

    @Override
    public Document call() {
        Document doc = null;
        PrintWriter write;
        BufferedReader read;
        String xml = null;

        try {
            URL obj = new URL(HOST_URL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            //Cabeceras HTTP
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-type", "application/x-www-form-urlencoded; charset=utf-8");

            //Mandar petición
            con.setDoOutput(true);
            write = new PrintWriter(con.getOutputStream());
            write.println(queryString);
            write.flush();

            //Respuesta del servidor
            int status = con.getResponseCode();
            if(status >= HttpStatus.SC_BAD_REQUEST) {
                read = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            } else {
                read = new BufferedReader(new InputStreamReader(con.getInputStream()));
            }

            //Cadena XML
            String inputLine;
            xml = "";
            while ((inputLine = read.readLine()) != null) {
                xml += inputLine;
            }

            //Fin de la comunicación
            write.close();
            read.close();

            //FIX para eliminar posible contenido no deseado
            xml = xml.substring(xml.indexOf("<?xml"), xml.indexOf("</root>")+7);

            //Validación
            if (XSDValidator.validateXMLResponse(context,xml)) {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                dbf.setNamespaceAware(true);
                DocumentBuilder db = dbf.newDocumentBuilder();
                doc = db.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));
            } else {
                //XML no válido
            }
        } catch (IOException e) {
            Log.e("MYAPP", e.getMessage(), e);
        } catch (SAXException e) {
            Log.e("MYAPP", e.getMessage(), e);
        } catch (ParserConfigurationException e) {
            Log.e("MYAPP", e.getMessage(), e);
        }

        return doc;
    }

    //Parsea el main local a UTF-8 para su posterior envío.
    private static String parseMainUTF8(HashMap<Integer,Version> main) {
        String ret = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        ret += "<root xmlns=\"comandappMAIN.xsd\" " +
            "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
            "xsi:schemaLocation=\"xml/comandappMAIN.xsd\">";

        for(Map.Entry<Integer,Version> e : main.entrySet()) {
            ret += "<main xmlns=\"comandappMAIN.xsd\" id=\""+e.getKey()+"\">";
            ret += "<local xmlns=\"\">"+e.getValue().getVersionInfoLocal()+"</local>";
            ret += "<carta xmlns=\"\">"+e.getValue().getVersionCarta()+"</carta>";
            ret += "<ofertas xmlns=\"\">"+e.getValue().getVersionOfertas()+"</ofertas>";
            ret += "</main>";
        }

        ret += "</root>";
        try {
            return URLEncoder.encode(ret,"UTF-8");
        } catch(UnsupportedEncodingException e) {
            Log.e("MYAPP", e.getMessage(), e);
            return null;
        }
    }

}
