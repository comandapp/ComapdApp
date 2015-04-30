package comandapp.comandappcliente.logicanegocio.objetos;

/**
 * Created by yoMismo on 30/03/2015.
 */
public class LineaComanda
{
    private LineaCarta entradaProd;
    private int cantidad;

    public LineaComanda(LineaCarta entrada)
    {
        setEntradaProd(entrada);
        this.setCantidad(0);
    }

    public LineaComanda(LineaCarta entrada, int cantidad)
    {
        setEntradaProd(entrada);
        this.setCantidad(cantidad);
    }

    public LineaCarta getEntradaProd() {
        return entradaProd;
    }

    public void setEntradaProd(LineaCarta entradaProd) {
        this.entradaProd = entradaProd;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
