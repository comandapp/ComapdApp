package serviceclient;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.Socket;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.InputSource;

import objetos.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ServiceClient {
    
        public static Bar getBar(int idBar) {

        Document doc = getXMLDocument("GLOC", idBar);
        if(doc == null) return null;
        
        NodeList localList = doc.getElementsByTagName("local");
        Element local = (Element)localList.item(0);
        NodeList content = local.getChildNodes();
        String[] argum = new String[11];
        argum[0] = ""+idBar;
        for(int i = 0; i < 10; i++) {
            argum[i+1] = content.item(i).getFirstChild().getNodeValue();
        }

        Bar bar = new Bar(Integer.parseInt(argum[0]),
                argum[1],
                argum[2],
                Integer.parseInt(argum[3]),
                argum[4],
                Double.parseDouble(argum[5]),
                Double.parseDouble(argum[6]),
                argum[7],
                argum[8],
                Integer.parseInt(argum[9]),
                Integer.parseInt(argum[10]));

        NodeList carta = doc.getElementsByTagName("entrada");
        if(carta.getLength() > 0) {
            Entrada e;
            Producto p;
            for(int i = 0; i < carta.getLength(); i++) {
                NodeList contenido = ((Element)carta.item(i)).getChildNodes();
                argum = new String[6]; //Reuso de variable.
                for(int j = 0; j < 6; j++) {
                    argum[j] = contenido.item(j).getFirstChild().getNodeValue();
                }

                p = new Producto(
                        Integer.parseInt(argum[0]),
                        argum[1], argum[3]);

                e = new Entrada(
                        p,
                        Double.parseDouble(argum[4]),
                        argum[2],
                        argum[5]);

                bar.getCarta().aniadeEntrada(e);
            }
        }
        return bar;
    }
    
    //Precondición: com debe ser "MAIN", "GLOC" o "GOFE".
    //Postcondición: Devuelve un documento DOM validado con la respuesta del servidor.
    //  Si no existe el bar con ese ID -> _______(Transmisión correcta. Error 404)
    //  Si no valida la respuesta del servidor -> null (Error en las transmisiones)
    private static Document getXMLDocument(String com, int id) {
        Socket s = null;
        PrintWriter write;
        BufferedReader read;
        
        try {
            s = new Socket(InetAddress.getByName("127.0.0.1"), 80);
            
            write = new PrintWriter(s.getOutputStream());
            write.println("GET /ComandappServer/index.php?COM="+com+"&ID="+id+" HTTP/1.1");
            write.println("Host:127.0.0.1");
            write.println("Connection: close");
            write.println();
            write.flush();
            
            read = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
            String aux;
            boolean encontrado = false;
            String xml = "";
            while((aux = read.readLine()) != null) {
                if(aux.indexOf("<?xml") >= 0) encontrado = true;
                if(encontrado) xml += aux;
            }
            
            s.close();
            
            //TODO: Crear una nueva excepción para errores que maneje las respuestas del servidor.
            //if(validateErrorResponse(xml)) return null;
            if(validateResponse(xml,com)) {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                dbf.setNamespaceAware(true);
                DocumentBuilder db = dbf.newDocumentBuilder(); 
                Document doc = db.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));
                return doc;
            }
            
        } catch(IOException ioE) {
            System.out.println(ioE.toString());
        } catch(SAXException ioSAX) {
            System.out.println(ioSAX.toString());
        } catch(ParserConfigurationException ioParse) {
            System.out.println(ioParse.toString());
        }
        return null;
    }
    
    //Postcondición: Devuelve cierto si valida el mensaje de error, falso si no.
    private static boolean validateErrorResponse(String xml) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);            
            Schema schema = factory.newSchema(new File("src/xml/comandappERROR.xsd"));

            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(xml)));
            
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
    private static boolean validateResponse(String xml, String option) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);            
            Schema schema;
            if(option.compareTo("GLOC") == 0) {
                schema = factory.newSchema(new File("src/xml/comandappLOCAL.xsd"));
            } else if(option.compareTo("MAIN") == 0) {
                schema = factory.newSchema(new File("src/xml/comandappMAIN.xsd"));
            } else {
                schema = factory.newSchema(new File("src/xml/comandappOFERTAS.xsd"));
            }
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(xml)));
            
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
