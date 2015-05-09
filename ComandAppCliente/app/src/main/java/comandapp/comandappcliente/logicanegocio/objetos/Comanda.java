package comandapp.comandappcliente.logicanegocio.objetos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yoMismo on 29/03/2015.
 */
public class Comanda
{
    private String nombreComanda;
    private Bar bar;
    private Date fecha;
    private ArrayList<LineaComanda> lineasComanda;

    public Comanda(String nombre)
    {
        this.nombreComanda = nombre;
        this.bar = null;
        this.setFecha(Calendar.getInstance().getTime());
        setLineasComanda(new ArrayList<LineaComanda>());
    }

    public Comanda(String nombre, Bar bar)
    {
        this.nombreComanda = nombre;
        this.setBar(bar);
        this.setFecha(Calendar.getInstance().getTime());
        setLineasComanda(new ArrayList<LineaComanda>());
    }

    public Comanda(String nombre, Bar bar, ArrayList<LineaComanda> lineas)
    {
        this.nombreComanda = nombre;
        this.setBar(bar);
        this.setFecha(Calendar.getInstance().getTime());
        setLineasComanda(lineas);
    }

    public String getNombre() {
        return nombreComanda;
    }

    public void setNombre(String nombre) {
        this.nombreComanda = nombre;
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
   //         total += (linea.getEntradaProd().getPrecio() * linea.getCantidad());
        }

        return  total;
    }

    public  int getNumeroLineas()
    {
        return (lineasComanda.size());
    }
}
