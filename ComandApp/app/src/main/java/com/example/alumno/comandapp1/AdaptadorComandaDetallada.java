package com.example.alumno.comandapp1;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Jesús on 24/03/2015.
 */
public class AdaptadorComandaDetallada extends ArrayAdapter<LineaComanda>
{
    Activity context;
    ArrayList<LineaComanda> lineasComanda;

    public AdaptadorComandaDetallada(Activity context, ArrayList<LineaComanda> datos) {
        super(context, R.layout.listitem_bar, datos);
        this.context = context;
        lineasComanda = datos;
        //listadoBaresAux = new ArrayList<Bar>();
        //listadoBaresAux.addAll(listadoBares);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View item = convertView;
        ViewHolderLineasComanda holder;
        LineaComanda linea = lineasComanda.get(position);

        if(item == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            item = inflater.inflate(R.layout.listitem_comanda, null);

            holder = new ViewHolderLineasComanda();
            holder.nombre = (TextView)item.findViewById(R.id.lblNombreProducto);
            holder.precioIndividual = (TextView)item.findViewById(R.id.lblPrecioIndividual);
            holder.cantidad = (TextView)item.findViewById(R.id.lblCantidadProd);
            holder.precioTotal = (TextView)item.findViewById(R.id.lblPrecioTotal);

            item.setTag(holder);
        }
        else
        {
            holder = (ViewHolderLineasComanda)item.getTag();
        }

        holder.nombre.setText(linea.getEntradaProd().getProducto().getNombre());
        double precio = linea.getEntradaProd().getPrecio();
        holder.precioIndividual.setText(precio + "");
        int cantidad = linea.getCantidad();
        holder.cantidad.setText("x " + cantidad);
        holder.precioTotal.setText((cantidad * precio) + "");

        return(item);
    }

    /*public void filter(String charText) {
        //Log.w("-", charText.toString());
        charText = charText.toLowerCase(Locale.getDefault());
        listadoBares.clear();
        if (charText.length() == 0) {
            listadoBares.addAll(listadoBaresAux);
        }
        else
        {
            for (Bar bar : listadoBaresAux)
            {
                if (bar.getNombre().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    listadoBares.add(bar);
                }
            }
        }
        notifyDataSetChanged();
    }*/

    static class ViewHolderLineasComanda {
        TextView nombre;
        TextView cantidad;
        TextView precioIndividual;
        TextView precioTotal;//Precio que tendrá el total de los productos de esta linea
    }
}
