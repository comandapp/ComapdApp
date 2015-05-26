package comandapp.comandappcliente.logicanegocio.utilidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by G62 on 14-Mar-15.
 */

//Codec para las imágenes.
//Convierte de png a cadena en base 64 y viceversa.
public class ImgCodec {
    public static Bitmap base64ToBitmap(String b64) {
        byte[] decodedString = Base64.decode(b64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
    public static String bitmapToBase64(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return Base64.encodeToString(baos.toByteArray(),Base64.DEFAULT);
    }
}
