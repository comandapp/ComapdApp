package comandapp.comandappuser.webclient;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import comandapp.comandappuser.objetos.Version;
import comandapp.comandappuser.utilidades.XSDValidator;

/**
 * Created by G62 on 15-Mar-15.
 */
public class XMLServerRequest implements Callable<Document> {

    //Clase encargada de realizar peticiones al servidor y obtener y validar el documento XML resultante.
    //Se ejecuta en segundo plano y su función run() devuelve un objeto Document (árbol DOM)

    private static final String HOST_URL = "http://www.comandappserver.site50.net:80/index.php";

    private String queryString;
    private Context context;

    //- com puede tomar los valores del protocolo de comunicación con el servidor:
    //      GBAR, GLOC, GCAR, GOFE
    //- mainLocal contiene para cada id_Bar sus versiones almacenadas en la BD local.
    public XMLServerRequest(Context c,String com, HashMap<Integer,Version> mainLocal) {
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
            //con.setRequestProperty("Content-Length", ""+queryString.length());

            //Mandar petición
            con.setDoOutput(true);
            write = new PrintWriter(con.getOutputStream());
            write.println(queryString);
            write.flush();

            //Respuesta del servidor
            int responseCode = con.getResponseCode();

            read = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            xml = "";
            while ((inputLine = read.readLine()) != null) {
                xml += inputLine;
            }

            write.close();
            read.close();

            Log.i("MYAPP", xml);

            //Se supone que el servidor envía en UTF-8
            //FIX para eliminar publicidad del hosting gratuito
            xml = xml.substring(xml.indexOf("<?xml"), xml.indexOf("</root>")+7);

            Log.i("MYAPP", xml);

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
