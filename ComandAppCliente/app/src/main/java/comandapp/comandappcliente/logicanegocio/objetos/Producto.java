package comandapp.comandappcliente.logicanegocio.objetos;

public class Producto {
    
    private int id;
    private String nombre;
    private String categoria;
    
    public Producto(int id, String nombre, String categoria){
        this.id=id;
        this.nombre=nombre;
        this.categoria=categoria;
    }
    
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }
}
