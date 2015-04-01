package comandapp.comandappuser.objetos;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Oferta {
    
    private int id;
    private ArrayList<Producto> productos;
    private double precio;
    private String descripcion;
    private Bitmap foto;
    
    public Oferta(int id, double precio, String descripcion, Bitmap foto){
        this.id = id;
        this.productos = new ArrayList<Producto>();
        this.precio = precio;
        this.descripcion = descripcion;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }
    
    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public double getPrecio() {
        return precio;
    }

    public String getDescripcion() { return descripcion; }

    public Bitmap getFoto() {
        return foto;
    }
}
