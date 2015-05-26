package comandapp.comandappcliente.presentacion.actividades;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import comandapp.comandappcliente.R;
import comandapp.comandappcliente.logicanegocio.LogicaNegocio;
import comandapp.comandappcliente.logicanegocio.objetos.Comanda;
import comandapp.comandappcliente.presentacion.adaptadores.AdaptadorHistorialComandas;


public class HistorialComandas extends ActionBarActivity {

    private ListView lstComandas;
    private AdaptadorHistorialComandas adaptador;
    private EditText editFilter;
    private String nombreComandaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_comandas);

        ArrayList<Comanda> comandas = LogicaNegocio.getInstancia().getAllComandas(this);

        adaptador = new AdaptadorHistorialComandas(this, comandas);

        ListView lstComandas = (ListView)findViewById(R.id.ListaComandas);

        lstComandas.setAdapter(adaptador);

        lstComandas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Intent intent = new Intent(HistorialComandas.this, ComandaDetallada.class);
                Comanda c = (Comanda) a.getItemAtPosition(position);
                intent.putExtra("nombre_comanda", c.getNombre());
                startActivity(intent);
            }
        });

        registerForContextMenu(lstComandas);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Colocamos el menu definido para esta vista:
        getMenuInflater().inflate(R.menu.menu_historial_comandas, menu);

        //Obtenemos la referencia al cajeton de busqueda de la ActionBar:
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        //Le anadimos al cajeton de busqueda la configuracion de searchable.xml:
            /*
            Sin olvidar que para que encuentre el searchable tendremos que anadir lo siguiente al
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
                    adaptador.filter(newText);//Filtramos la lista de comandas desde la ActionBar
                }
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                //Desde aqui se puede obtener el texto "query" introducido en la caja de busqueda de la ActionBar
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

    AdapterView.AdapterContextMenuInfo info;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        nombreComandaSeleccionada = ((TextView) info.targetView.findViewById(R.id.lblNombreComanda)).getText().toString();
        menu.setHeaderTitle(nombreComandaSeleccionada);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_historial_seleccion, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.OpcEditar:
                final EditText txtNombreComanda = new EditText(HistorialComandas.this);

                new AlertDialog.Builder(HistorialComandas.this)
                        .setTitle("Modificar nombre")
                        .setMessage("Introduce el nombre de la comanda a modificar:")
                        .setView(txtNombreComanda)
                        .setNegativeButton("Cancelar", null)
                        .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String nomCom = txtNombreComanda.getText().toString();

                                if(!nomCom.isEmpty())
                                {
                                    if(!LogicaNegocio.getInstancia().existeComanda(HistorialComandas.this, nomCom))
                                    {
                                        LogicaNegocio.getInstancia().modificaNombreComanda(HistorialComandas.this, nombreComandaSeleccionada, nomCom);
                                        //((TextView) info.targetView.findViewById(R.id.lblNombreComanda)).setText(nomCom);
                                        finish();
                                        startActivity(getIntent());
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(), "Ya existe una comanda con ese nombre", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Una comanda no puede tener el nombre en blanco", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setCancelable(true).create().show();
                return true;
            case R.id.OpcBorrar:
                LogicaNegocio.getInstancia().borraComanda(this, nombreComandaSeleccionada);
                finish();
                startActivity(getIntent());
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
