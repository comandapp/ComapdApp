package comandapp.comandappuser.objetos;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Bar {
    private int id;
    private String nombre;
    private String direccion;
    private int telefono;
    private String correo;
    private double latitud;
    private double longitud;
    private String Provincia;
    private String Municipio;
    private Bitmap foto;
    
    private Version version;
    private Carta carta;
    private ArrayList<Oferta> ofertas;

    public Bar (int id) {
        this.id = id;
        this.nombre="";
        this.direccion="";
        this.telefono=0;
        this.correo="";
        this.latitud=0;
        this.longitud=0;
        this.Provincia="";
        this.Municipio="";
        this.foto = null;
        this.version = new Version();
        this.carta = new Carta();
        this.ofertas = new ArrayList<Oferta>();
    }

    public Bar (int id, String nombre, String direccion ,int telefono, String correo,double latitud, double longitud, String provincia, String municipio, Bitmap foto, int vInfoLocal){
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
        this.version = new Version();
        this.version.setVersionInfoLocal(vInfoLocal);
        this.carta = new Carta();
        this.ofertas = new ArrayList<Oferta>();
    }
    
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public int getTelefono() {
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
    
    public Version getVersion() {
        return this.version;
    }
    
    public Carta getCarta() {
        return this.carta;
    }
    
    public ArrayList<Oferta> getOfertas() {
        return this.ofertas;
    }
    
    public void importarInfoBar(Bar b) {
        //this.id=b.id;
        this.nombre=b.getNombre();
        this.direccion=b.getDireccion();
        this.telefono=b.getTelefono();
        this.correo=b.getCorreo();
        this.latitud=b.getLatitud();
        this.longitud=b.getLongitud();
        this.Provincia = b.getProvincia();
        this.Municipio = b.getMunicipio();
        this.version.setVersionInfoLocal(b.version.getVersionInfoLocal());
    }
    
    public void importarCarta(Carta c, int version) {
        this.carta.clear();
        for(int i=0;i<c.numEntradas();i++) {
            this.carta.aniadeEntrada(c.getEntrada(i));
        }
        this.version.setVersionCarta(version);
    }
    
    public void importarOfertas(ArrayList<Oferta> o, int version) {
        this.ofertas.clear();
        for(Oferta of : o) this.ofertas.add(of);
        this.version.setVersionOfertas(version);
    }
    
    public int compareTo(Bar b) {
        if(id < b.getId()) return -1;
        else if(id == b.getId()) return 0;
        else return 1;
    }
}
