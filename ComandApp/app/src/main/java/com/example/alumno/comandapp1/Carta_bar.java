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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class Carta_bar extends ActionBarActivity {
    private Bar bar=null;
    List<Entrada> le=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carta_bar);

        bar=(Bar)this.getIntent().getExtras().getParcelable("bar");
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

        le=new ArrayList<Entrada>();
        le.add(new Entrada(new Producto(1,"patatas","comida"),12,"descrip",null));
        le.add(new Entrada(new Producto(2,"pats","comidsa"),12,"descrqqqqip",null));
        le.add(new Entrada(new Producto(3,"patas","comifda"),12,"descrrtip",null));
        le.add(new Entrada(new Producto(4,"patats","cogmida"),12,"deschrtrip",null));
        cargaCarta(le);
    }

    public void cargaCarta(List<Entrada> le){
        LinearLayout ll=null;
        TextView tv=null;
        View v=null;
        for(int i=0;i<le.size();i++){
            ll=(LinearLayout)findViewById(R.id.ll);
            tv=new TextView(this);
            tv.setText("Nombre: "+le.get(i).getProducto().getNombre()+", Precio: "+le.get(i).getPrecio());
            ll.addView(tv);
            v=new View(this);
            RelativeLayout.LayoutParams rl=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,1);
            rl.setMargins(0,2,0,0);
            v.setLayoutParams(rl);
            ll.addView(v);
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
