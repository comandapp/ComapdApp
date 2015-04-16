package com.example.alumno.comandapp1;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
            final View v=item;
            holder = new ViewHolderListadoOfertas();
            final ViewHolderListadoOfertas vhlo=holder;
            holder.nombre = (TextView)item.findViewById(R.id.lblNombreOferta);
            holder.precio=(TextView)item.findViewById(R.id.lblPrecioOferta);
            holder.descripcion=(TextView)item.findViewById(R.id.lblDescripcionOferta);

            LinearLayout hl=(LinearLayout)v.findViewById(R.id.hLayout);
            TextView tv=new TextView(context);
            tv.setText("0");
            RelativeLayout.LayoutParams adminTextViewLayoutParams = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            tv.setLayoutParams(adminTextViewLayoutParams);
            tv.setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);
            hl.addView(tv);


            ((LinearLayout)item.findViewById(R.id.LLayout)).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v)
                {

                       for(int i=0;i<((ListView)context.findViewById(R.id.lo)).getChildCount();i++) {
                           View a=(LinearLayout)((ListView)context.findViewById(R.id.lo)).getChildAt(i).findViewById(R.id.LLayout);
                           if(a instanceof LinearLayout) {
                               if(((LinearLayout)((LinearLayout)a).findViewById(R.id.hLayout)).getChildCount()!=3) {
                                   ((LinearLayout) ((LinearLayout) a).findViewById(R.id.hLayout)).removeView(((LinearLayout) ((LinearLayout) a).findViewById(R.id.hLayout)).getChildAt(2));
                                   ((LinearLayout) ((LinearLayout) a).findViewById(R.id.hLayout)).removeView(((LinearLayout) ((LinearLayout) a).findViewById(R.id.hLayout)).getChildAt(3));
                               }
                           }
                       }
                       // ((ViewGroup)v.getParent().getParent()).getId();
                       // hl.removeView(aux);

                    LinearLayout hl=(LinearLayout)v.findViewById(R.id.hLayout);

                    Button but=new Button(context);
                    but.setText("▲");
                    but.setTextSize(40);
                    hl.addView(but,2);
                    but=new Button(context);
                    but.setText("▼");
                    but.setTextSize(40);
                    hl.addView(but,4);
                    notifyDataSetChanged();
                }
            });
            v.setTag(holder);
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
        TextView prueba;
        Button b;
    }
}
