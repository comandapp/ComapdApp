package comandapp.comandappcliente.presentacion.adaptadores;

import android.app.Activity;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import comandapp.comandappcliente.R;
import comandapp.comandappcliente.logicanegocio.LogicaNegocio;
import comandapp.comandappcliente.logicanegocio.objetos.LineaCarta;
import comandapp.comandappcliente.logicanegocio.objetos.Oferta;

/**
 * Created by miauen on 29/03/2015.
 */
public class AdaptadorCarta extends ArrayAdapter<LineaCarta> {

    private Activity context;
    private ArrayList<LineaCarta> listadoEntrada;
    private ArrayList<Oferta> ofertas;

    public AdaptadorCarta(Activity context, ArrayList<LineaCarta> datos, ArrayList<Oferta> ofertas) {
        super(context, R.layout.listitem_carta, datos);
        this.context = context;
        listadoEntrada = datos;
        this.ofertas=ofertas;
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
            holder.img = (ImageView)item.findViewById(R.id.LVICartaImg);
            holder.nombre = (TextView)item.findViewById(R.id.LVICartaNombre);
            holder.descripcion=(TextView)item.findViewById(R.id.LVICartaInfo);
            holder.precio=(TextView)item.findViewById(R.id.LVICartaPrecio);
            holder.precioOferta = (TextView)item.findViewById(R.id.LVICartaPrecioOferta);
            item.setTag(holder);
        }
        else
        {
            holder = (ViewHolderListadoEntradas)item.getTag();
        }

        holder.img.setImageBitmap(e.getProducto().getFoto());
        holder.nombre.setText(e.getProducto().getNombre());
        holder.precio.setText(e.getPrecio()+"€");
        holder.descripcion.setText(e.getDescripcion());
        double precioOf = LogicaNegocio.getInstancia().getPrecioFinalLineaCarta(context, e, ofertas);
        if(precioOf > 0) {
            holder.precio.setPaintFlags(holder.precio.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.precioOferta.setText(precioOf+"€");
        } else {
            holder.precio.setPaintFlags(holder.precio.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.precioOferta.setVisibility(View.GONE);
        }
        return(item);
    }

    public void filter(String charText) {
        //Log.w("-", charText.toString());
        charText = charText.toLowerCase(Locale.getDefault());
        listadoEntrada.clear();
        if (charText.length() == 0) {
            listadoEntrada.addAll(listadoEntrada);
        }
        else
        {
            for (LineaCarta e : listadoEntrada)
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
        TextView descripcion;
        TextView precio;
        TextView precioOferta;
    }
}
