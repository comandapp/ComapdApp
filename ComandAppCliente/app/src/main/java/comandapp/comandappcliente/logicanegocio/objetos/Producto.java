package comandapp.comandappcliente.logicanegocio.objetos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Producto {
    
    private int id;
    private String nombre;
    private String categoria;
    private Bitmap foto;
    
    public Producto(int id, String nombre, String categoria, Bitmap foto){
        this.id=id;
        this.nombre=nombre;
        this.categoria=categoria;
        this.foto = foto;
    }
    
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public Bitmap getFoto() { return foto; }
}
