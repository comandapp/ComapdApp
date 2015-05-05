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
    ArrayList<LineaComanda> lComanda;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carta_bar);

        final int id_Bar = this.getIntent().getExtras().getInt("id_bar");
        ArrayList<LineaCarta> carta = LogicaNegocio.getInstancia().getCarta(this,id_Bar);

        if(carta.size() == 0) {
            //No hay elementos en la carta !!! Mostrar mensaje al usuario
        } else {
            adaptador = new AdaptadorCarta(this, carta, LogicaNegocio.getInstancia().getOfertas(this,id_Bar));
            lstEntradas = (ListView)findViewById(R.id.ll);
            lstEntradas.setAdapter(adaptador);
        }


        Button btn = (Button)findViewById(R.id.button2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Carta_bar.this, InicioBar.class);
                intent.putExtra("id_bar",id_Bar);
                startActivity(intent);
            }
        });
        Button btn2 = (Button)findViewById(R.id.button4);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Carta_bar.this, Ofertas_bar.class);
                intent.putExtra("id_bar",id_Bar);
                startActivity(intent);
            }
        });

        lComanda=new ArrayList<LineaComanda>();
        for(LineaCarta lC:carta){
            lComanda.add(new LineaComanda(new LineaCarta(lC.getProducto(),lC.getPrecio(), lC.getDescripcion()),0));
        }
        Button but=(Button)this.findViewById(R.id.btnCarta);
        but.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Carta_bar.this, ComandaDetallada.class);
                for(int i=0;i<((ListView)findViewById(R.id.ll)).getChildCount();i++) {
                    View a=(RelativeLayout)((ListView)findViewById(R.id.ll)).getChildAt(i).findViewById(R.id.LLayoutCarta);
                    if(a instanceof RelativeLayout) {
                        TextView tv=(TextView)((LinearLayout)((RelativeLayout)a).findViewById(R.id.linearLayout)).findViewById(R.id.but);
                        LineaComanda lc=lComanda.get(i);
                        lc.setCantidad(Integer.parseInt(tv.getText().toString()));
                        lComanda.remove(i);
                        lComanda.add(i,lc);

                    }
                }
                for(int i=0;i<lComanda.size();i++){
                    if(lComanda.get(i).getCantidad()==0)
                        lComanda.remove(i);
                }
                Comanda c=new Comanda(bar,lComanda);
                //MANDAR A PERSISTENCIA
                //
                //
                //
                //
                //
                startActivity(intent);
            }
        });
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
