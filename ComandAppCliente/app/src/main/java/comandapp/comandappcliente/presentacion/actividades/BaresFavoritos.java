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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import comandapp.comandappcliente.R;
import comandapp.comandappcliente.logicanegocio.LogicaNegocio;
import comandapp.comandappcliente.logicanegocio.objetos.Bar;
import comandapp.comandappcliente.presentacion.adaptadores.AdaptadorListadoBares;

public class BaresFavoritos extends ActionBarActivity {

    private LogicaNegocio logicaN = LogicaNegocio.getInstancia();

    ListView lstBares;
    AdaptadorListadoBares adaptador;
    EditText editFilter;
    CheckBox checkFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bares_favoritos);


        //ArrayList<Bar> listadoBares = logicaN.getAllBares(this);

        ArrayList<Bar> listadoBares = new ArrayList<Bar>();
        ArrayList<Bar> listadoBaresAux = new ArrayList<Bar>();
        listadoBares.add(new Bar(1,"Favorito 1","dire",12345,"aaa",12.123123,12.123123,"aaaa","bbb",0,0, true));
        listadoBares.add(new Bar(1,"nombre1","dire",12345,"aaa",22.123123,22.123123,"aaaa","bbb",0,0, false));
        listadoBares.add(new Bar(1,"Favorito 2","dire",12345,"aaa",32.123123,32.123123,"aaaa","bbb",0,0, true));
        listadoBares.add(new Bar("Favorito 3", true));
        listadoBares.add(new Bar("nombre2", false));
        listadoBares.add(new Bar("Favorito 4", true));


        for(int i = 0; i < listadoBares.size(); i++)
        {
            Bar bareto = listadoBares.get(i);
            if(bareto.getFavorito())
            {
                listadoBaresAux.add(bareto);
            }
        }
        listadoBares.clear();

        adaptador = new AdaptadorListadoBares(this, listadoBaresAux);

        lstBares = (ListView)findViewById(R.id.ListaBares);

        lstBares.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                Bar opcionSeleccionada = (Bar)a.getItemAtPosition(position);

                Intent intent = new Intent(BaresFavoritos.this, InicioBar.class);
                intent.putExtra("bar",opcionSeleccionada);
                startActivity(intent);
            }
        });

        //CheckBox checkFav = ( CheckBox ) findViewById( R.id.checkBarFavorito );
        //if(checkFav == null) Log.w("-->", "pepe");

        /*checkFav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    new AlertDialog.Builder(BaresFavoritos.this)
                            .setTitle("Hola")
                            .setMessage("Hola Jesusito")
                            .setCancelable(true).create().show();
                }

            }
        });*/

        //lstBares.setAdapter(null);
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

        return super.onOptionsItemSelected(item);
    }
}
