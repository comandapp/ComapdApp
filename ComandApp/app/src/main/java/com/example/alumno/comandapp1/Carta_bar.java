package com.example.alumno.comandapp1;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class Carta_bar extends ActionBarActivity {
    ArrayList<Entrada> le=null;
    AdaptadorCarta adaptador;
    ListView lstEntradas;
    Bar bar=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carta_bar);

        bar=(Bar)this.getIntent().getExtras().getParcelable("bar");

        le=new ArrayList<Entrada>();
        le.add(new Entrada(new Producto(1,"patatas","comida"),12,"descrip",null));
        le.add(new Entrada(new Producto(2,"pats","comidsa"),12,"descrqqqqip",null));
        le.add(new Entrada(new Producto(3,"patas","comifda"),12,"descrrtip",null));
        le.add(new Entrada(new Producto(4,"patats","cogmida"),12,"deschrtripdeschrtripdeschrtripdeschrtripdeschrtripdeschrtripdeschrtripdeschrtripdeschrtripdeschrtripdeschrtripdeschrtrip",null));

        adaptador = new AdaptadorCarta(this, le);

        lstEntradas = (ListView)findViewById(R.id.ll);
        lstEntradas.setAdapter(adaptador);

        Button btn = (Button)findViewById(R.id.button2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos el Intent
                Intent intent = new Intent(Carta_bar.this, InicioBar.class);
                intent.putExtra("bar",bar);
                //Iniciamos la nueva actividad
                startActivity(intent);
            }
        });
        Button btn2 = (Button)findViewById(R.id.button4);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos el Intent
                Intent intent = new Intent(Carta_bar.this, Ofertas_bar.class);
                intent.putExtra("bar",bar);
                //Iniciamos la nueva actividad
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