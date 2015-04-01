package comandapp.comandappuser.objetos;

import java.util.ArrayList;

public class Carta {
    
    private ArrayList<Linea_Carta> lista;
    
    public Carta(){
        this.lista = new ArrayList<Linea_Carta>();
    }
    
    public Carta(ArrayList<Linea_Carta> a){
        this.lista = a;
    }    

    public boolean aniadeEntrada(Linea_Carta e) {
        return lista.add(e);
    }
    
    public boolean eliminaEntrada(Linea_Carta e) {
        return lista.remove(e);
    }
    
    public Linea_Carta getEntrada(int i) {
        return lista.get(i);
    }
    
    public void clear() {
        this.lista.clear();
    }

    public Producto getProductById(int id) {
        for(Linea_Carta e : lista) {
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
