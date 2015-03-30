package com.example.alumno.comandapp1;

/**
 * Created by yoMismo on 30/03/2015.
 */
public class LineaComanda
{
    private Entrada entradaProd;
    private int cantidad;

    public LineaComanda(Entrada entrada)
    {
        setEntradaProd(entrada);
        this.setCantidad(0);
    }

    public LineaComanda(Entrada entrada, int cantidad)
    {
        setEntradaProd(entrada);
        this.setCantidad(cantidad);
    }

    public Entrada getEntradaProd() {
        return entradaProd;
    }

    public void setEntradaProd(Entrada entradaProd) {
        this.entradaProd = entradaProd;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
