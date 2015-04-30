package com.example.alumno.comandappCliente;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by miauen on 29/03/2015.
 */
public class AdaptadorCarta extends ArrayAdapter<Entrada> {
    Activity context;
    ArrayList<Entrada> listadoEntrada;
    ArrayList<Entrada> listadoEntradaAux;
    ArrayList<LineaComanda> lComanda;

    public AdaptadorCarta(Activity context, ArrayList<Entrada> datos) {
        super(context, R.layout.listitem_carta, datos);
        this.context = context;
        final Activity con=context;
        listadoEntrada = datos;
        listadoEntradaAux = new ArrayList<Entrada>();
        listadoEntradaAux.addAll(listadoEntrada);
        lComanda=new ArrayList<LineaComanda>();
        for(Entrada o:listadoEntrada){
            lComanda.add(new LineaComanda(new Entrada(o.getProducto()),0));
        }

    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View item = convertView;
        ViewHolderListadoEntradas holder;
        final Entrada e = listadoEntrada.get(position);

        if(item == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            item = inflater.inflate(R.layout.listitem_carta, null);
            final View v=item;
            holder = new ViewHolderListadoEntradas();
            final ViewHolderListadoEntradas vhlo=holder;
            holder = new ViewHolderListadoEntradas();
            holder.nombre = (TextView)item.findViewById(R.id.lblNombreEntrada);
            holder.precio=(TextView)item.findViewById(R.id.lblPrecioEntrada);
            holder.descripcion=(TextView)item.findViewById(R.id.lblDescripcionEntrada);
            item.setTag(holder);
            LinearLayout hl=(LinearLayout)v.findViewById(R.id.hLayoutCarta);
            final TextView tv=new TextView(context);
            tv.setText("0");
            tv.setId(R.id.but);
            hl.addView(tv);

            ((LinearLayout)item.findViewById(R.id.LLayoutCarta)).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v)
                {

                    for(int i=0;i<((ListView)context.findViewById(R.id.ll)).getChildCount();i++) {
                        View a=(LinearLayout)((ListView)context.findViewById(R.id.ll)).getChildAt(i).findViewById(R.id.LLayoutCarta);
                        if(a instanceof LinearLayout) {
                            if(((LinearLayout)((LinearLayout)a).findViewById(R.id.hLayoutCarta)).getChildCount()!=3) {
                                LinearLayout ll= (LinearLayout) ((LinearLayout) a).findViewById(R.id.hLayoutCarta);
                                TextView textView2=(TextView)ll.findViewById(R.id.lblPrecioEntrada);
                                LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                llp.setMargins(10, 0, 100, 0); // llp.setMargins(left, top, right, bottom);
                                textView2.setLayoutParams(llp);
                                ((LinearLayout) ((LinearLayout) a).findViewById(R.id.hLayoutCarta)).removeView(((LinearLayout) ((LinearLayout) a).findViewById(R.id.hLayoutCarta)).getChildAt(2));
                                ((LinearLayout) ((LinearLayout) a).findViewById(R.id.hLayoutCarta)).removeView(((LinearLayout) ((LinearLayout) a).findViewById(R.id.hLayoutCarta)).getChildAt(3));
                            }
                        }
                    }
                    // ((ViewGroup)v.getParent().getParent()).getId();
                    // hl.removeView(aux);

                    LinearLayout hl=(LinearLayout)v.findViewById(R.id.hLayoutCarta);
                    final TextView aux=(TextView)hl.findViewById(R.id.lblCantidadProd);
                    final TextView textView=(TextView)hl.findViewById(R.id.lblPrecioEntrada);
                    LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    llp.setMargins(10, 0, 20, 0); // llp.setMargins(left, top, right, bottom);
                    textView.setLayoutParams(llp);

                    Button but=new Button(context);
                    but.setText("▲");
                    but.setTextSize(40);
                    but.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            int auxInt = Integer.parseInt(tv.getText().toString()) + 1;
                            for(int i=0;i<((ListView)context.findViewById(R.id.ll)).getChildCount();i++) {
                                View a=(LinearLayout)((ListView)context.findViewById(R.id.ll)).getChildAt(i).findViewById(R.id.LLayoutCarta);
                                if(a instanceof LinearLayout) {
                                    if(((LinearLayout)((LinearLayout)a).findViewById(R.id.hLayoutCarta)).getChildCount()!=3) {
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
                    but.setTextSize(40);
                    but.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v)
                        {
                            int auxInt=Integer.parseInt(tv.getText().toString());
                            if(auxInt>0) {
                                auxInt--;
                                for(int i=0;i<((ListView)context.findViewById(R.id.ll)).getChildCount();i++) {
                                    View a=(LinearLayout)((ListView)context.findViewById(R.id.ll)).getChildAt(i).findViewById(R.id.LLayoutCarta);
                                    if(a instanceof LinearLayout) {
                                        if(((LinearLayout)((LinearLayout)a).findViewById(R.id.hLayoutCarta)).getChildCount()!=3) {
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
            v.setTag(holder);
        }
        else
        {
            holder = (ViewHolderListadoEntradas)item.getTag();
        }

        holder.nombre.setText(e.getProducto().getNombre());
        holder.precio.setText(e.getPrecio()+"€");
        holder.descripcion.setText(e.getDescripcion());
        return(item);
    }

    public void filter(String charText) {
        //Log.w("-", charText.toString());
        charText = charText.toLowerCase(Locale.getDefault());
        listadoEntrada.clear();
        if (charText.length() == 0) {
            listadoEntrada.addAll(listadoEntradaAux);
        }
        else
        {
            for (Entrada e : listadoEntradaAux)
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
        TextView nombre;
        TextView precio;
        TextView descripcion;
    }
}
