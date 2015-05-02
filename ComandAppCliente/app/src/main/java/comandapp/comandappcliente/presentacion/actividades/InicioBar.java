package comandapp.comandappcliente.presentacion.actividades;

import android.app.AlertDialog;
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
                Intent intent = new Intent(InicioBar.this, Carta_bar.class);
                intent.putExtra("id_bar",id_Bar);
                startActivity(intent);
            }
        });
        Button btn2 = (Button)findViewById(R.id.button4);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioBar.this, Ofertas_bar.class);
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

        return super.onOptionsItemSelected(item);
    }
    public void clickBoton(View v)
    {

    }
}
