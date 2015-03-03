/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import java.util.ArrayList;
import objetos.Bar;
import objetos.Producto;

/**
 *
 * @author G62
 */
public class Persistencia {
    
    private static Persistencia instancia;
    
    private ArrayList<Bar> bares;
    
    private Persistencia() {
        this.bares = new ArrayList<Bar>();
    }
    
    public static synchronized Persistencia getInstancia() {
        if(instancia == null) instancia = new Persistencia();
        return instancia;
    }
    
    public Bar getBarById(int id) {
        for(Bar b : bares) if(b.getId() == id) return b;
        return null;
    }
    
    public Bar getBarByProductId(int id) {
        for(Bar b : bares) if(b.getCarta().getProductoById(id) != null) return b;
        return null;
    }
    
    public Producto getProductById(int id) {
        for(Bar b : bares) {
            Producto p = b.getCarta().getProductoById(id);
            if(p != null) return p;
        }
        return null;
    }
    
    public boolean actualizaBar(Bar b) {
        Bar x = getBarById(b.getId());
        if(x != null) {
            x.importarInfoBar(b);
            x.importarCarta(b);
            x.importarOfertas(b);
            return true;
        }
        return false;
    }
    
    public boolean actualizaBar(Bar b, int option) {
        Bar x = getBarById(b.getId());
        if(x != null) {
            if(option == 0) {
                x.importarInfoBar(b);
            } else if(option == 1) {
                x.importarCarta(b);
            } else if(option == 2) {
                x.importarOfertas(b);
            } else return false;
            return true;
        }
        return false;
    }    
}
