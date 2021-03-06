package comandapp.comandappcliente.presentacion.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import comandapp.comandappcliente.R;
import comandapp.comandappcliente.logicanegocio.LogicaNegocio;
import comandapp.comandappcliente.logicanegocio.objetos.Bar;
import comandapp.comandappcliente.logicanegocio.objetos.LineaCarta;
import comandapp.comandappcliente.logicanegocio.objetos.Oferta;
import comandapp.comandappcliente.presentacion.adaptadores.AdaptadorOfertas;


public class Ofertas_bar extends ActionBarActivity {
    private Bar bar=null;
    ArrayList<Oferta> lo=null;
    AdaptadorOfertas adaptador;
    ListView lstOfertas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ofertas_bar);

        final int id_Bar = this.getIntent().getExtras().getInt("id_bar");
        ArrayList<Oferta> ofertas = LogicaNegocio.getInstancia().getOfertas(this, id_Bar);

        if(ofertas.size() == 0) {
            //No hay ofertas. Mostrar mensaje de aviso
        } else {
            adaptador = new AdaptadorOfertas(this,ofertas);
            lstOfertas = (ListView) findViewById(R.id.lo);
            lstOfertas.setAdapter(adaptador);
        }

        Button btnMenuCarta = (Button)findViewById(R.id.btnMenuCarta);
        Button btnMenuInicio = (Button)findViewById(R.id.btnMenuInicio);
        Button btnMenuComanda = (Button)findViewById(R.id.btnMenuComanda);

        btnMenuCarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ofertas_bar.this, Carta_bar.class);
                intent.putExtra("id_bar",id_Bar);
                startActivity(intent);
            }
        });

        btnMenuInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ofertas_bar.this, InicioBar.class);
                intent.putExtra("id_bar",id_Bar);
                startActivity(intent);
            }
        });

        btnMenuComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ofertas_bar.this, ComandaDetallada.class);
                intent.putExtra("id_bar",id_Bar);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ofertas, menu);
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
        else if(id == R.id.menuHistorial)
        {
            Intent intent = new Intent(this, HistorialComandas.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
