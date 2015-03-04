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
import java.util.ArrayList;
import java.util.HashMap;
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
import persistencia.Persistencia;

public class ServiceClient {

    public static int[] getIdArray(ArrayList<Bar> bares) {
        int[] idArray = new int[bares.size()];
        
        for(int i=0;i<bares.size();i++) {
            idArray[i] = bares.get(i).getId();
        }
        
        return idArray;
    }
    
    public static HashMap<Integer,Version> getMain(int[] idArray) {
        Document doc = getXMLDocument("MAIN", idArray);
        if (doc == null) return null;
        
        HashMap<Integer,Version> ret = new HashMap<Integer,Version>();
        
        NodeList mainList = doc.getElementsByTagName("main");
        NodeList errorList = doc.getElementsByTagName("error");
        
        if (mainList.getLength() > 0) {
           Element mainElement;
           NodeList content;
           int[] argum;
           Version v;
           for (int i = 0; i < mainList.getLength(); i++) {
               mainElement = (Element) mainList.item(i);
               content = mainElement.getChildNodes();
               argum = new int[3];
               for (int j = 0; j < 3; j++) {
                    argum[j] = Integer.parseInt(content.item(j).getFirstChild().getNodeValue());
               }
               
               v = new Version();
               v.setVersionInfoLocal(argum[0]);
               v.setVersionCarta(argum[1]);
               v.setVersionOfertas(argum[2]);
               
               
               ret.put(Integer.parseInt(mainElement.getAttribute("id")), v);
           }
        } else {
            //No hay elementos main
        }
        
        if(errorList.getLength() > 0) {
            //Hay elementos error
        }
        
        return ret;
    }
    
    //Devuelve un bar con id "idBar" en una lista de bares.
    private static Bar getBar(ArrayList<Bar> bares, int idBar) {
        for(int i=0;i<bares.size();i++) if(bares.get(i).getId() == idBar) return bares.get(i);
        return null;
    }
    
    //Actualiza los bares en la lista con la info del servidor. (NOTA: No los modifica en la persistencia)
    //Devuelve una tabla HashMap con los identificadores de los bares que han devuelto error y el mensaje de error.
    //Si todos se han actualizado el valor devuelto será la lista vacía.
    //Si ha habido problema al recibir/construir/validar XML devuelve null.
    public static HashMap<Integer,String> actualizarInfoBar(ArrayList<Bar> bares) {
        Document doc = getXMLDocument("GLOC", getIdArray(bares));
        if (doc == null) return null; //TODO: Volver a intentar la petición 
        
        NodeList localList = doc.getElementsByTagName("local");
        
        if (localList.getLength() > 0) {
            Bar original,nuevo;
            Element local;
            NodeList content;
            String[] argum;
            for (int i = 0; i < localList.getLength(); i++) {
                local = (Element) localList.item(i);
                content = local.getChildNodes();
                argum = new String[10];
                argum[0] = local.getAttribute("id");
                argum[1] = local.getAttribute("version");
                for (int j = 0; j < 8; j++) {
                    argum[j + 2] = content.item(j).getFirstChild().getNodeValue();
                }

                nuevo = new Bar(Integer.parseInt(argum[0]),
                        argum[2],//Nombre
                        argum[3],//Dirección
                        Integer.parseInt(argum[4]),//Tlf
                        argum[5],//Correo
                        Double.parseDouble(argum[6]),//Latitud
                        Double.parseDouble(argum[7]),//Longitud
                        argum[8],//Provincia
                        argum[9],//Municipio
                        Integer.parseInt(argum[1]));//VersionInfoBar
                
                original =getBar(bares, nuevo.getId());
                if(original != null) original.importarInfoBar(nuevo);
                else {} //Se ha enviado la info de un bar que no está en la lista de parámetros. (Poco probable)
            }
        }
        
        HashMap<Integer,String> errores = new HashMap<Integer,String>();
        NodeList errorList = doc.getElementsByTagName("error");    
        
        if(errorList.getLength() > 0) {
            Element error;
            for (int i = 0; i < errorList.getLength(); i++) {
                error = (Element)errorList.item(i);
                errores.put(Integer.parseInt(error.getAttribute("id")), errorList.item(i).getFirstChild().getNodeValue());
            }
        }
        
        return errores;
    }
    
    public static HashMap<Integer,String> actualizarCarta(ArrayList<Bar> bares) {
        Document doc = getXMLDocument("GCAR", getIdArray(bares));
        if (doc == null) return null;
        
        NodeList listaCartas = doc.getElementsByTagName("carta");
                
        if (listaCartas.getLength() > 0) {
            //Cada fila contiene 3 columnas(idBar,version y carta).
            Object[][] actualizada = new Object[listaCartas.getLength()][3];
            Bar original;
            Element cartaElement;
            NodeList listaEntradas,listaContenido;
            Entrada e;
            Producto p;
            String[] argum;
            for(int j=0;j<listaCartas.getLength();j++) {
                cartaElement = (Element)listaCartas.item(j);
                actualizada[j][0] = Integer.parseInt(cartaElement.getAttribute("id"));
                actualizada[j][1] = Integer.parseInt(cartaElement.getAttribute("version"));
                actualizada[j][2] = new Carta();
                listaEntradas = cartaElement.getChildNodes();
                for (int i = 0; i < listaEntradas.getLength(); i++) {
                    listaContenido = listaEntradas.item(i).getChildNodes();
                    argum = new String[6];
                    for (int k = 0; k < 6; k++) {
                        argum[k] = listaContenido.item(k).getFirstChild().getNodeValue();
                    }

                    p = new Producto(
                            Integer.parseInt(argum[0]),
                            argum[1], argum[3]);

                    e = new Entrada(
                            p,
                            Double.parseDouble(argum[4]),
                            argum[2],
                            argum[5]);

                    ((Carta)actualizada[j][2]).aniadeEntrada(e);
                }
                
                original =getBar(bares, (int)actualizada[j][0]);
                if(original != null) original.importarCarta((Carta)actualizada[j][2], (int)actualizada[j][1]);
                else {} //Se ha enviado la info de un bar que no está en la lista de parámetros. (Poco probable)
            }
        }
        
        HashMap<Integer,String> errores = new HashMap<Integer,String>();
        NodeList errorList = doc.getElementsByTagName("error");    
        
        if(errorList.getLength() > 0) {
            Element error;
            for (int i = 0; i < errorList.getLength(); i++) {
                error = (Element)errorList.item(i);
                errores.put(Integer.parseInt(error.getAttribute("id")), errorList.item(i).getFirstChild().getNodeValue());
            }
        }
        
        return errores;
        
    }

    public static HashMap<Integer,String> actualizarOfertas(ArrayList<Bar> bares) {
        Document doc = getXMLDocument("GOFE", getIdArray(bares));
        if (doc == null) return null;
        
        NodeList listaOfertas = doc.getElementsByTagName("listaofertas");
        
        if (listaOfertas.getLength() > 0) {
            //Cada fila contiene 3 columnas(idBar,version y ArrayList<Oferta>).
            Object[][] actualizada = new Object[listaOfertas.getLength()][3];
            Bar original;
            Element listaofertasElement, ofertaElement;
            NodeList ofertas,content;
            Oferta oferta;
            String[] idProdArray;
            Producto prod;
            for (int i = 0; i < listaOfertas.getLength(); i++) {//ListaOfertas 
                listaofertasElement = (Element) listaOfertas.item(i);
                actualizada[i][0] = Integer.parseInt(listaofertasElement.getAttribute("id"));
                actualizada[i][1] = Integer.parseInt(listaofertasElement.getAttribute("version"));
                actualizada[i][2] = new ArrayList<Oferta>();
                original = getBar(bares, (int)actualizada[i][0]);
                
                ofertas = listaofertasElement.getChildNodes();
                for(int j=0;j<ofertas.getLength();j++) {//Ofertas dentro de la lista de cada bar (id oferta)
                    ofertaElement = (Element)ofertas.item(j);
                    content = ofertaElement.getChildNodes();

                    oferta = new Oferta(Integer.parseInt(ofertaElement.getAttribute("id")),
                            Double.parseDouble(content.item(1).getFirstChild().getNodeValue()),
                            ImgCodec.decodeImage(content.item(2).getFirstChild().getNodeValue()));

                    idProdArray = content.item(0).getFirstChild().getNodeValue().split((","));

                    //El XSD no valida los productos.
                    //En el XML vienen sus id en texto plano separados por comas.
                    //Por lo tanto deben existir en la carta del bar correspondiente.
                    if(idProdArray.length > 0) {
                        for(String id : idProdArray) {
                            prod = original.getCarta().getProductById(Integer.parseInt(id));
                            if(prod != null) oferta.getProductos().add(prod);
                            else {}//Producto no existe en el bar
                        }
                    } else {
                        //Oferta sin productos TODO: Lo valida el XSD?
                    }

                    ((ArrayList<Oferta>)actualizada[i][2]).add(oferta);                        
                }
                
                original.importarOfertas((ArrayList<Oferta>)actualizada[i][2], (int)actualizada[i][1]);
            }
        } else {
            //No hay elementos oferta
        }
        
        
        HashMap<Integer,String> errores = new HashMap<Integer,String>();
        NodeList errorList = doc.getElementsByTagName("error");    
        
        if(errorList.getLength() > 0) {
            Element error;
            for (int i = 0; i < errorList.getLength(); i++) {
                error = (Element)errorList.item(i);
                errores.put(Integer.parseInt(error.getAttribute("id")), errorList.item(i).getFirstChild().getNodeValue());
            }
        }
        
        return errores;
    }

    private static Document getXMLDocument(String com, int[] idArray) {
        Socket s = null;
        PrintWriter write;
        BufferedReader read;

        String request = "GET /ComandappServer/index.php?COM=" + com + "&ID=";
        for (int i : idArray) {
            request += ""+ i + ",";
        }
        
        request = request.substring(0, request.length() - 1) + " HTTP/1.1";
        
        try {
            s = new Socket(InetAddress.getByName("127.0.0.1"), 80);

            write = new PrintWriter(s.getOutputStream());
            write.println(request);
            write.println("Host:127.0.0.1");
            write.println("Connection: close");
            write.println();
            write.flush();

            read = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
            String aux;
            boolean encontrado = false;
            String xml = "";
            while ((aux = read.readLine()) != null) {
                if (aux.indexOf("<?xml") >= 0) {
                    encontrado = true;
                }
                if (encontrado) {
                    xml += aux;
                }
            }
            
            //FIX: En el documento xml de carta aparece un 0 después de </root> lo que causaba error al validar.
            if((xml.charAt(xml.length()-1)) == '0') {
                xml = xml.substring(0, xml.length()-1);
            }
                        
            System.out.println(xml);

            s.close();
            
            if (validateResponse(xml, com)) {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                dbf.setNamespaceAware(true);
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));
                return doc;
            } else {
                //XML no válido
            }

        } catch (IOException ioE) {
            System.out.println(ioE.toString());
        } catch (SAXException ioSAX) {
            System.out.println(ioSAX.toString());
        } catch (ParserConfigurationException ioParse) {
            System.out.println(ioParse.toString());
        }
        return null;
    }

    private static boolean validateResponse(String xml, String option) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema;
            if (option.compareTo("GLOC") == 0) {
                schema = factory.newSchema(new File("src/xml/comandappLOCAL.xsd"));
            } else if (option.compareTo("MAIN") == 0) {
                schema = factory.newSchema(new File("src/xml/comandappMAIN.xsd"));
            } else if (option.compareTo("GCAR") == 0) {
                schema = factory.newSchema(new File("src/xml/comandappCARTA.xsd"));
            } else {
                schema = factory.newSchema(new File("src/xml/comandappOFERTAS.xsd"));
            }
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(xml)));

            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
