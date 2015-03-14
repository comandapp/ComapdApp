package comandapp.comandappuser.presentacion;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import comandapp.comandappuser.objetos.Bar;
import comandapp.comandappuser.persistencia.Persistencia;
import comandapp.comandappuser.persistencia.Utilities;
import comandapp.comandappuser.presentacion.adaptadores.ListaBaresAdapter;
import comandapp.comandappuser.R;

public class ListaBares extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_bares);

        final Context c = this;

        Persistencia p = Persistencia.getInstancia(this);

        ListView listViewBares = (ListView) findViewById(R.id.listViewBares);
        ArrayList<Bar> bares = p.getBares(null, 0); //Bares huecos

        if (bares.size() == 0) {
            listViewBares.setVisibility(ListView.GONE);
        } else {
            listViewBares.setVisibility(ListView.VISIBLE);


            ListaBaresAdapter adaptador = new ListaBaresAdapter(this,bares);

            listViewBares.setAdapter(adaptador);
            Utilities.setListViewHeightBasedOnChildren(listViewBares);

            listViewBares.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                    // TODO Auto-generated method stub
//                Intent vehiculo = new Intent(Proyecto1Form.this, Proyecto1Vehiculo1.class);
//                vehiculo.putExtra("Inicio", 0);
//                vehiculo.putExtra("id_form",id_form);
//                vehiculo.putExtra("id_vehiculo", (int)arg3);
//                startActivity(vehiculo);
                    Toast.makeText(c, "Hola", Toast.LENGTH_LONG).show();
                }
            });
        }
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
