package com.example.alumno.comandapp1;

import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TextView;


public class InicioBar extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_bar);

        anadirMenu();

        String nom="Nombre: "+getResources().getString(R.string.Nombre);
        String direc="Direccion: "+getResources().getString(R.string.Direccion);
        String localidad="Localidad: "+"Guadalupe";
        String municipio="Municipio: "+"AUAUAUA";
        String tlf="Telefono: "+getResources().getString(R.string.Telefono);
        String correo="Correo: " +"asdjasiod@correo.es";

        ((TextView)findViewById(R.id.textView1)).setText(nom);
        ((TextView)findViewById(R.id.textView2)).setText(tlf);
        ((TextView)findViewById(R.id.textView3)).setText(direc);
        ((TextView)findViewById(R.id.textView4)).setText(localidad);
        ((TextView)findViewById(R.id.textView5)).setText(municipio);
        ((TextView)findViewById(R.id.textView6)).setText(correo);
    }

    private void anadirMenu()
    {
        Resources res = getResources();

        TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("Inicio");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Inicio", res.getDrawable(android.R.drawable.ic_dialog_map));

        tabs.addTab(spec);

        spec=tabs.newTabSpec("Carta");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Carta", res.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);
        spec=tabs.newTabSpec("Ofertas");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Ofertas", res.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);
        tabs.setCurrentTab(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inicio_bar, menu);
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
