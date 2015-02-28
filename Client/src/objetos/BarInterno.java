package objetos;

public class BarInterno extends Bar {
    
    private String Contraseña;
    
    public BarInterno(int id, String nombre, String direccion ,int telefono, String correo,double latitud, double longitud, String provincia, String municipio,String contraseña, int vBar, int vOfertas) {
        super(id,nombre,direccion,telefono,correo,latitud,longitud,provincia,municipio,vBar,vOfertas);
        this.Contraseña = contraseña;
    }
    
    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String Contraseña) {
        this.Contraseña = Contraseña;
    }
 
}
