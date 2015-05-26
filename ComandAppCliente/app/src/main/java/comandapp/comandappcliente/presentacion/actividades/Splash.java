package comandapp.comandappcliente.presentacion.actividades;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import comandapp.comandappcliente.R;
import comandapp.comandappcliente.logicanegocio.LogicaNegocio;
import comandapp.comandappcliente.logicanegocio.utilidades.DOMParser;
import comandapp.comandappcliente.logicanegocio.webclient.HTTPServerRequest;
import comandapp.comandappcliente.persistencia.Persistencia;
import comandapp.comandappcliente.persistencia.SQLHelper;

//Actividad principal o pantalla de carga.
//Lanza los dos threads:
    //HTTPServerRequest para recibir el DOM Document
    //Thread anónimo para el parseo
//Inicia la MainActivity cuando termina el segundo.
public class Splash extends ActionBarActivity {

    private static ExecutorService pool = Executors.newFixedThreadPool(2);
    private static Persistencia persistencia = Persistencia.getInstancia();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);

        final Future<Document> response = pool.submit(new HTTPServerRequest(this.getBaseContext(), "GLOC", LogicaNegocio.getInstancia().getMain(this)));
        final Intent intent = new Intent(Splash.this, MainActivity.class);
        final Context con = this;

        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    Document doc = response.get();

                    if(doc != null) {
                        SQLiteOpenHelper sql = Persistencia.getInstancia().getSQL(con);
                        SQLiteDatabase dbw = sql.getWritableDatabase();

                        NodeList listaInfoBares = doc.getElementsByTagName("bar");
                        for(int i =0; i < listaInfoBares.getLength(); i++) {
                            persistencia.actualizaInfoBar(con, dbw, DOMParser.parseInfoBar((Element)listaInfoBares.item(i)));
                        }

                        NodeList listaCartas = doc.getElementsByTagName("carta");
                        for(int i =0; i < listaCartas.getLength(); i++) {
                            Element e = (Element)listaCartas.item(i);
                            persistencia.actualizaCarta(dbw,
                                    Integer.parseInt(e.getAttribute("id_Bar")),
                                    DOMParser.parseCarta(con, e),
                                    Integer.parseInt(e.getAttribute("version")));
                        }

                        NodeList listaOfertas = doc.getElementsByTagName("ofertas");
                        for(int i =0; i < listaOfertas.getLength(); i++) {
                            Element e = (Element)listaOfertas.item(i);
                            persistencia.actualizaOfertas(dbw,
                                    Integer.parseInt(e.getAttribute("id_Bar")),
                                    DOMParser.parseOfertas(con, e),
                                    Integer.parseInt(e.getAttribute("version")));
                        }

                        int[] eliminados = DOMParser.getIdBaresEliminados(doc);
                        if(eliminados != null) {
                            for (int i : eliminados) {
                                persistencia.eliminarBar(con, i);
                            }
                        }

                        dbw.close();
                        sql.close();
                    } else {
                        //Error de transmisión
                    }

                    Intent i = new Intent(Splash.this,
                            MainActivity.class);
                    startActivity(i);
                    finish();

                } catch(InterruptedException e) {
                    Log.e("MYAPP", e.getMessage(), e);
                } catch(ExecutionException e) {
                    Log.e("MYAPP", e.getCause().getMessage(), e);
                } catch(NullPointerException e) {
                    Log.e("MYAPP", e.getMessage(), e);
                }
            }
        };
        welcomeThread.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
