package comandapp.comandappcliente.presentacion.actividades;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import comandapp.comandappcliente.R;
import comandapp.comandappcliente.logicanegocio.LogicaNegocio;
import comandapp.comandappcliente.logicanegocio.objetos.Bar;
import comandapp.comandappcliente.presentacion.adaptadores.AdaptadorListadoBares;


public class BusquedaBar extends ActionBarActivity {

    private ListView lstBares;
    private AdaptadorListadoBares adaptador;
    private EditText editFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_bar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //getSupportActionBar().setIcon(R.drawable.logo_comandapp);

        ArrayList<Bar> bares = LogicaNegocio.getInstancia().getAllBaresHuecos(this);
        adaptador = new AdaptadorListadoBares(this, bares);

        ListView lstBares = (ListView)findViewById(R.id.ListaBares);

        lstBares.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Intent intent = new Intent(BusquedaBar.this, InicioBar.class);
                Bar b = (Bar)a.getItemAtPosition(position);
                intent.putExtra("id_bar",b.getIdBar());
                startActivity(intent);
            }
        });

        lstBares.setAdapter(adaptador);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Colocamos el menú definido para esta vista:
        getMenuInflater().inflate(R.menu.menu_busqueda_bar, menu);

        //Obtenemos la referencia al cajetín de búsqueda de la ActionBar:
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        //Le añadimos al cajetín de búsqueda la configuración de searchable.xml:
            /*
            Sin olvidar que para que encuentre el searchable tendremos que añadir lo siguiente al
            manifest, para esta clase:
                <intent-filter>
                    <action android:name="android.intent.action.SEARCH" />
                </intent-filter>
             */
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        //Declaramos el evento de cambio en el SearchView creado:
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                if (adaptador != null) {
                    adaptador.filter(newText);//Filtramos la lista de bares desde la ActionBar
                }
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                //Desde aquí se puede obtener el texto "query" introducido en la caja de búsqueda de la ActionBar
                return true;
            }
        };

        //Le asignamos el evento:
        searchView.setOnQueryTextListener(queryTextListener);

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
        else if(id == R.id.action_settings2)
        {
            //Date fecha = Calendar.getInstance().getTime();
            //Log.w("Fecha actual---------->", new SimpleDateFormat("dd/MM/yyyy").format(fecha) + "");
            Intent intent = new Intent(this, ComandaDetallada.class);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.action_favoritos)
        {
            Intent intent = new Intent(this, BaresFavoritos.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
