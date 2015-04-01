package comandapp.comandappuser.objetos;

/**
 * Created by G62 on 19-Mar-15.
 */
public class ErrorServerBar {

    private String id_bar;
    private int error;
    private int[] argums;

    //Si sólo hay id_bar --> Error
    public ErrorServerBar(String id_bar, String errorString) {
        this.id_bar = id_bar;
        String[] aux = errorString.split("-");
        if(aux.length > 0) {
            error = Integer.parseInt(aux[0]);
            String[] aux2 = aux[1].split(" ");
            if(aux.length > 0) {
                //
            }
        }

    }
}
