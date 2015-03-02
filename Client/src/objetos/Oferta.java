package objetos;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Oferta {
    
    private ArrayList<Producto> productos;
    private double precio;
    private BufferedImage foto;
    
    public Oferta(double precio, BufferedImage foto){
        this.productos = new ArrayList<Producto>();
        this.precio = precio;
        this.foto = foto;
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
