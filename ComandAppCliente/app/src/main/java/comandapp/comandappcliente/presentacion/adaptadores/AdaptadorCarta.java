package comandapp.comandappcliente.presentacion.adaptadores;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import comandapp.comandappcliente.R;
import comandapp.comandappcliente.logicanegocio.objetos.LineaCarta;

/**
 * Created by miauen on 29/03/2015.
 */
public class AdaptadorCarta extends ArrayAdapter<LineaCarta> {
    Activity context;
    ArrayList<LineaCarta> listadoEntrada;
    ArrayList<LineaCarta> listadoEntradaAux;

    public AdaptadorCarta(Activity context, ArrayList<LineaCarta> datos) {
        super(context, R.layout.listitem_carta, datos);
        this.context = context;
        listadoEntrada = datos;
        listadoEntradaAux = new ArrayList<LineaCarta>();
        listadoEntradaAux.addAll(listadoEntrada);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View item = convertView;
        ViewHolderListadoEntradas holder;
        final LineaCarta e = listadoEntrada.get(position);

        if(item == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            item = inflater.inflate(R.layout.listitem_carta, null);

            holder = new ViewHolderListadoEntradas();
            holder.img = (ImageView)item.findViewById(R.id.imgEntrada);
            holder.nombre = (TextView)item.findViewById(R.id.lblNombreEntrada);
            holder.precio=(TextView)item.findViewById(R.id.lblPrecioEntrada);
            holder.descripcion=(TextView)item.findViewById(R.id.lblDescripcionEntrada);
            item.setTag(holder);
        }
        else
        {
            holder = (ViewHolderListadoEntradas)item.getTag();
        }

        holder.img.setImageBitmap(e.getFoto());
        holder.nombre.setText(e.getProducto().getNombre());
        holder.precio.setText(e.getPrecio()+"€");
        holder.descripcion.setText(e.getDescripcion());
        return(item);
    }

    public void filter(String charText) {
        //Log.w("-", charText.toString());
        charText = charText.toLowerCase(Locale.getDefault());
        listadoEntrada.clear();
        if (charText.length() == 0) {
            listadoEntrada.addAll(listadoEntradaAux);
        }
        else
        {
            for (LineaCarta e : listadoEntradaAux)
            {
                if (e.getProducto().getNombre().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    listadoEntrada.add(e);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class ViewHolderListadoEntradas {
        ImageView img;
        TextView nombre;
        TextView precio;
        TextView descripcion;
    }
}
