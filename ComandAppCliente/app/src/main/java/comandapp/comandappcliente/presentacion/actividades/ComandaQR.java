package comandapp.comandappcliente.presentacion.actividades;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.ArrayList;

import comandapp.comandappcliente.R;
import comandapp.comandappcliente.logicanegocio.LogicaNegocio;
import comandapp.comandappcliente.logicanegocio.objetos.LineaComanda;
import comandapp.comandappcliente.logicanegocio.objetos.LineaComandaEnCurso;
import comandapp.comandappcliente.logicanegocio.utilidades.LineaComandaEnCursoCodec;


public class ComandaQR extends ActionBarActivity {

    ImageView imgQR;
    ArrayList<LineaComandaEnCurso> lineasComandaEnCurso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda_qr);

        final int id_Bar = this.getIntent().getExtras().getInt("id_bar");
        final String nombreComanda = this.getIntent().getExtras().getString("nombre_comanda");

        //--------------------------- BOTONES MENU ------------------------------------------------
        Button btnMenuCarta = (Button) findViewById(R.id.btnMenuCarta);
        Button btnMenuInicio = (Button) findViewById(R.id.btnMenuInicio);

        btnMenuCarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComandaQR.this, Carta_bar.class);
                intent.putExtra("id_bar", id_Bar);
                startActivity(intent);
            }
        });

        btnMenuInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComandaQR.this, InicioBar.class);
                intent.putExtra("id_bar", id_Bar);
                startActivity(intent);
            }
        });


        //----------------------------------------------------------------------------------------------------------

        lineasComandaEnCurso = new ArrayList<LineaComandaEnCurso>();
        if(nombreComanda == null || nombreComanda.isEmpty())
        {
            lineasComandaEnCurso = LogicaNegocio.getInstancia().getLineasComandaEnCurso(this);
        }
        else
        {
            LinearLayout laySubMenu = (LinearLayout) findViewById(R.id.LytSubMenu);
            LinearLayout layMenu = (LinearLayout) findViewById(R.id.LytMenu);
            laySubMenu.removeAllViews();
            layMenu.removeAllViews();
            lineasComandaEnCurso = LogicaNegocio.getInstancia().lineasComandaToLineasComandaEnCurso(this, LogicaNegocio.getInstancia().getComanda(this, nombreComanda).getLineasComanda());
        }

        /*Gson gson = new Gson();
        String json = gson.toJson(lineasComandaEnCurso);
        Log.w("------->", json);
        ArrayList<LineaComandaEnCurso> obj2 = gson.fromJson(json, new TypeToken<ArrayList<LineaComandaEnCurso>>(){}.getType());
        Log.w(lineasComandaEnCurso.size() + "------->", obj2.size() + "");*/

        String lineasCodificadas = LineaComandaEnCursoCodec.lineasComandaEnCursoToString(lineasComandaEnCurso);
        //Log.w("------->", lineasCodificadas);
        //ArrayList<LineaComandaEnCurso> lineasDecodificadas = LineaComandaEnCursoCodec.stringToLineasComandaEnCurso(lineasCodificadas);
        //Log.w(lineasComandaEnCurso.size() + "------->", lineasDecodificadas.size() + "");

        imgQR = (ImageView)findViewById(R.id.imgQR);
        generarQRCode(imgQR, lineasCodificadas);

        Button btnDetalles = (Button)findViewById(R.id.btnDetalles);

        btnDetalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComandaQR.this, ComandaDetallada.class);
                intent.putExtra("id_bar", id_Bar);
                startActivity(intent);
            }
        });
        //http://androideity.com/2011/11/23/trabajar-con-codigos-qr-en-tus-aplicaciones-android/
    }

    private void generarQRCode(ImageView imgQR, String texto)
    {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(texto, BarcodeFormat.QR_CODE, 300, 300);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            imgQR.setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_genera_comanda, menu);
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
}
