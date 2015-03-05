package com.example.alumno.comandapp1;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Locale;


public class BusquedaBar extends ActionBarActivity {

    ListView lstBares;
    AdaptadorTitulares adaptador;
    EditText editFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_bar);

        Bar[] datos = new Bar[]{
                        new Bar("Entrepuentes 1"),
                        new Bar("Antrepuentes 2"),
                        new Bar("Entrepuentes 3"),
                        new Bar("Antrepuentes 4"),
                        new Bar("Antrepuentes 5"),
                        new Bar("Entrepuentes 6"),
                        new Bar("Entrepuentes 7"),
                        new Bar("Dntrepuentes 8"),
                        new Bar("Dntrepuentes 9"),
                        new Bar("Entrepuentes 10"),
                        new Bar("Entrepuentes 11")};

        adaptador = new AdaptadorTitulares(this, datos);

        lstBares = (ListView)findViewById(R.id.ListaBares);

        lstBares.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                Bar opcionSeleccionada = (Bar)a.getItemAtPosition(position);

                Intent intent = new Intent(BusquedaBar.this, InicioBar.class);

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

        editFilter = (EditText)findViewById(R.id.EditFilter);

        editFilter.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0)
            {
                // TODO Auto-generated method stub
                String text = editFilter.getText().toString().toLowerCase(Locale.getDefault());
                adaptador.getFilter().filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_busqueda_bar, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

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
        Bar[] datos;

        public AdaptadorTitulares(Activity context, Bar[] datos) {
            super(context, R.layout.listitem_bar, datos);
            this.context = context;
            this.datos = datos;
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

                item.setTag(holder);
            }
            else
            {
                holder = (ViewHolder)item.getTag();
            }

            holder.nombre.setText(datos[position].getNombre());

            return(item);
        }


    }

    static class ViewHolder {
        TextView nombre;
    }
}
