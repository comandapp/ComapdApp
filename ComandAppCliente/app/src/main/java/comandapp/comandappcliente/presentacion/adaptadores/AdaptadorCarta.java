package comandapp.comandappcliente.presentacion.adaptadores;

import android.app.Activity;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import comandapp.comandappcliente.R;
import comandapp.comandappcliente.logicanegocio.LogicaNegocio;
import comandapp.comandappcliente.logicanegocio.objetos.LineaCarta;
import comandapp.comandappcliente.logicanegocio.objetos.LineaComanda;
import comandapp.comandappcliente.logicanegocio.objetos.Oferta;

/**
 * Created by miauen on 29/03/2015.
 */
public class AdaptadorCarta extends ArrayAdapter<LineaCarta> {

    private Activity context;
    private ArrayList<LineaCarta> listadoEntrada;
    private ArrayList<Oferta> ofertas;
    ArrayList<LineaComanda> lComanda;
    public AdaptadorCarta(Activity context, ArrayList<LineaCarta> datos, ArrayList<Oferta> ofertas) {
        super(context, R.layout.listitem_carta, datos);
        this.context = context;
        listadoEntrada = datos;
        this.ofertas=ofertas;
        lComanda=new ArrayList<LineaComanda>();
        for(LineaCarta lC:listadoEntrada){
            lComanda.add(new LineaComanda(new LineaCarta(lC.getProducto(),lC.getPrecio(), lC.getDescripcion()),0));
        }
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View item = convertView;
        ViewHolderListadoEntradas holder;
        final LineaCarta e = listadoEntrada.get(position);

        if(item == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            item = inflater.inflate(R.layout.listitem_carta, null);
            final View v=item;
            holder = new ViewHolderListadoEntradas();
            holder.img = (ImageView)item.findViewById(R.id.LVICartaImg);
            holder.nombre = (TextView)item.findViewById(R.id.LVICartaNombre);
            holder.descripcion=(TextView)item.findViewById(R.id.LVICartaInfo);
            holder.precio=(TextView)item.findViewById(R.id.LVICartaPrecio);
            holder.precioOferta = (TextView)item.findViewById(R.id.LVICartaPrecioOferta);
            final ViewHolderListadoEntradas vhlo=holder;
            LinearLayout hl=(LinearLayout)v.findViewById(R.id.layoutPrecios);
            final TextView tv=new TextView(context);
            tv.setText("0");
            tv.setId(R.id.but);
            hl.addView(tv);

            ((RelativeLayout)item.findViewById(R.id.layoutCartaGlobal)).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v)
                {

                    for(int i=0;i<((ListView)context.findViewById(R.id.listaCarta)).getChildCount();i++) {
                        View a=(RelativeLayout)((ListView)context.findViewById(R.id.listaCarta)).getChildAt(i).findViewById(R.id.layoutCartaGlobal);
                        if(a instanceof RelativeLayout) {
                            if(((LinearLayout)((RelativeLayout)a).findViewById(R.id.layoutPrecios)).getChildCount()>3) {
                                //LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                //llp.setMargins(10, 0, 100, 0); // llp.setMargins(left, top, right, bottom);
                                ((LinearLayout)((RelativeLayout)a).findViewById(R.id.layoutPrecios)).removeView(((LinearLayout)((RelativeLayout)a).findViewById(R.id.layoutPrecios)).getChildAt(((LinearLayout)((RelativeLayout)a).findViewById(R.id.layoutPrecios)).getChildCount()-3));
                                ((LinearLayout)((RelativeLayout)a).findViewById(R.id.layoutPrecios)).removeView(((LinearLayout)((RelativeLayout)a).findViewById(R.id.layoutPrecios)).getChildAt(((LinearLayout)((RelativeLayout)a).findViewById(R.id.layoutPrecios)).getChildCount()-1));
                            }
                        }
                    }
                    // ((ViewGroup)v.getParent().getParent()).getId();
                    // hl.removeView(aux);

                    LinearLayout hl=(LinearLayout)v.findViewById(R.id.layoutPrecios);
                    final TextView aux=(TextView)hl.findViewById(R.id.lblCantidadProd);
                    final TextView textView=(TextView)hl.findViewById(R.id.LVICartaPrecio);
                    LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    llp.setMargins(10, 0, 20, 0); // llp.setMargins(left, top, right, bottom);
                    textView.setLayoutParams(llp);

                    Button but=new Button(context);
                    but.setText("▲");
                    but.setTextSize(20);
                    but.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            int auxInt = Integer.parseInt(tv.getText().toString()) + 1;
                            for(int i=0;i<((ListView)context.findViewById(R.id.listaCarta)).getChildCount();i++) {
                                View a=(RelativeLayout)((ListView)context.findViewById(R.id.listaCarta)).getChildAt(i).findViewById(R.id.layoutCartaGlobal);
                                if(a instanceof RelativeLayout) {
                                    if(((RelativeLayout)a).getChildCount()!=3) {
                                        LineaComanda lc=lComanda.get(i);
                                        lc.setCantidad(auxInt);
                                        lComanda.remove(i);
                                        lComanda.add(i,lc);
                                    }
                                }
                            }

                            tv.setText(auxInt + "");
                        }
                    });
                    hl.addView(but, 2);
                    but=new Button(context);
                    but.setText("▼");
                    but.setTextSize(20);
                    but.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v)
                        {
                            int auxInt=Integer.parseInt(tv.getText().toString());
                            if(auxInt>0) {
                                auxInt--;
                                for(int i=0;i<((ListView)context.findViewById(R.id.listaCarta)).getChildCount();i++) {
                                    View a=(RelativeLayout)((ListView)context.findViewById(R.id.listaCarta)).getChildAt(i).findViewById(R.id.layoutCartaGlobal);
                                    if(a instanceof RelativeLayout) {
                                        if(((RelativeLayout)a).getChildCount()!=3) {
                                            LineaComanda lc=lComanda.get(i);
                                            lc.setCantidad(auxInt);
                                            lComanda.remove(i);
                                            lComanda.add(i,lc);
                                        }
                                    }
                                }
                                tv.setText(auxInt + "");
                            }
                        }
                    });
                    hl.addView(but,4);
                    notifyDataSetChanged();
                }
            });
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
