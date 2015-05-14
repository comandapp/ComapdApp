package comandapp.comandappcliente.presentacion.actividades;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import comandapp.comandappcliente.R;
import comandapp.comandappcliente.logicanegocio.LogicaNegocio;
import comandapp.comandappcliente.logicanegocio.objetos.Bar;
import comandapp.comandappcliente.logicanegocio.objetos.Comanda;
import comandapp.comandappcliente.logicanegocio.objetos.LineaCarta;
import comandapp.comandappcliente.logicanegocio.objetos.LineaComanda;
import comandapp.comandappcliente.logicanegocio.objetos.Producto;
import comandapp.comandappcliente.presentacion.adaptadores.AdaptadorComandaDetallada;

public class ComandaDetallada extends ActionBarActivity {

    ListView lstLineasComanda;
    AdaptadorComandaDetallada adaptador;
    ArrayList<LineaComanda> listadoLineasComanda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda_detallada);

        final int id_Bar = this.getIntent().getExtras().getInt("id_bar");

        ListView lv = (ListView)findViewById(R.id.listaDetalles);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lv.getLayoutParams();

        listadoLineasComanda = new ArrayList<LineaComanda>();
        listadoLineasComanda = LogicaNegocio.getInstancia().lineasComandaEnCursoToLineasComanda(this, id_Bar);

        if(listadoLineasComanda != null)
        {
            if(listadoLineasComanda.size() <= 6)
            {
                params.weight = 0f;
            }
            else
            {
                params.weight = 1.0f;
            }

            lv.setLayoutParams(params);

            adaptador = new AdaptadorComandaDetallada(this, listadoLineasComanda);

            lstLineasComanda = (ListView)findViewById(R.id.listaDetalles);

            //View header = getLayoutInflater().inflate(R.layout.listitem_comanda_header, null);
            //lstLineasComanda.addHeaderView(header);

            lstLineasComanda.setAdapter(adaptador);

            TextView lblPrecioFinal = (TextView) findViewById(R.id.lblPrecioFinal);
            double precioFinal = 0;
            for(LineaComanda lin : listadoLineasComanda)
            {
                precioFinal += lin.getProductoCarta().getPrecio() * lin.getCantidad();
            }
            lblPrecioFinal.setText(new DecimalFormat("#.##").format(precioFinal) + "€");
        }

        Button btnPedidoQR = (Button)findViewById(R.id.btnQR);

        btnPedidoQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listadoLineasComanda != null)
                {
                    Intent intent = new Intent(ComandaDetallada.this, ComandaQR.class);
                    startActivity(intent);
                }
                else
                {
                    new AlertDialog.Builder(ComandaDetallada.this)
                            .setTitle("Aviso")
                            .setMessage("No hay ningún producto para generar la comanda.")
                            .setCancelable(true).create().show();
                }
            }
        });

        //----------------------------------------------------------------------------------------------------------

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

        Button btnHistorial = (Button)findViewById(R.id.btnHistorial);

        btnHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComandaDetallada.this, HistorialComandas.class);
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

    protected void onStop (){
        super.onStop();
        //Log.w("---------------->", "Cerrando ComandaDetallada");
    }
}
