package objetos;

import java.util.Calendar;

public class Version {
    
    private int vLocal;
    private int vCarta;
    private int vOfertas;
    private long ultimaActualizacion;
    
    public Version() {
        vLocal = vCarta = vOfertas = 0;
        ultimaActualizacion = Calendar.getInstance().getTimeInMillis();
    }
    
    public int getVersionInfoLocal() {
        return vLocal;
    }
    
    public int getVersionCarta() {
        return vCarta;
    }
    
    public int getVersionOfertas() {
        return vOfertas;
    }
    
    public void setVersionInfoLocal(int i) {
        this.vLocal = i;
    }
    
    public void setVersionCarta(int i) {
        this.vCarta = i;
    }
        
    public void setVersionOfertas(int i) {
        this.vOfertas = i;
    }
}
