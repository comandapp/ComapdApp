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
import comandapp.comandappcliente.logicanegocio.objetos.Oferta;

/**
 * Created by miauen on 29/03/2015.
 */
public class AdaptadorOfertas extends ArrayAdapter<Oferta> {
    Activity context;
    ArrayList<Oferta> listadoOferta;
    ArrayList<Oferta> listadoOfertaAux;

    public AdaptadorOfertas(Activity context, ArrayList<Oferta> datos) {
        super(context, R.layout.listitem_oferta, datos);
        this.context = context;
        listadoOferta = datos;
        listadoOfertaAux = new ArrayList<Oferta>();
        listadoOfertaAux.addAll(listadoOferta);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View item = convertView;
        ViewHolderListadoOfertas holder;
        final Oferta o = listadoOferta.get(position);

        if(item == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            item = inflater.inflate(R.layout.listitem_oferta, null);

            holder = new ViewHolderListadoOfertas();
            holder.img = (ImageView)item.findViewById(R.id.LVIOfertaImg);
            holder.nombre = (TextView)item.findViewById(R.id.LVIOfertaNombre);
            holder.descripcion = (TextView)item.findViewById(R.id.LVIOfertaInfo);
            holder.precio = (TextView)item.findViewById(R.id.LVIOfertaPrecio);
            item.setTag(holder);
        }
        else
        {
            holder = (ViewHolderListadoOfertas)item.getTag();
        }

        holder.img.setImageBitmap(o.getProducto().getFoto());
        holder.nombre.setText(o.getProducto().getNombre());
        holder.descripcion.setText(o.getDescripcion());
        holder.precio.setText(o.getPrecio()+"€");
        return(item);
    }

    public void filter(String charText) {
        //Log.w("-", charText.toString());
        charText = charText.toLowerCase(Locale.getDefault());
        listadoOferta.clear();
        if (charText.length() == 0) {
            listadoOferta.addAll(listadoOfertaAux);
        }
        else
        {
            for (Oferta o : listadoOfertaAux)
            {
                if (o.getProducto().getNombre().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    listadoOferta.add(o);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class ViewHolderListadoOfertas {
        ImageView img;
        TextView nombre;
        TextView descripcion;
        TextView precio;
    }
}
