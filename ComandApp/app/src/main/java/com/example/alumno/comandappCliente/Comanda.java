package com.example.alumno.comandappCliente;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yoMismo on 29/03/2015.
 */
public class Comanda
{
    private Bar bar;
    private Date fecha;
    private ArrayList<LineaComanda> lineasComanda;

    public Comanda(Bar bar)
    {
        this.setBar(bar);
        this.setFecha(Calendar.getInstance().getTime());
        setLineasComanda(new ArrayList<LineaComanda>());
    }

    public Comanda(Bar bar, ArrayList<LineaComanda> lineas)
    {
        this.setBar(bar);
        this.setFecha(Calendar.getInstance().getTime());
        setLineasComanda(lineas);
    }

    public Bar getBar() {
        return bar;
    }

    public void setBar(Bar bar) {
        this.bar = bar;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public ArrayList<LineaComanda> getLineasComanda() {
        return lineasComanda;
    }

    public void setLineasComanda(ArrayList<LineaComanda> lineasComanda) {
        this.lineasComanda = lineasComanda;
    }

    public boolean aniadeLinea(LineaComanda e) {
        return lineasComanda.add(e);
    }

    public boolean eliminaLinea(LineaComanda e) {
        return lineasComanda.remove(e);
    }

    public LineaComanda getLinea(int i) {
        return lineasComanda.get(i);
    }

    public boolean esVacia() {
        return lineasComanda.isEmpty();
    }

    public double getPrecioTotal()
    {
        double total = 0;

        for(LineaComanda linea : lineasComanda)
        {
            total += (linea.getEntradaProd().getPrecio() * linea.getCantidad());
        }

        return  total;
    }

    public  int getNumeroLineas()
    {
        return (lineasComanda.size());
    }
}
