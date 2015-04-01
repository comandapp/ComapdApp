package comandapp.comandappuser.objetos;

public class Version {
    
    private int vLocal;
    private int vCarta;
    private int vOfertas;
    
    public Version() {
        vLocal = vCarta = vOfertas = 0;
    }

    public Version(int vLocal, int vCarta, int vOfertas) {
        this.vLocal = vLocal;
        this.vCarta = vCarta;
        this.vOfertas = vOfertas;
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
