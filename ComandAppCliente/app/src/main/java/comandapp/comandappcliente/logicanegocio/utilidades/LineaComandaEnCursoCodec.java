package comandapp.comandappcliente.logicanegocio.utilidades;

import java.util.ArrayList;

import comandapp.comandappcliente.logicanegocio.objetos.LineaComandaEnCurso;

public class LineaComandaEnCursoCodec {

    public static ArrayList<LineaComandaEnCurso> stringToLineasComandaEnCurso(String lineas)
    {
        ArrayList<LineaComandaEnCurso> lineasEnCurso = null;
        String[] partes = lineas.split("/");

        if(partes.length > 0)
        {
            lineasEnCurso = new ArrayList<LineaComandaEnCurso>();

            for(String parte : partes)
            {
                String[] subPartes = parte.split(",");
                lineasEnCurso.add(new LineaComandaEnCurso(Integer.parseInt(subPartes[0]), Integer.parseInt(subPartes[1])));
            }
        }

        return(lineasEnCurso);
    }

    public static String lineasComandaEnCursoToString(ArrayList<LineaComandaEnCurso> lineas)
    {
        String codificadas = "";

        for(LineaComandaEnCurso linea : lineas)
        {
            codificadas = codificadas + linea.getIdProducto() + "," + linea.getCantidad() + "/";
        }

        if(codificadas.length() > 0)
        {
            codificadas = codificadas.substring(0, codificadas.length()-1);
        }

        return codificadas;
    }
}
