package comandapp.comandappcliente.logicanegocio.objetos;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yoMismo on 29/03/2015.
 */
public class Comanda
{
    private String nombreComanda;
    private Bitmap fotoBar;
    private Date fecha;
    private ArrayList<LineaComanda> lineasComanda;

    public Comanda(String nombre)
    {
        this.nombreComanda = nombre;
        this.fotoBar = null;
        this.setFecha(Calendar.getInstance().getTime());
        setLineasComanda(new ArrayList<LineaComanda>());
    }

    public Comanda(String nombre, Bitmap fotoBar, Date fecha)
    {
        this.nombreComanda = nombre;
        this.setFotoBar(fotoBar);
        this.setFecha(fecha);
        setLineasComanda(new ArrayList<LineaComanda>());
    }

    public Comanda(String nombre, Bitmap fotoBar, Date fecha, ArrayList<LineaComanda> lineas)
    {
        this.nombreComanda = nombre;
        this.setFotoBar(fotoBar);
        this.setFecha(fecha);
        setLineasComanda(lineas);
    }

    public String getNombre() {
        return nombreComanda;
    }

    public void setNombre(String nombre) {
        this.nombreComanda = nombre;
    }

    public Bitmap getFotoBar() {
        return fotoBar;
    }

    public void setFotoBar(Bitmap fotoBar) {
        this.fotoBar = fotoBar;
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

            total += (linea.getProductoCarta().getPrecio() * linea.getCantidad());
        }

        return  total;
    }

    public  int getNumeroLineas()
    {
        return (lineasComanda.size());
    }
}
