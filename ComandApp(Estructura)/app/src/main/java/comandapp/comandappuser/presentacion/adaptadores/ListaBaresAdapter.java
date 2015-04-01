package comandapp.comandappuser.presentacion.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import comandapp.comandappuser.R;
import comandapp.comandappuser.objetos.Bar;

/**
 * Created by G62 on 14-Mar-15.
 */
public class ListaBaresAdapter extends ArrayAdapter<Bar> {

    public ListaBaresAdapter(Context context, ArrayList<Bar> bares) {
        super(context, 0, bares);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Bar bar = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lista_bares, parent, false);
        }
        // Lookup view for data population
        ImageView img = (ImageView) convertView.findViewById(R.id.imagenBar);
        img.setImageBitmap(bar.getFoto());
        TextView nombre = (TextView) convertView.findViewById(R.id.nombre);
        nombre.setText(bar.getNombre());
        TextView municipio = (TextView) convertView.findViewById(R.id.municipio);
        municipio.setText(bar.getMunicipio());
        // Return the completed view to render on screen
        return convertView;
    }

}