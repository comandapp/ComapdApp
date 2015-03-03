package com.example.alumno.comandapp1;

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
    }

    public Bar (String nombre)
    {
        this.nombre = nombre;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getProvincia() {
        return Provincia;
    }

    public void setProvincia(String Provincia) {
        this.Provincia = Provincia;
    }

    public String getMunicipio() {
        return Municipio;
    }

    public void setMunicipio(String Municipio) {
        this.Municipio = Municipio;
    }
    
    public int getVersionBar() {
        return this.versionBar;
    }
    
    public void setVersionBar(int i) {
        this.versionBar = i;
    }
    
    public int getVersionOfertas() {
        return this.versionOfertas;
    }
    
    public void setVersionOfertas(int i) {
        this.versionOfertas = i;
    }
    
    public Carta getCarta() {
        return this.carta;
    }
    
}
