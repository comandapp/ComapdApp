package com.example.alumno.comandapp1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

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
            holder.nombre = (TextView)item.findViewById(R.id.lblNombreOferta);
            holder.precio=(TextView)item.findViewById(R.id.lblPrecioOferta);
            holder.descripcion=(TextView)item.findViewById(R.id.lblDescripcionOferta);
            item.setTag(holder);
        }
        else
        {
            holder = (ViewHolderListadoOfertas)item.getTag();
        }

        holder.nombre.setText(o.getNombre());
        holder.precio.setText(o.getPrecio()+"€");
        holder.descripcion.setText(o.getDescripcion());
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
                if (o.getNombre().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    listadoOferta.add(o);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class ViewHolderListadoOfertas {
        TextView nombre;
        TextView precio;
        TextView descripcion;
    }
}
