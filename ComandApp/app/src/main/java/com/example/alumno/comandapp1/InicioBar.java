package com.example.alumno.comandapp1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;


public class InicioBar extends ActionBarActivity {
    private Bar bar=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_bar);
        bar=(Bar)this.getIntent().getExtras().getParcelable("bar");

        String nom="Nombre: "+bar.getNombre();
        String direc="Direccion: "+bar.getDireccion();
        String localidad="Localidad: "+bar.getProvincia();
        String municipio="Municipio: "+bar.getMunicipio();
        String tlf="Telefono: "+bar.getTelefono();
        String correo="Correo: " +bar.getCorreo();

        ((TextView)findViewById(R.id.textView1)).setText(nom);
        ((TextView)findViewById(R.id.textView2)).setText(tlf);
        ((TextView)findViewById(R.id.textView3)).setText(direc);
        ((TextView)findViewById(R.id.textView4)).setText(localidad);
        ((TextView)findViewById(R.id.textView5)).setText(municipio);
        ((TextView)findViewById(R.id.textView6)).setText(correo);
        Button btn = (Button)findViewById(R.id.button3);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos el Intent
                Intent intent = new Intent(InicioBar.this, Carta_bar.class);
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
                Intent intent = new Intent(InicioBar.this, Ofertas_bar.class);
                intent.putExtra("bar",bar);
                //Iniciamos la nueva actividad
                startActivity(intent);
            }
        });
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
    public void clickBoton(View v)
    {
        final Intent intent = new Intent(InicioBar.this, MapsActivityBar.class);
        Bundle b=new Bundle();

        b.putString("nombre",bar.getNombre());
        b.putDouble("latitud",bar.getLatitud());
       b.putDouble("longitud",bar.getLongitud());
        intent.putExtras(b);

        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

// check if enabled and if not send user to the GSP settings
// Better solution would be to display a dialog and suggesting to
// go to the settings
        if (!enabled) {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(InicioBar.this);
            dlgAlert.setMessage("Es necesario tener los servicios de localización activados, se le redireccionara a dicho lugar.");
            dlgAlert.setTitle("Aviso");
            dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent2 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                    startActivity(intent2);
                }
                ;
            });
            dlgAlert.setCancelable(false);
            dlgAlert.create().show();
        }else{
            startActivity(intent);
        }
        //Iniciamos la nueva actividad

    }
}
