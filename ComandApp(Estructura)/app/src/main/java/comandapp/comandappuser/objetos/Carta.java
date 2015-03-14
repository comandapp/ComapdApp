package comandapp.comandappuser.objetos;

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
    
    public void clear() {
        this.lista.clear();
    }

    public Producto getProductById(int id) {
        for(Entrada e : lista) {
            if(e.getProducto().getId() == id) return e.getProducto();
        }
        return null;
    }
    
    public int numEntradas() {
        return lista.size();
    }
    
    public boolean esVacia() {
        return lista.isEmpty();
    }
}
