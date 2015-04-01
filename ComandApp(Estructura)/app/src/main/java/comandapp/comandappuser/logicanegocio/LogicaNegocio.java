package comandapp.comandappuser.logicanegocio;

import android.content.Context;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import comandapp.comandappuser.objetos.Bar;
import comandapp.comandappuser.persistencia.Persistencia;
import comandapp.comandappuser.utilidades.DOMParser;
import comandapp.comandappuser.webclient.XMLServerRequest;

/**
 * Created by G62 on 14-Mar-15.
 */
public class LogicaNegocio {

    private static LogicaNegocio instancia = null;
    private static Persistencia persistencia = Persistencia.getInstancia();
    private static ExecutorService pool = Executors.newFixedThreadPool(2);

    private LogicaNegocio() {
    }

    public static LogicaNegocio getInstancia(Context c) {
        if(instancia == null) instancia = new LogicaNegocio();
        return instancia;
    }

    public void rellenaLViewBares(Context context, ArrayList<Bar> bares) {
        Future<Document> response = pool.submit(new XMLServerRequest(context,"GLOC",persistencia.getMain(context)));
        pool.execute(new DOMParser(context, response, bares));
    }



}
