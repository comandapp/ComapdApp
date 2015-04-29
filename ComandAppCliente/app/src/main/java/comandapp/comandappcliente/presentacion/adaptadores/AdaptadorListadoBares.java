package comandapp.comandappcliente.presentacion.adaptadores;

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
            holder.nombre = (TextView)item.findViewById(R.id.lblNombreBar);
            holder.checkFavorito = (CheckBox)item.findViewById(R.id.checkBarFavorito);

            holder.checkFavorito.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v)
                {
                    Log.w("-->", ((CheckBox) v).isChecked() + "");
                    bar.setFavorito(((CheckBox) v).isChecked());

                    if(!((CheckBox) v).isChecked() && context instanceof BaresFavoritos)
                    {
                        listadoBares.remove(position);
                        notifyDataSetChanged();
                    }

                }
            });

            item.setTag(holder);
        }
        else
        {
            holder = (ViewHolderListadoBares)item.getTag();
        }

        holder.nombre.setText(bar.getNombre());
        holder.checkFavorito.setChecked(bar.getFavorito());

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
        TextView nombre;
        CheckBox checkFavorito;
    }
}
