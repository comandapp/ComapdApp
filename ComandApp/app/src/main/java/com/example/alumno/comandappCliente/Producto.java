package com.example.alumno.comandappCliente;

public class Producto {
    
    private int id;
    private String nombre;
    private String categoria;

    public Producto(int id, String nombre){
        this.id=id;
        this.nombre=nombre;
        this.categoria = "";
    }

    public Producto(int id, String nombre, String categoria){
        this.id=id;
        this.nombre=nombre;
        this.categoria=categoria;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

}
