package comandapp.comandappcliente.logicanegocio.objetos;

/**
 * Created by yoMismo on 30/03/2015.
 */
public class LineaComanda
{
    private String nombre;
    private Producto producto;
    private int cantidad;

    public LineaComanda(String nombre, Producto producto)
    {
        this.nombre = nombre;
        setProducto(producto);
        this.setCantidad(0);
    }

    public LineaComanda(String nombre, Producto producto, int cantidad)
    {
        this.nombre = nombre;
        setProducto(producto);
        this.setCantidad(cantidad);
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto prod) {
        this.producto = prod;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
