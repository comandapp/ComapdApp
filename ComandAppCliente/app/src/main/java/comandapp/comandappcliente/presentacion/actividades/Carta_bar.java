package comandapp.comandappcliente.presentacion.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import comandapp.comandappcliente.R;
import comandapp.comandappcliente.logicanegocio.LogicaNegocio;
import comandapp.comandappcliente.logicanegocio.objetos.Bar;
import comandapp.comandappcliente.logicanegocio.objetos.LineaCarta;
import comandapp.comandappcliente.logicanegocio.objetos.LineaComanda;
import comandapp.comandappcliente.logicanegocio.objetos.LineaComandaEnCurso;
import comandapp.comandappcliente.presentacion.adaptadores.AdaptadorCarta;


public class Carta_bar extends ActionBarActivity {

    AdaptadorCarta adaptador;
    ListView lstEntradas;
    Bar bar=null;
    ArrayList<LineaComandaEnCurso> lineasComanda;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carta_bar);

        final int id_Bar = this.getIntent().getExtras().getInt("id_bar");
        //Comanda c=LogicaNegocio.getInstancia().getComandaTemporal(this)
        //if(c==null)
        ArrayList<LineaCarta> carta = LogicaNegocio.getInstancia().getCarta(this,id_Bar);

        if(carta.size() == 0)
        {
            //No hay elementos en la carta !!! Mostrar mensaje al usuario
        }
        else
        {
            lineasComanda=new ArrayList<LineaComandaEnCurso>();

            if(LogicaNegocio.getInstancia().getLineasComandaEnCurso(this)==null) {
                for (LineaCarta lC : carta) {
                    System.out.println(lC.getProducto().getId());
                    lineasComanda.add(new LineaComandaEnCurso(lC.getProducto().getId(), 0));
                }
            }else{
                lineasComanda=LogicaNegocio.getInstancia().getLineasComandaEnCurso(this);
                for(int i=0;i<lineasComanda.size();i++){
                    System.out.println(lineasComanda.get(i).getIdProducto());
                }
                for (LineaCarta lC : carta) {
                    boolean enc=false;
                    for(LineaComandaEnCurso lcc:lineasComanda) {
                        if (lC.getProducto().getId() == lcc.getIdProducto()) {
                            enc = true;
                        }
                    }
                    if(!enc){
                        lineasComanda.add(new LineaComandaEnCurso(lC.getProducto().getId(), 0));
                    }
                }

            }
            adaptador = new AdaptadorCarta(this, carta, LogicaNegocio.getInstancia().getOfertas(this,id_Bar),lineasComanda);
            lstEntradas = (ListView)findViewById(R.id.listaCarta);
            lstEntradas.setAdapter(adaptador);
        }



        //else Si la comanda no es null
        /*
        Cargamos los objetos desde comanda a lComanda
         */

        Button btnMenuInicio = (Button)findViewById(R.id.btnMenuInicio);
        Button btnMenuOfertas = (Button)findViewById(R.id.btnMenuOfertas);
        Button btnMenuComanda = (Button)findViewById(R.id.btnMenuComanda);

        btnMenuInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Carta_bar.this, InicioBar.class);
                intent.putExtra("id_bar",id_Bar);
                startActivity(intent);
            }
        });



        btnMenuComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Carta_bar.this, ComandaDetallada.class);
                intent.putExtra("id_bar", id_Bar);
                startActivity(intent);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_carta_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.menuHistorial)
        {
            Intent intent = new Intent(this, HistorialComandas.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
