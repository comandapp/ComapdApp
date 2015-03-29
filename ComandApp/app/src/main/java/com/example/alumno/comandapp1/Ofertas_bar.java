package com.example.alumno.comandapp1;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class Ofertas_bar extends ActionBarActivity {
    private Bar bar=null;
    ArrayList<Oferta> lo=null;
    AdaptadorOfertas adaptador;
    ListView lstOfertas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ofertas_bar);
        lo=new ArrayList<Oferta>();
        lo.add(new Oferta(1,2,12.5,"patatas","muuuuchas patatas"));
        lo.add(new Oferta(2,3,20.5,"Kebab","asdasfdasdf"));
        lo.add(new Oferta(3,2,52.5,"rrrrrrrrrrrr","muuuuchas rrrrrrrrrrrrrrrrr"));
        lo.add(new Oferta(4,4,72.5,"patttttttttttt","muuuuchas tttttttttttttt"));
        adaptador = new AdaptadorOfertas(this, lo);

        lstOfertas = (ListView)findViewById(R.id.lo);
        lstOfertas.setAdapter(adaptador);
        Button btn = (Button)findViewById(R.id.button3);
        bar=(Bar)this.getIntent().getExtras().getParcelable("bar");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos el Intent
                Intent intent = new Intent(Ofertas_bar.this, Carta_bar.class);
                intent.putExtra("bar",bar);
                //Iniciamos la nueva actividad
                startActivity(intent);
            }
        });
        Button btn2 = (Button)findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos el Intent
                Intent intent = new Intent(Ofertas_bar.this, InicioBar.class);
                intent.putExtra("bar",bar);
                //Iniciamos la nueva actividad
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

        return super.onOptionsItemSelected(item);
    }
}
