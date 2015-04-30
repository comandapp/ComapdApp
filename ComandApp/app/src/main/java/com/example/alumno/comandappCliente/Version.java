/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.alumno.comandappCliente;

public class Version {
    
    private int vLocal;
    private int vOfertas;
    
    public Version(int vLocal, int vOfertas) {
        this.vLocal = vLocal;
        this.vOfertas = vOfertas;
    }
    
    public int getVersionLocal() {
        return vLocal;
    }
    
    
    public int getVersionOfertas() {
        return vOfertas;
    }
}
