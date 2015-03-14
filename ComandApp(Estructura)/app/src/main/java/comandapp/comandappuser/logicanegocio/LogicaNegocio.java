package comandapp.comandappuser.logicanegocio;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

import comandapp.comandappuser.objetos.Bar;
import comandapp.comandappuser.objetos.Version;
import comandapp.comandappuser.persistencia.Persistencia;
import comandapp.comandappuser.webclient.WebClient;

/**
 * Created by G62 on 14-Mar-15.
 */
public class LogicaNegocio {

    private static LogicaNegocio instancia = null;
    private static Persistencia pers = null;

    private LogicaNegocio() {

    }

    public static LogicaNegocio getInstancia() {
        if(instancia == null) instancia = new LogicaNegocio();
        return instancia;
    }

    public void actualizaBaseDatos(Context c) {
        HashMap<Integer,Version> mainServidor = WebClient.getMain();
        HashMap<Integer,Version> mainLocal = Persistencia.getInstancia(c).getMain();

        if(mainLocal.size() == 0) {
            poblarBaseDatos(mainServidor, c);
        }
    }

    //Poco eficiente. Mejorar
    private void poblarBaseDatos(HashMap<Integer,Version> mainServidor, Context c) {
        ArrayList<Bar> bares = new ArrayList<Bar>();
        for(int id : mainServidor.keySet()) {
            bares.add(new Bar(id));
        }
        WebClient.actualizarInfoBar(bares);
        WebClient.actualizarCarta(bares);
        WebClient.actualizarOfertas(bares);

        for(Bar b : bares) Persistencia.getInstancia(c).insertarBar(b);
    }
}
