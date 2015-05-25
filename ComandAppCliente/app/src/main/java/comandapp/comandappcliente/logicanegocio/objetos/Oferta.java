package comandapp.comandappcliente.logicanegocio.objetos;

public class Oferta {

    private Producto producto;
    private int id_bar;
    private String descripcion;
    private double precio;

    public Oferta(int id_bar, Producto prod, String descripcion, double precio){
        this.id_bar=id_bar;
        this.producto = prod;
        this.precio=precio;
        this.descripcion=descripcion;
    }

    public int getIdBar() {
        return id_bar;
    }

    public Producto getProducto() {
        return producto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion(){return descripcion;}

    public void setDescripcion(String descripcion){this.descripcion=descripcion;}

}

