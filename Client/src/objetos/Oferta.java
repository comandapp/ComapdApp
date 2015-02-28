package objetos;

public class Oferta {
    private int id_bar;
    private int id_producto;
    private double precio;
    public Oferta(int id_bar, int id_producto, double precio){
        this.id_bar=id_bar;
        this.id_producto=id_producto;
        this.precio=precio;
    }

    public int getId() {
        return id_bar;
    }

    public void setId(int id_bar) {
        this.id_bar = id_bar;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    
}
