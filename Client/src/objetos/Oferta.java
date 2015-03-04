package objetos;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Oferta {
    
    private int id;
    private ArrayList<Producto> productos;
    private double precio;
    private BufferedImage foto;
    
    public Oferta(int id, double precio, BufferedImage foto){
        this.id = id;
        this.productos = new ArrayList<Producto>();
        this.precio = precio;
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

    public BufferedImage getFoto() {
        return foto;
    }
}
