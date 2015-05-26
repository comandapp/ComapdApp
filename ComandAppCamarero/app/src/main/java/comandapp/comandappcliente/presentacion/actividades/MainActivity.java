package comandapp.comandappcliente.presentacion.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import comandapp.comandappcliente.R;
import comandapp.comandappcliente.logicanegocio.objetos.Bar;


public class MainActivity extends ActionBarActivity {

    private EditText txtNombre;
    private EditText txtPass;
    private Bar bar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        Button btnLogear = (Button)findViewById(R.id.btnLoguear);
        txtNombre = (EditText)findViewById(R.id.txtUsuario);
        txtPass = (EditText)findViewById(R.id.txtPass);

        btnLogear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pedir al servidor con el nombre de usuario y contraseña, el bar donde trabaja ese camarero:
                //bar = new Bar(1,"nombre","dire",12345,"aaa",12.123123,12.123123,"aaaa","bbb",0,0, true);
                bar = new Bar(1);

                Intent intent = new Intent(MainActivity.this, Carta_bar.class);
                intent.putExtra("id_bar",bar.getIdBar());
                startActivity(intent);
                /*if (txtNombre.getText().toString().equals("jesus") && txtPass.getText().toString().equals("miau")) {

                    startActivity(intent);
                }
                else
                {
                    new AlertDialog.Builder(LoginCamarero.this)
                            .setTitle("Prueba logueo")
                            .setMessage("Login incorrecto")
                            .setCancelable(true).create().show();
                }*/
            }
        });
    }


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
