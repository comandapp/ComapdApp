package serviceclient;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.InputSource;

import objetos.*;
import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ServiceClient {
    
    public static Bar getBar(int idBar) {

        Socket s = null;
        PrintWriter write;
        BufferedReader read;
      
        Bar bar;
        
        try {
            s = new Socket(InetAddress.getByName("127.0.0.1"), 80);
            
            write = new PrintWriter(s.getOutputStream());
            write.println("GET /ComandappServer/index.php?COM=GLOC&ID="+idBar+" HTTP/1.1");
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
            
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder(); 
            Document doc = db.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));
            
            NodeList searchLocal = doc.getElementsByTagName("local");
            if(searchLocal.getLength() > 0) {
                Element local = (Element)searchLocal.item(0);
                NodeList searchContent = local.getChildNodes();
                if(searchContent.getLength() >= 10) {
                    String[] argum = new String[11];
                    argum[0] = ""+idBar;
                    for(int i = 0; i < 10; i++) {
                        argum[i+1] = searchContent.item(i).getFirstChild().getNodeValue();
                    }
                    bar = new Bar(Integer.parseInt(argum[0]),
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
                    
                    NodeList searchCarta = doc.getElementsByTagName("entrada");
                    if(searchCarta.getLength() > 0) {
                        Entrada e;
                        Producto p;
                        for(int i = 0; i < searchCarta.getLength(); i++) {
                            NodeList contenido = ((Element)searchCarta.item(i)).getChildNodes();
                            if(contenido.getLength() == 6) {
                                argum = new String[6];
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
                            } else {
                                //Número de argumentos para entrada inválidos.
                            }
                        }
                    }
                    return bar;
                } else {
                    //Número de elementos xml incorrectos.
                }
            } else {
                //No hay elemento raiz "local".
            }
            
        } catch(SAXException ioSAX) {
            System.out.println(ioSAX.toString());
        } catch(IOException ioe) {
            System.out.println(ioe.toString());
        } catch(ParserConfigurationException ioParse) {
            System.out.println(ioParse.toString());
        } finally {
            try {
                s.close();
            } catch(IOException ioe2) {
                System.out.println(ioe2.getMessage());
            }
        }
        return null;
       
    }

}
