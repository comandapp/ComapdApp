package com.example.alumno.comandapp1;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;


public class BaresFavoritos extends ActionBarActivity {

    ListView lstBares;
    AdaptadorTitulares adaptador;
    EditText editFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bares_favoritos);

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

        adaptador = new AdaptadorTitulares(this, listadoBares);

        lstBares = (ListView)findViewById(R.id.ListaBares);

        lstBares.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                Bar opcionSeleccionada = (Bar)a.getItemAtPosition(position);

                Intent intent = new Intent(BaresFavoritos.this, InicioBar.class);
                intent.putExtra("bar",opcionSeleccionada);
                startActivity(intent);
            }
        });

        lstBares.setAdapter(null);
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

    class AdaptadorTitulares extends ArrayAdapter<Bar> {

        Activity context;
        ArrayList<Bar> listadoBares;
        ArrayList<Bar> listadoBaresAux;

        public AdaptadorTitulares(Activity context, ArrayList<Bar> datos) {
            super(context, R.layout.listitem_bar, datos);
            this.context = context;
            listadoBaresAux = new ArrayList<Bar>();
            listadoBares = new ArrayList<Bar>();
            listadoBaresAux.addAll(datos);

            for(Bar b : listadoBaresAux)
            {
                if(b.getFavorito())
                {
                    listadoBares.add(b);
                }
            }

            listadoBaresAux.clear();
            listadoBaresAux.addAll(listadoBares);



            Log.w("lb-------------->", listadoBares.size() + "");
            Log.w("lba-------------->", listadoBaresAux.size() + "");
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View item = convertView;
            ViewHolder holder;

            if(item == null)
            {
                LayoutInflater inflater = context.getLayoutInflater();
                item = inflater.inflate(R.layout.listitem_bar, null);

                holder = new ViewHolder();
                holder.nombre = (TextView)item.findViewById(R.id.lblNombreBar);
                holder.checkFavorito = (CheckBox)item.findViewById(R.id.checkBarFavorito);

                item.setTag(holder);
            }
            else
            {
                holder = (ViewHolder)item.getTag();
            }

            if(position < listadoBares.size())
            {
                Bar bar = listadoBares.get(position);
                holder.nombre.setText(bar.getNombre());
                holder.checkFavorito.setChecked(bar.getFavorito());
            }

            return(item);
        }

        public void filter(String charText) {
            //Log.w("-", charText.toString());
            charText = charText.toLowerCase(Locale.getDefault());
            listadoBares.clear();
            if (charText.length() == 0) {
                listadoBares.addAll(listadoBaresAux);
            }
            else
            {
                for (Bar bar : listadoBaresAux)
                {
                    if (bar.getNombre().toLowerCase(Locale.getDefault()).contains(charText))
                    {
                        listadoBares.add(bar);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }

    static class ViewHolder {
        TextView nombre;
        CheckBox checkFavorito;
    }
}
