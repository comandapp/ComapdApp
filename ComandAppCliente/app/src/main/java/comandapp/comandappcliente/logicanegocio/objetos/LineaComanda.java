package comandapp.comandappcliente.logicanegocio.objetos;

/**
 * Created by yoMismo on 30/03/2015.
 */
public class LineaComanda
{
    private LineaCarta productoCarta;
    private int cantidad;
    private LineaCarta lineaCarta;

    public LineaComanda(String nombre, Producto producto)
    {
        setProductoCarta(productoCarta);
        this.setCantidad(0);
    }

    public LineaComanda(LineaCarta productoCarta, int cantidad)
    {
        setProductoCarta(productoCarta);
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


    public LineaCarta getProductoCarta() {
        return productoCarta;
    }

    public void setProductoCarta(LineaCarta prod) {
        this.productoCarta = prod;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
