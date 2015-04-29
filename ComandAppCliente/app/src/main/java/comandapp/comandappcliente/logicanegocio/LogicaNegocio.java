package comandapp.comandappcliente.logicanegocio;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import comandapp.comandappcliente.logicanegocio.objetos.*;
import comandapp.comandappcliente.persistencia.Persistencia;
import comandapp.comandappcliente.logicanegocio.utilidades.DOMParser;
import comandapp.comandappcliente.logicanegocio.webclient.HTTPServerRequest;
import comandapp.comandappcliente.persistencia.SQLHelper;

/**
 * Created by G62 on 14-Mar-15.
 */
public class LogicaNegocio {

    private static LogicaNegocio instancia = null;
    private static Persistencia persistencia = Persistencia.getInstancia();
    private static ExecutorService pool = Executors.newFixedThreadPool(2);

    private LogicaNegocio() {
    }

    public static LogicaNegocio getInstancia() {
        if(instancia == null) instancia = new LogicaNegocio();
        return instancia;
    }

    //Función sin acabar
    public void rellenaLViewBares(Context context, ArrayList<Bar> bares) {
        Future<Document> response = pool.submit(new HTTPServerRequest(context,"GLOC",persistencia.getMain(context)));
        pool.execute(new DOMParser(context, response, bares));
    }

    //Devuelve un objeto bar sin carta ni ofertas
    public Bar getBarHueco(Context con, int id) {
        ArrayList<Bar> b = persistencia.getBares(con, new int[]{id}, false);
        if(b.size()>0) return b.get(0);
        else return null;
    }

    //Devuelve el objeto bar con carta y ofertas
    public Bar getBar(Context con, int id) {
        ArrayList<Bar> b = persistencia.getBares(con, new int[]{id}, true);
        if(b.size()>0) return b.get(0);
        else return null;
    }

    public ArrayList<Bar> getBaresHuecos(Context con, ArrayList<Integer> ids) {
        ArrayList<Bar> b = persistencia.getBares(con, arrayListToArray(ids), false);
        if(b.size()>0) return b;
        else return null;
    }

    public ArrayList<Bar> getBares(Context con, ArrayList<Integer> ids) {
        ArrayList<Bar> b = persistencia.getBares(con, arrayListToArray(ids), true);
        if(b.size()>0) return b;
        else return null;
    }

    public ArrayList<Bar> getAllBaresHuecos(Context con) {
        ArrayList<Bar> b = persistencia.getBares(con, null, false);
        if(b.size()>0) return b;
        else return null;
    }

    public ArrayList<Bar> getAllBares(Context con) {
        ArrayList<Bar> b = persistencia.getBares(con, null, true);
        if(b.size()>0) return b;
        else return null;
    }


    private int[] arrayListToArray(ArrayList<Integer> list) {
        int[] array = new int[list.size()];
        for(int i = 0; i<list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }
}
