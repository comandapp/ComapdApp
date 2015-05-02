package comandapp.comandappcliente.logicanegocio.objetos;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Bar {

    private int id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String correo;
    private double latitud;
    private double longitud;
    private String Provincia;
    private String Municipio;
    private Bitmap foto;
    private Boolean favorito;
    private Version version;
    private ArrayList<LineaCarta> carta;
    private ArrayList<Oferta> ofertas;

    public Bar (int id) {
        this.id = id;
        this.nombre="";
        this.direccion="";
        this.telefono="";
        this.correo="";
        this.latitud=0;
        this.longitud=0;
        this.Provincia="";
        this.Municipio="";
        this.foto = null;
        this.favorito=false;
        this.version = new Version();
        this.carta = new ArrayList<LineaCarta>();
        this.ofertas = new ArrayList<Oferta>();
    }

    public Bar (int id, String nombre, String direccion ,String telefono, String correo,double latitud, double longitud, String provincia, String municipio, Bitmap foto, Boolean favorito, int vInfoLocal){
        this.id=id;
        this.nombre=nombre;
        this.direccion=direccion;
        this.telefono=telefono;
        this.correo=correo;
        this.latitud=latitud;
        this.longitud=longitud;
        this.Provincia=provincia;
        this.Municipio=municipio;
        this.foto = foto;
        this.favorito=favorito;
        this.version = new Version();
        this.version.setVersionInfoLocal(vInfoLocal);
        this.carta = new ArrayList<LineaCarta>();
        this.ofertas = new ArrayList<Oferta>();
    }

    public int getIdBar() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public String getProvincia() {
        return Provincia;
    }

    public String getMunicipio() {
        return Municipio;
    }

    public Bitmap getFoto() { return this.foto; }

    public Boolean getFavorito() { return this.favorito; }

    public Version getVersion() {
        return this.version;
    }

    public ArrayList<LineaCarta> getCarta() {
        return this.carta;
    }

    public void setFavorito(Boolean fav)
    {
        this.favorito = fav;
    }

    public ArrayList<Oferta> getOfertas() {
        return this.ofertas;
    }

    public boolean esHueco() {
        return ((carta.size() == 0) && (ofertas.size() == 0));
    }

    public void setCarta(ArrayList<LineaCarta> c) {
        this.carta = c;
    }

    public void setOfertas(ArrayList<Oferta> o) {
        this.ofertas = o;
    }

    public int compareTo(Bar b) {
        if(id < b.getIdBar()) return -1;
        else if(id == b.getIdBar()) return 0;
        else return 1;
    }

}
