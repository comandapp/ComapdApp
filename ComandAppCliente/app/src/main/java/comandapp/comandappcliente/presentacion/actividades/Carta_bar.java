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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import comandapp.comandappcliente.R;
import comandapp.comandappcliente.logicanegocio.LogicaNegocio;
import comandapp.comandappcliente.logicanegocio.objetos.Bar;
import comandapp.comandappcliente.logicanegocio.objetos.Comanda;
import comandapp.comandappcliente.logicanegocio.objetos.LineaCarta;
import comandapp.comandappcliente.logicanegocio.objetos.LineaComanda;
import comandapp.comandappcliente.logicanegocio.objetos.Producto;
import comandapp.comandappcliente.presentacion.adaptadores.AdaptadorCarta;


public class Carta_bar extends ActionBarActivity {

    AdaptadorCarta adaptador;
    ListView lstEntradas;
    Bar bar=null;
    ArrayList<LineaComanda> lineasComanda;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carta_bar);

        final int id_Bar = this.getIntent().getExtras().getInt("id_bar");
        //Comanda c=LogicaNegocio.getInstancia().getComandaTemporal(this)
        //if(c==null)
        ArrayList<LineaCarta> carta = LogicaNegocio.getInstancia().getCarta(this,id_Bar);

        if(carta.size() == 0)
        {
            //No hay elementos en la carta !!! Mostrar mensaje al usuario
        }
        else
        {
            adaptador = new AdaptadorCarta(this, carta, LogicaNegocio.getInstancia().getOfertas(this,id_Bar));
            lstEntradas = (ListView)findViewById(R.id.listaCarta);
            lstEntradas.setAdapter(adaptador);
        }

        lineasComanda=new ArrayList<LineaComanda>();

        for(LineaCarta lineaCarta : carta)
        {
            lineasComanda.add(new LineaComanda(new LineaCarta(lineaCarta.getProducto(), lineaCarta.getPrecio(), lineaCarta.getDescripcion()), 0));
        }
        //else Si la comanda no es null
        /*
        Cargamos los objetos desde comanda a lComanda
         */

        Button btnMenuInicio = (Button)findViewById(R.id.btnMenuInicio);
        Button btnMenuOfertas = (Button)findViewById(R.id.btnMenuOfertas);
        Button btnMenuComanda = (Button)findViewById(R.id.btnMenuComanda);

        btnMenuInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Carta_bar.this, InicioBar.class);
                intent.putExtra("id_bar",id_Bar);

                guardaComanda();
                //MANDAR A PERSISTENCIA

                startActivity(intent);
            }
        });

        btnMenuOfertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Carta_bar.this, Ofertas_bar.class);
                intent.putExtra("id_bar",id_Bar);

                guardaComanda();
                //MANDAR A PERSISTENCIA

                startActivity(intent);
            }
        });

        btnMenuComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Carta_bar.this, ComandaDetallada.class);
                intent.putExtra("id_bar",id_Bar);
                guardaComanda();
                //MANDAR A PERSISTENCIA

                startActivity(intent);
            }
        });
    }

    private void guardaComanda()
    {
        for(int i=0; i < ((ListView)findViewById(R.id.listaCarta)).getChildCount(); i++)
        {
            View a = (RelativeLayout)((ListView)findViewById(R.id.listaCarta)).getChildAt(i).findViewById(R.id.layoutCartaGlobal);

            if(a instanceof RelativeLayout)
            {
                TextView tv = (TextView)((LinearLayout)((RelativeLayout)a).findViewById(R.id.layoutPrecios)).findViewById(R.id.but);
                LineaComanda lc = lineasComanda.get(i);
                lc.setCantidad(Integer.parseInt(tv.getText().toString()));
                lineasComanda.remove(i);
                lineasComanda.add(i, lc);
            }
        }
        for(int i=0; i < lineasComanda.size(); i++)
        {
            if(lineasComanda.get(i).getCantidad()==0)
                lineasComanda.remove(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_carta_bar, menu);
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
