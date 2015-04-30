/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.alumno.comandappCliente;

/**
 *
 * @author G62
 */
public class Entrada
{

    private Producto producto;
    private double precio;
    private String descripcion;
    //private BufferedImage foto;

    public Entrada(Producto producto) {
        this.producto = producto;
        precio = 0;
        descripcion = "";
    }

    public Entrada(Producto producto, double precio) {
        this.producto = producto;
        this.precio = precio;
        descripcion = "";
    }

    public Entrada(Producto producto, double precio, String descripcion) {
        this.producto = producto;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public Entrada(Producto producto, double precio, String descripcion, String foto) {
        this.producto = producto;
        this.precio = precio;
        this.descripcion=descripcion;
        //this.foto = ImgCodec.decodeImage(foto);
    }
    
    public Producto getProducto() {
        return producto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /*public BufferedImage getFoto() {
        return foto;
    }*/

    /*public void setFoto(BufferedImage foto) {
        this.foto = foto;
    }*/
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
