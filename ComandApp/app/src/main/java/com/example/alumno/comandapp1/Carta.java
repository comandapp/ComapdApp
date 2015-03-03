package com.example.alumno.comandapp1;

import java.util.ArrayList;

public class Carta {
    
    private ArrayList<Entrada> lista;
    
    public Carta(){
        this.lista = new ArrayList<Entrada>();
    }
    
    public Carta(ArrayList<Entrada> a){
        this.lista = a;
    }    

    public boolean aniadeEntrada(Entrada e) {
        return lista.add(e);
    }
    
    public boolean eliminaEntrada(Entrada e) {
        return lista.remove(e);
    }
    
    public Entrada getEntrada(int i) {
        return lista.get(i);
    }
    
    public boolean esVacia() {
        return lista.isEmpty();
    }
}
