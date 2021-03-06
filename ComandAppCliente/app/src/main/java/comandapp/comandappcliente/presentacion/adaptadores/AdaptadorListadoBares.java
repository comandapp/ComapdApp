package comandapp.comandappcliente.presentacion.adaptadores;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Locale;

import comandapp.comandappcliente.R;
import comandapp.comandappcliente.logicanegocio.LogicaNegocio;
import comandapp.comandappcliente.logicanegocio.objetos.Bar;
import comandapp.comandappcliente.presentacion.actividades.BaresFavoritos;

/**
 * Created by Jesús on 24/03/2015.
 */
public class AdaptadorListadoBares extends ArrayAdapter<Bar>
{
    Activity context;
    ArrayList<Bar> listadoBares;
    ArrayList<Bar> listadoBaresAux;

    public AdaptadorListadoBares(Activity context, ArrayList<Bar> datos) {
        super(context, R.layout.listitem_bar, datos);
        this.context = context;
        listadoBares = datos;
        listadoBaresAux = new ArrayList<Bar>();
        listadoBaresAux.addAll(listadoBares);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View item = convertView;
        ViewHolderListadoBares holder;
        final Bar bar = listadoBares.get(position);

        if(item == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            item = inflater.inflate(R.layout.listitem_bar, null);

            holder = new ViewHolderListadoBares();
            holder.imgBar = (ImageView)item.findViewById(R.id.LVIBarImg);
            holder.nombre = (TextView)item.findViewById(R.id.LVIBarNombre);
            holder.descripcion = (TextView)item.findViewById(R.id.LVIBarInfo);
            holder.fav = (ImageView)item.findViewById(R.id.imageViewFavorito);
            holder.fila = (RelativeLayout)item.findViewById(R.id.LVItemRelLayout);

            item.setTag(holder);
        }
        else
        {
            holder = (ViewHolderListadoBares)item.getTag();
        }

        holder.imgBar.setImageBitmap(bar.getFoto());
        holder.nombre.setText(bar.getNombre());
        holder.descripcion.setText(bar.getMunicipio()+", "+bar.getProvincia());
        if(bar.getFavorito()) {
            holder.fav.setVisibility(View.VISIBLE);
            holder.fila.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.gradiente_lvitem_favorito));
        } else {
            holder.fav.setVisibility(View.INVISIBLE);
        }

        return(item);
    }

    public void filter(String charText) {
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
    }

    static class ViewHolderListadoBares {
        ImageView imgBar;
        TextView nombre;
        TextView descripcion;
        ImageView fav;
        RelativeLayout fila;
    }
}
