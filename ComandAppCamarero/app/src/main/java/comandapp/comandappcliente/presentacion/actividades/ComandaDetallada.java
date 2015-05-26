package comandapp.comandappcliente.presentacion.actividades;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.DecimalFormat;
import java.util.ArrayList;

import comandapp.comandappcliente.R;
import comandapp.comandappcliente.logicanegocio.LogicaNegocio;
import comandapp.comandappcliente.logicanegocio.objetos.LineaComanda;
import comandapp.comandappcliente.logicanegocio.objetos.LineaComandaEnCurso;
import comandapp.comandappcliente.logicanegocio.utilidades.LineaComandaEnCursoCodec;
import comandapp.comandappcliente.presentacion.adaptadores.AdaptadorComandaDetallada;

public class ComandaDetallada extends ActionBarActivity {

    ListView lstLineasComanda;
    AdaptadorComandaDetallada adaptador;
    ArrayList<LineaComanda> listadoLineasComanda;
    TextView lblPrecioFinal;
    String nombreComanda;
    int idBar;
    IntentResult scanResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda_detallada);

        idBar = LogicaNegocio.getInstancia().getAllBares(this).get(0).getIdBar();
        nombreComanda = this.getIntent().getExtras().getString("nombre_comanda");

        lstLineasComanda = (ListView)findViewById(R.id.listaDetalles);
        listadoLineasComanda = new ArrayList<LineaComanda>();

        rellenaVista();

        if(nombreComanda == null || nombreComanda.isEmpty()) {
            //--------------------------- BOTONES MENÚ ------------------------------------------------
            Button btnMenuCarta = (Button) findViewById(R.id.btnMenuCarta);
            Button btnMenuInicio = (Button) findViewById(R.id.btnMenuInicio);

            btnMenuCarta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ComandaDetallada.this, Carta_bar.class);
                    intent.putExtra("id_bar", idBar);
                    startActivity(intent);
                }
            });

            btnMenuInicio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ComandaDetallada.this, InicioBar.class);
                    intent.putExtra("id_bar", idBar);
                    startActivity(intent);
                }
            });


            //----------------------------------------------------------------------------------------------------------

            Button btnGuardar = (Button) findViewById(R.id.btnGuardarComanda);

            btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (listadoLineasComanda != null && listadoLineasComanda.size() > 0) {
                        final EditText txtNombreComanda = new EditText(ComandaDetallada.this);

                        new AlertDialog.Builder(ComandaDetallada.this)
                                .setTitle("Guardar comanda")
                                .setMessage("Introduce el nombre de la comanda a guardar:")
                                .setView(txtNombreComanda)
                                .setNegativeButton("Cancelar", null)
                                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String nomCom = txtNombreComanda.getText().toString();

                                        if(!nomCom.isEmpty())
                                        {
                                            if(!LogicaNegocio.getInstancia().existeComanda(ComandaDetallada.this, nomCom))
                                            {
                                                LogicaNegocio.getInstancia().insertaComanda(ComandaDetallada.this, nomCom, idBar);
                                                LogicaNegocio.getInstancia().insertaLineasComanda(ComandaDetallada.this, nomCom, listadoLineasComanda);
                                                Toast.makeText(getApplicationContext(), "Comanda guardada", Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                Toast.makeText(getApplicationContext(), "Ya existe una comanda con ese nombre", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(), "No puedes almacenar una comanda con el nombre en blanco", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                                .setCancelable(true).create().show();
                    } else {
                        Toast.makeText(getApplicationContext(), "No puedes almacenar una comanda en blanco", Toast.LENGTH_SHORT).show();
                        /*new AlertDialog.Builder(ComandaDetallada.this)
                                .setTitle("Aviso")
                                .setMessage("No puedes almacenar una comanda en blanco.")
                                .setCancelable(true).create().show();*/
                    }
                }
            });

            Button btnBorrar = (Button) findViewById(R.id.btnBorrar);

            btnBorrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(ComandaDetallada.this)
                            .setTitle("Borrar comanda en curso")
                            .setMessage("¿Está seguro de que desea borrar la comanda en curso?")
                            .setNegativeButton("No", null)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    LogicaNegocio.getInstancia().borraLineasComandaEnCurso(ComandaDetallada.this);
                                    listadoLineasComanda.clear();
                                    adaptador.notifyDataSetChanged();
                                    lblPrecioFinal.setText("0€");
                                }
                            })
                            .setCancelable(true).create().show();
                }
            });
        }

        /*Button btnPedidoQR = (Button)findViewById(R.id.btnQR);

        btnPedidoQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listadoLineasComanda != null && listadoLineasComanda.size() > 0)
                {
                    Intent intent = new Intent(ComandaDetallada.this, ComandaQR.class);
                    intent.putExtra("id_bar", id_Bar);
                    startActivity(intent);
                }
                else
                {
                    new AlertDialog.Builder(ComandaDetallada.this)
                            .setTitle("Aviso")
                            .setMessage("No hay ningún producto para generar la comanda.")
                            .setCancelable(true).create().show();
                }
            }
        });*/

        Button btnLectorQR = (Button)findViewById(R.id.btnLectorQR);

        btnLectorQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //############# MÉTODO 1:
                /*try {

                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes

                    startActivityForResult(intent, 0);

                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                }*/

                //############# MÉTODO 2:
                /*try
                {
                    //Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    //intent.putExtra("SCAN_FORMATS", "QR_CODE");
                    //intent.putExtra("SCAN_MODE", "SCAN_MODE");
                    //intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                    //startActivityForResult(intent, 0);
                    Intent intent = new Intent(ComandaDetallada.this, SimpleScannerActivity.class);
                    startActivity(intent);
                }
                catch (Exception e) {
                    Log.e("BARCODE_ERROR", e.getMessage());
                }*/

                ////############# MÉTODO 3:
                IntentIntegrator lectorQr = new IntentIntegrator(ComandaDetallada.this);
                lectorQr.setCaptureActivity(CaptureActivityAnyOrientation.class);
                lectorQr.setOrientationLocked(false);
                lectorQr.initiateScan();


                //https://github.com/journeyapps/zxing-android-embedded
                //http://www.vlone.es/index.php/tecnologia-curiosidades/40-software/201-escanear-codigos-de-barras-y-qr-en-android.html
                //http://jstyl8.blogspot.com.es/2011/10/como-anadir-libreria-zxing-tu-proyecto.html
            }
        });
    }

    private void rellenaVista()
    {
        if(nombreComanda == null || nombreComanda.isEmpty())
        {
            listadoLineasComanda = LogicaNegocio.getInstancia().lineasComandaEnCursoToLineasComanda(this, idBar);
        }
        else
        {
            LinearLayout laySubMenu = (LinearLayout) findViewById(R.id.LytSubMenu);
            LinearLayout layMenu = (LinearLayout) findViewById(R.id.LytMenu);
            laySubMenu.removeAllViews();
            layMenu.removeAllViews();
            listadoLineasComanda = LogicaNegocio.getInstancia().getComanda(this, nombreComanda).getLineasComanda();
        }

        if(listadoLineasComanda != null)
        {
            /*if(listadoLineasComanda.size() <= 6)
            {
                params.weight = 0f;
            }
            else
            {
                params.weight = 1.0f;
            }

            lstLineasComanda.setLayoutParams(params);*/

            adaptador = new AdaptadorComandaDetallada(this, listadoLineasComanda);



            //View header = getLayoutInflater().inflate(R.layout.listitem_comanda_header, null);
            //lstLineasComanda.addHeaderView(header);

            lstLineasComanda.setAdapter(adaptador);

            lblPrecioFinal = (TextView) findViewById(R.id.lblPrecioFinal);
            double precioFinal = 0;
            for(LineaComanda lin : listadoLineasComanda)
            {
                precioFinal += lin.getProductoCarta().getPrecio() * lin.getCantidad();
            }
            lblPrecioFinal.setText(new DecimalFormat("#.##").format(precioFinal) + "€");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comanda_detallada, menu);
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
            Intent intent = new Intent(ComandaDetallada.this, HistorialComandas.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    /*protected void onStop (){
        super.onStop();
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanResult != null)
        {
            String resultado = scanResult.getContents();

            if(resultado != null && !resultado.isEmpty())
            {
                ArrayList<LineaComandaEnCurso> lineasComandaEnCurso = LineaComandaEnCursoCodec.stringToLineasComandaEnCurso(resultado);

                if(lineasComandaEnCurso != null && lineasComandaEnCurso.size() > 0)
                {
                    nombreComanda = "";
                    getIntent().putExtra("nombre_comanda", "");
                    LogicaNegocio.getInstancia().borraLineasComandaEnCurso(ComandaDetallada.this);
                    LogicaNegocio.getInstancia().insertaLineasComandaEnCurso(ComandaDetallada.this,lineasComandaEnCurso);
                    rellenaVista();
                }
            }
        }
    }
}
