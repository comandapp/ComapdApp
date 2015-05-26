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

                if(isNumeric(subPartes[0]) && isNumeric(subPartes[1]))
                {
                    lineasEnCurso.add(new LineaComandaEnCurso(Integer.parseInt(subPartes[0]), Integer.parseInt(subPartes[1])));
                }
                else
                {
                    return(null);
                }
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

    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
}
