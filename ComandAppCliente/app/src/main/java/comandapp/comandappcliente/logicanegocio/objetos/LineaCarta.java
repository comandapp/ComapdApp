/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package comandapp.comandappcliente.logicanegocio.objetos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

/**
 *
 * @author G62
 */
public class LineaCarta {
 
    private Producto producto;
    private double precio;
    private String descripcion;
    private Bitmap foto;
    
    public LineaCarta(Producto producto, double precio, String descripcion, String foto) {
        this.producto = producto;
        this.precio = precio;
        this.descripcion=descripcion;
        byte[] decodedString = Base64.decode(foto, Base64.URL_SAFE);
        this.foto = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
    
    public Producto getProducto() {
        return producto;
    }

    public double getPrecio() {
        return precio;
    }

    public Bitmap getFoto() {
        return foto;
    }
    
    public String getDescripcion() {
        return descripcion;
    }    
}


