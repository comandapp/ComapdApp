package objetos;

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
    private int versionBar;
    private int versionOfertas;
    
    private Carta carta;
    private ArrayList<Oferta> ofertas;
    
    public Bar (int id, String nombre, String direccion ,int telefono, String correo,double latitud, double longitud, String provincia, String municipio, int vBar, int vOfertas){
        this.id=id;
        this.nombre=nombre;
        this.direccion=direccion;
        this.telefono=telefono;
        this.correo=correo;
        this.latitud=latitud;
        this.longitud=longitud;
        this.Provincia=provincia;
        this.Municipio=municipio;
        this.versionBar = vBar;
        this.versionOfertas = vOfertas;
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
    
    public int getVersionBar() {
        return this.versionBar;
    }
    
    public int getVersionOfertas() {
        return this.versionOfertas;
    }
    
    public Carta getCarta() {
        return this.carta;
    }
    
    public ArrayList<Oferta> getOfertas() {
        return this.ofertas;
    }
    
    public void importarInfoBar(Bar b) {
        this.id=b.id;
        this.nombre=b.getNombre();
        this.direccion=b.getDireccion();
        this.telefono=b.getTelefono();
        this.correo=b.getCorreo();
        this.latitud=b.getLatitud();
        this.longitud=b.getLongitud();
        this.Provincia = b.getProvincia();
        this.Municipio = b.getMunicipio();
        this.versionBar = b.getVersionBar();
        this.versionOfertas = b.getVersionOfertas();
    }
    
    public void importarCarta(Bar b) {
        this.carta = b.getCarta();
    }
    
    public void importarOfertas(Bar b) {
        this.ofertas = b.getOfertas();
    }
    
}
