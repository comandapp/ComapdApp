package comandapp.comandappcliente.logicanegocio;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

    public ArrayList<Comanda> getAllComandas(Context con) {
        ArrayList<Comanda> c = persistencia.getComandas(con);
        if(c.size()>0) return c;
        else return null;
    }

    public void insertaComanda(Context con, Comanda comanda, int idBar)
    {
        persistencia.insertaComanda(con, comanda, idBar);
    }

    public void setBarFavorito(Context con, Bar b, boolean fav) {
        b.setFavorito(fav);
        persistencia.actualizaFavoritoBar(con, b);
    }


    private int[] arrayListToArray(ArrayList<Integer> list) {
        int[] array = new int[list.size()];
        for(int i = 0; i<list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    public ArrayList<LineaCarta> getCarta(Context con, int id_Bar) {
        return persistencia.getCarta(con,id_Bar);
    }

    public ArrayList<Oferta> getOfertas(Context con, int id_Bar) {
        return persistencia.getOfertas(con, id_Bar);
    }

    public double getPrecioFinalLineaCarta(Context con, LineaCarta lc, ArrayList<Oferta> ofertas) {
        for(Oferta of : ofertas) {
            if(lc.getProducto().getId() == of.getProducto().getId()) {
                return of.getPrecio();
            }
        }
        return -1;
    }

    public void insertaLineasComandaEnCurso(Context con, ArrayList<LineaComandaEnCurso> lineas)
    {
        persistencia.insertaLineasComandaEnCurso(con, lineas);
    }

    public ArrayList<LineaComandaEnCurso> getLineasComandaEnCurso(Context con) {
        ArrayList<LineaComandaEnCurso> c = persistencia.getLineasComandaEnCurso(con);
        if(c.size()>0) return c;
        else return null;
    }

    public ArrayList<LineaComanda> lineasComandaEnCursoToLineasComanda(Context con, int idBar)
    {
        ArrayList<LineaComandaEnCurso> lineasComandaEnCurso = persistencia.getLineasComandaEnCurso(con);

        if(lineasComandaEnCurso.size() > 0)
        {
            ArrayList<LineaComanda> lineasComanda = new ArrayList<LineaComanda>();

            for(LineaComandaEnCurso lineaCurso : lineasComandaEnCurso)
            {
                lineasComanda.add(new LineaComanda(persistencia.getLineaCarta(con, idBar, lineaCurso.getIdProducto()), lineaCurso.getCantidad()));
            }

            return(lineasComanda);
        }

        return null;
    }

    public void borraLineasComandaEnCurso(Context con){
        persistencia.borraLineaComandaEnCurso(con);
    }

    public void insertaLineasComanda(Context con, String nombreComanda, ArrayList<LineaComanda> lineas)
    {
        if(lineas.size() > 0)
        {
            persistencia.insertaLineasComanda(con, nombreComanda, lineas);
        }
    }
}
