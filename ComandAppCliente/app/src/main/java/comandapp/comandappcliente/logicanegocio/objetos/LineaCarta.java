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
    
    public LineaCarta(Producto producto, double precio, String descripcion) {
        this.producto = producto;
        this.precio = precio;
        this.descripcion=descripcion;
    }
    
    public Producto getProducto() {
        return producto;
    }

    public double getPrecio() {
        return precio;
    }
    
    public String getDescripcion() {
        return descripcion;
    }    
}


