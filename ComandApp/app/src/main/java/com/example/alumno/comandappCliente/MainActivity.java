package com.example.alumno.comandappCliente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button btnBuscarLocal;
    private Button btnLocalizame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBuscarLocal = (Button)findViewById(R.id.btnBuscarLocal);

        btnBuscarLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos el Intent
                Intent intent = new Intent(MainActivity.this, BusquedaBar.class);

                //Iniciamos la nueva actividad
                startActivity(intent);
            }
        });

            btnLocalizame=(Button)findViewById(R.id.btnLocalizame);
        btnLocalizame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos el Intent
                final Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
                boolean enabled = service
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);

// check if enabled and if not send user to the GSP settings
// Better solution would be to display a dialog and suggesting to
// go to the settings
                Bar[] lb=new Bar[5];
                lb[0]=new Bar(1,"nombre","dire",12345,"aaa",12.123123,12.123123,"aaaa","bbb",0,0, true);
                lb[1]=new Bar(1,"nombre2","dire",12345,"aaa",22.123123,22.123123,"aaaa","bbb",0,0, false);
                lb[2]=new Bar(1,"nombre3","dire",12345,"aaa",32.123123,32.123123,"aaaa","bbb",0,0, true);
                lb[3]=new Bar(1,"nombre4","dire",12345,"aaa",42.123123,52.123123,"aaaa","bbb",0,0, true);
                lb[4]=new Bar(1,"nombre5","dire",12345,"aaa",52.123123,62.123123,"aaaa","bbb",0,0, false);
                intent.putExtra("listabar",lb);
                if (!enabled) {
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(MainActivity.this);
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
        });

        //Obtenemos una referencia a los controles de la interfaz
        /*txtNombre = (EditText)findViewById(R.id.TxtNombre);
        btnAceptar = (Button)findViewById(R.id.BtnAceptar);

        //Implementamos el evento click del botón
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos el Intent
                Intent intent = new Intent(MainActivity.this, SaludoActivity.class);

                //Creamos la información a pasar entre actividades
                Bundle b = new Bundle();
                b.putString("NOMBRE", txtNombre.getText().toString());

                //Añadimos la información al intent
                intent.putExtras(b);

                //Iniciamos la nueva actividad
                startActivity(intent);
            }
        });*/
    }

    /*public void clickBoton(View v)
    {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Hola")
                .setMessage("Hola Jesusito")
                .setCancelable(true).create().show();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
