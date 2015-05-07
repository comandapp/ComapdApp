package comandapp.comandappcliente.presentacion.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import comandapp.comandappcliente.R;
import comandapp.comandappcliente.logicanegocio.objetos.Bar;
import comandapp.comandappcliente.logicanegocio.objetos.Comanda;
import comandapp.comandappcliente.logicanegocio.objetos.LineaCarta;
import comandapp.comandappcliente.logicanegocio.objetos.LineaComanda;
import comandapp.comandappcliente.logicanegocio.objetos.Producto;
import comandapp.comandappcliente.presentacion.adaptadores.AdaptadorComandaDetallada;

public class ComandaDetallada extends ActionBarActivity {

    ListView lstLineasComanda;
    AdaptadorComandaDetallada adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda_detallada);

        final int id_Bar = this.getIntent().getExtras().getInt("id_bar");

        /*
        ArrayList<LineaComanda> listadoLineasComanda = new ArrayList<LineaComanda>();
        listadoLineasComanda.add(new LineaComanda(new LineaCarta(new Producto(1, "Coca Cola"), 2.5), 3));
        listadoLineasComanda.add(new LineaComanda(new LineaCarta(new Producto(2, "7 UP"), 2.8), 1));
        listadoLineasComanda.add(new LineaComanda(new LineaCarta(new Producto(3, "Cerveza Franciscaner"), 4), 2));
        listadoLineasComanda.add(new LineaComanda(new LineaCarta(new Producto(4, "Combinado Barceló - Coca Cola"), 6), 1));
        listadoLineasComanda.add(new LineaComanda(new LineaCarta(new Producto(5, "Batido chocolate"), 2), 1));
        listadoLineasComanda.add(new LineaComanda(new LineaCarta(new Producto(6, "Fanta naranja"), 2.5), 3));
        listadoLineasComanda.add(new LineaComanda(new LineaCarta(new Producto(7, "Combinado Absolut - Tónica"), 6), 1));
        listadoLineasComanda.add(new LineaComanda(new LineaCarta(new Producto(8, "Bolsa de patatas"), 1.2), 2));

        Comanda comanda = new Comanda(new Bar(1, "Café Madrid"), listadoLineasComanda);


        ListView lv = (ListView)findViewById(R.id.listaDetalles);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lv.getLayoutParams();

        if(comanda.getNumeroLineas() > 6)
        {
            params.weight = 1.0f;
        }
        else
        {
            params.weight = 0f;
        }

        lv.setLayoutParams(params);

        adaptador = new AdaptadorComandaDetallada(this, comanda.getLineasComanda());

        lstLineasComanda = (ListView)findViewById(R.id.listaDetalles);

        //View header = getLayoutInflater().inflate(R.layout.listitem_comanda_header, null);
        //lstLineasComanda.addHeaderView(header);

        lstLineasComanda.setAdapter(adaptador);

        TextView lblPrecioFinal = (TextView) findViewById(R.id.lblPrecioFinal);
        lblPrecioFinal.setText("€" + String.format("%.2f", comanda.getPrecioTotal()));

        Button btnPedidoQR = (Button)findViewById(R.id.btnQR);

        btnPedidoQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComandaDetallada.this, ComandaQR.class);
                startActivity(intent);
            }
        });
        ----------------------------------------------------------------------------------------------------------*/

        Button btnMenuCarta = (Button)findViewById(R.id.btnMenuCarta);
        Button btnMenuInicio = (Button)findViewById(R.id.btnMenuInicio);
        Button btnMenuOferta = (Button)findViewById(R.id.btnMenuOfertas);

        btnMenuCarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComandaDetallada.this, Carta_bar.class);
                intent.putExtra("id_bar",id_Bar);
                startActivity(intent);
            }
        });

        btnMenuInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComandaDetallada.this, InicioBar.class);
                intent.putExtra("id_bar",id_Bar);
                startActivity(intent);
            }
        });

        btnMenuOferta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComandaDetallada.this, Ofertas_bar.class);

                intent.putExtra("id_bar",id_Bar);

                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comanda_detallada, menu);
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
