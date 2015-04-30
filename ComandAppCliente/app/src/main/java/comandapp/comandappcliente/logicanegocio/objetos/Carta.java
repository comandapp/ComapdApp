package comandapp.comandappcliente.logicanegocio.objetos;

import java.util.ArrayList;

public class Carta {
    
    private ArrayList<LineaCarta> lista;
    
    public Carta(){
        this.lista = new ArrayList<LineaCarta>();
    }
    
    public Carta(ArrayList<LineaCarta> a){
        this.lista = a;
    }    

    public boolean aniadeLinea(LineaCarta e) {
        return lista.add(e);
    }
    
    public boolean eliminaLinea(LineaCarta e) {
        return lista.remove(e);
    }
    
    public LineaCarta getLinea(int i) {
        return lista.get(i);
    }
    
    public void clear() {
        this.lista.clear();
    }

    public Producto getProductById(int id) {
        for(LineaCarta e : lista) {
            if(e.getProducto().getId() == id) return e.getProducto();
        }
        return null;
    }
    
    public int numLineas() {
        return lista.size();
    }
    
    public boolean esVacia() {
        return lista.isEmpty();
    }
}
