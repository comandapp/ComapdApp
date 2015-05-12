package comandapp.comandappcliente.presentacion.adaptadores;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import comandapp.comandappcliente.R;
import comandapp.comandappcliente.logicanegocio.objetos.Bar;
import comandapp.comandappcliente.logicanegocio.objetos.Comanda;
import comandapp.comandappcliente.logicanegocio.objetos.LineaComanda;

/**
 * Created by Jesús on 24/03/2015.
 */
public class AdaptadorHistorialComandas extends ArrayAdapter<Comanda>
{
    Activity context;
    ArrayList<Comanda> listadoComandas;
    ArrayList<Comanda> listadoComandasAux;

    public AdaptadorHistorialComandas(Activity context, ArrayList<Comanda> datos) {
        super(context, R.layout.listitem_historial, datos);
        this.context = context;
        listadoComandas = datos;
        listadoComandasAux = new ArrayList<Comanda>();
        listadoComandasAux.addAll(listadoComandas);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View item = convertView;
        ViewHolderListadoComandas holder;
        final Comanda comanda = listadoComandas.get(position);

        if(item == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            item = inflater.inflate(R.layout.listitem_historial, null);

            holder = new ViewHolderListadoComandas();
            holder.imgBar = (ImageView)item.findViewById(R.id.imgBar);
            holder.nombre = (TextView)item.findViewById(R.id.lblNombreComanda);
            holder.fecha = (TextView)item.findViewById(R.id.lblFecha);
            holder.precio = (TextView)item.findViewById(R.id.lblPrecioComanda);

            item.setTag(holder);
        }
        else
        {
            holder = (ViewHolderListadoComandas)item.getTag();
        }

        //Log.w("----------------->", comanda.getNombre() + "");
        holder.imgBar.setImageBitmap(comanda.getFotoBar());
        holder.nombre.setText(comanda.getNombre());
        holder.fecha.setText(comanda.getFecha().toString());

        int precioFinal = 0;
        for(LineaComanda lin : comanda.getLineasComanda())
        {
            precioFinal += lin.getProductoCarta().getPrecio() * lin.getCantidad();
        }

        holder.precio.setText("" + precioFinal);

        return(item);
    }

    public void filter(String charText) {

        notifyDataSetChanged();

        charText = charText.toLowerCase(Locale.getDefault());
        listadoComandas.clear();
        if (charText.length() == 0) {
            listadoComandas.addAll(listadoComandasAux);
        }
        else
        {
            for (Comanda com : listadoComandasAux)
            {
                if (com.getNombre().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    listadoComandas.add(com);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class ViewHolderListadoComandas {
        ImageView imgBar;
        TextView nombre;
        TextView fecha;
        TextView precio;
    }
}
