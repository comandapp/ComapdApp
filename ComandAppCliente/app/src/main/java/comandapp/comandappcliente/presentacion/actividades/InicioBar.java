package comandapp.comandappcliente.presentacion.actividades;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import comandapp.comandappcliente.R;
import comandapp.comandappcliente.logicanegocio.LogicaNegocio;
import comandapp.comandappcliente.logicanegocio.objetos.Bar;



public class InicioBar extends ActionBarActivity {

    private Bar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_bar);

        final int id_Bar = this.getIntent().getExtras().getInt("id_bar");
        bar = LogicaNegocio.getInstancia().getBarHueco(this,id_Bar);

        String nom="Nombre: "+bar.getNombre();
        String direc="Direccion: "+bar.getDireccion();
        String localidad="Localidad: "+bar.getProvincia();
        String municipio="Municipio: "+bar.getMunicipio();
        String tlf="Telefono: "+bar.getTelefono();
        String correo="Correo: " +bar.getCorreo();

        ((ImageView)findViewById(R.id.imageViewBar)).setImageBitmap(bar.getFoto());
        ((TextView)findViewById(R.id.textView1)).setText(nom);
        ((TextView)findViewById(R.id.textView2)).setText(tlf);
        ((TextView)findViewById(R.id.textView3)).setText(direc);
        ((TextView)findViewById(R.id.textView4)).setText(localidad);
        ((TextView)findViewById(R.id.textView5)).setText(municipio);
        ((TextView)findViewById(R.id.textView6)).setText(correo);
        Switch fav = (Switch)findViewById(R.id.SwitchFavorito);
        fav.setChecked(bar.getFavorito());

        final Context con = this;

        fav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LogicaNegocio.getInstancia().setBarFavorito(con,bar,isChecked);
            }
        });

        Button btnMenuCarta = (Button)findViewById(R.id.btnMenuCarta);
        Button btnMenuOfertas = (Button)findViewById(R.id.btnMenuOfertas);
        Button btnMenuComanda = (Button)findViewById(R.id.btnMenuComanda);

        btnMenuCarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioBar.this, Carta_bar.class);
                intent.putExtra("id_bar",id_Bar);
                startActivity(intent);
            }
        });

        btnMenuOfertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioBar.this, Ofertas_bar.class);
                intent.putExtra("id_bar",id_Bar);
                startActivity(intent);
            }
        });

        btnMenuComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioBar.this, ComandaDetallada.class);
                intent.putExtra("id_bar",id_Bar);
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
        else if(id == R.id.menuHistorial)
        {
            Intent intent = new Intent(this, HistorialComandas.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    public void clickBoton(View v)
    {
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        final Intent intent = new Intent(InicioBar.this, MapsActivityBar.class);
        
        intent.putExtra("bar",bar.getIdBar());
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
    }
}
