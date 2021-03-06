package comandapp.comandappcliente.logicanegocio.utilidades;

import android.content.Context;
import android.util.Log;

import java.io.StringReader;
import java.net.URL;

import mf.javax.xml.transform.Source;
import mf.javax.xml.transform.stream.StreamSource;
import mf.javax.xml.validation.Schema;
import mf.javax.xml.validation.SchemaFactory;
import mf.javax.xml.validation.Validator;
import mf.org.apache.xerces.jaxp.validation.XMLSchemaFactory;

/**
 * Created by G62 on 28-Mar-15.
 */

//Validdor XSD para las respuestas XML del servidor.
public class XSDValidator {

    //Valida una cadena contra comandappResponse.xsd
    public static boolean validateXMLResponse(Context c, String xml) {
        if(xml == null || xml.length()==0) return false;
        try {
            SchemaFactory  factory = new XMLSchemaFactory();
            URL schemaFile = new URL("http://193.146.250.82/osfm/files/xml/comandappRESPONSE.xsd");
            Schema schema = factory.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(xml)));
            return true;
        } catch (Exception e) {
            Log.e("MYAPP", e.getMessage(), e);
            return false;
        }
    }
}
