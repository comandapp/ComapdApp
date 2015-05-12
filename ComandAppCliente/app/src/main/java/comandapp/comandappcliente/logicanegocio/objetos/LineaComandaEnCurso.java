package comandapp.comandappcliente.logicanegocio.objetos;

/**
 * Created by yoMismo on 12/05/2015.
 */
public class LineaComandaEnCurso {

    private int IdProducto;
    private int cantidad;

    public LineaComandaEnCurso(int idProd, int cantidad)
    {
        this.IdProducto = idProd;
        this.cantidad = cantidad;
    }

    public int getIdProducto() {
        return IdProducto;
    }

    public void setIdProducto(int idProducto) {
        IdProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
