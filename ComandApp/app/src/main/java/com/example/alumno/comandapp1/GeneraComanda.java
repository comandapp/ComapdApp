package com.example.alumno.comandapp1;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;


public class GeneraComanda extends ActionBarActivity {

    ImageView imgQR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genera_comanda);

        imgQR = (ImageView)findViewById(R.id.imgQR);

        //http://androideity.com/2011/11/23/trabajar-con-codigos-qr-en-tus-aplicaciones-android/
        Bitmap bm = encodeAsBitmap("Hola hola! no vengas sola!", BarcodeFormat.QR_CODE, 200, 200);

        if(bm != null) {
            imgQR.setImageBitmap(bm);
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

        return super.onOptionsItemSelected(item);
    }
}
