package com.example.alumno.comandapp1;

import android.os.Parcel;
import android.os.Parcelable;

public class Bar implements Parcelable{
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
    private Boolean favorito;
    
    private Carta carta;
    
    public Bar (int id, String nombre, String direccion ,int telefono, String correo,double latitud, double longitud, String provincia, String municipio, int vBar, int vOfertas, Boolean favorito){
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
        this.favorito = favorito;
    }

    public Bar (String nombre, Boolean favorito)
    {
        this.nombre = nombre;
        this.favorito = favorito;
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
    
    public Boolean getFavorito() {
        return this.favorito;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(direccion);
        dest.writeString(getProvincia());
        dest.writeString(getMunicipio());
        dest.writeString(correo);
        dest.writeInt(id);
        dest.writeInt(telefono);
        dest.writeInt(versionBar);
        dest.writeInt(versionOfertas);
        dest.writeDouble(latitud);
        dest.writeDouble(longitud);
    }

    private Bar(Parcel in){

        this.nombre=in.readString();
        this.direccion=in.readString();
        this.correo=in.readString();
        this.Provincia=in.readString();
        this.Municipio=in.readString();
        this.id=in.readInt();
        this.telefono=in.readInt();
        this.versionBar = in.readInt();
        this.versionOfertas = in.readInt();
        this.latitud=in.readDouble();
        this.longitud=in.readDouble();
        this.carta = new Carta();
    }

    public static final Parcelable.Creator<Bar> CREATOR
            = new Parcelable.Creator<Bar>() {
        @Override
        public Bar createFromParcel(Parcel in) {
            return new Bar(in);
        }

        @Override
        public Bar[] newArray(int size) {
            return new Bar[size];
        }
    };
}
