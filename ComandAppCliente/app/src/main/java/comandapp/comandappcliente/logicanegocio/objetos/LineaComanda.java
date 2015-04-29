package comandapp.comandappcliente.logicanegocio.objetos;

/**
 * Created by yoMismo on 30/03/2015.
 */
public class LineaComanda
{
    private Linea_Carta entradaProd;
    private int cantidad;

    public LineaComanda(Linea_Carta entrada)
    {
        setEntradaProd(entrada);
        this.setCantidad(0);
    }

    public LineaComanda(Linea_Carta entrada, int cantidad)
    {
        setEntradaProd(entrada);
        this.setCantidad(cantidad);
    }

    public Linea_Carta getEntradaProd() {
        return entradaProd;
    }

    public void setEntradaProd(Linea_Carta entradaProd) {
        this.entradaProd = entradaProd;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
