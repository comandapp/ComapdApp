package comandapp.comandappuser.presentacion;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import comandapp.comandappuser.R;
import comandapp.comandappuser.logicanegocio.LogicaNegocio;
import comandapp.comandappuser.objetos.Bar;
import comandapp.comandappuser.presentacion.adaptadores.ListaBaresAdapter;

public class ListadoBares extends ActionBarActivity {

    private static ArrayList<Bar> bares = new ArrayList<Bar>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_bares);

        ListView listViewBares = (ListView) findViewById(R.id.listViewBares);
        ListaBaresAdapter adaptador = new ListaBaresAdapter(this,bares);
        listViewBares.setAdapter(adaptador);
        LogicaNegocio.getInstancia(this).rellenaLViewBares(this, bares);

        Toast.makeText(this,"Numero de bares mostrados: "+bares.size(), Toast.LENGTH_LONG).show();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_bares, menu);
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
