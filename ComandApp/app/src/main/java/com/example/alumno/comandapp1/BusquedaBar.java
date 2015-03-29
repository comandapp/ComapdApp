package com.example.alumno.comandapp1;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class BusquedaBar extends ActionBarActivity {

    ListView lstBares;
    AdaptadorListadoBares adaptador;
    EditText editFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_bar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //getSupportActionBar().setIcon(R.drawable.logo_comandapp);

        final ArrayList<Bar> listadoBares = new ArrayList<Bar>();
        listadoBares.add(new Bar(1,"nombre","dire",12345,"aaa",12.123123,12.123123,"aaaa","bbb",0,0, true));
        listadoBares.add(new Bar(1,"nombre2","dire",12345,"aaa",22.123123,22.123123,"aaaa","bbb",0,0, false));
        listadoBares.add(new Bar(1,"nombre3","dire",12345,"aaa",32.123123,32.123123,"aaaa","bbb",0,0, true));
        listadoBares.add(new Bar("Antrepuentes 1", true));
        listadoBares.add(new Bar("Antrepuentes 1", true));
        listadoBares.add(new Bar("Entrepuentes 1", false));
        listadoBares.add(new Bar("Dntrepuentes 1", true));
        listadoBares.add(new Bar("Dntrepuentes 1", false));
        listadoBares.add(new Bar("Entrepuentes 1", false));

        adaptador = new AdaptadorListadoBares(this, listadoBares);

        lstBares = (ListView)findViewById(R.id.ListaBares);

        lstBares.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                Bar opcionSeleccionada = (Bar)a.getItemAtPosition(position);

                Intent intent = new Intent(BusquedaBar.this, InicioBar.class);
                intent.putExtra("bar",opcionSeleccionada);
                //Creamos la información a pasar entre actividades
                //Bundle b = new Bundle();
                //b.put("BAR", opcionSeleccionada);

                //Añadimos la información al intent
                //intent.putExtra("BAR", opcionSeleccionada);

                //Iniciamos la nueva actividad
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
