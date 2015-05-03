package comandapp.comandappcliente.persistencia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import comandapp.comandappcliente.logicanegocio.objetos.*;
import comandapp.comandappcliente.logicanegocio.utilidades.ImgCodec;

/**
 * Created by G62 on 13-Mar-15.
 */
public class Persistencia {

    private static Persistencia instancia = null;

    private Persistencia() {

    }

    public static Persistencia getInstancia() {
        if(instancia == null) {
            instancia = new Persistencia();
        }
        return instancia;
    }

    private SQLHelper getSQL(Context con) {
        return new SQLHelper(con, "ComandappClient", null, 5);
    }

    //Obtiene el MAIN completo de la base de datos y lo devuelve en forma de HashMap junto al idBar correspondiente
    public HashMap<Integer,Version> getMain(Context con) {
        SQLiteOpenHelper sql = getSQL(con);
        HashMap<Integer,Version> main = new HashMap<Integer,Version>();

        SQLiteDatabase dbr = sql.getReadableDatabase();
        Cursor c = dbr.rawQuery("SELECT Id_Bar, VersionInfoBar, VersionCarta, VersionOfertas FROM bar", null);
        while(c.moveToNext()) {
            main.put(c.getInt(0), new Version(c.getInt(1), c.getInt(2), c.getInt(3)));
        }

        dbr.close();
        sql.close();
        return main;
    }



    //BARES-----------------------------------------------------------------------------------------
    //Obtiene los objetos Bar almacenados en la base de datos.
    //Argumentos:
    // - idArray
        //Si se le pasa un array de identificadores devuelve los bares correspondientes
        //Si se le pasa null o la lista vacía devuelve todos los existentes
    // - opcion
        // false = Devuelve los bares sin cartas ni ofertas
        // true = Devuelve los bares con sus cartas y ofertas
    public ArrayList<Bar> getBares(Context con, int[] idArray, boolean opcion) {
        ArrayList<Bar> ret = new ArrayList<Bar>();

        SQLiteOpenHelper sql = getSQL(con);
        SQLiteDatabase dbr = sql.getReadableDatabase();

        Cursor c;
        String idArrayString = null;
        if(idArray != null && idArray.length > 0) {
            idArrayString = idArrayToSqlSelect(idArray);
            c = dbr.rawQuery("SELECT * FROM bar WHERE Id_Bar in "+idArrayString+";", null);
        } else {
            c = dbr.rawQuery("SELECT * FROM bar;", null);
        }

        Bar b;
        while(c.moveToNext()) {
            b = new Bar(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(10),
                    c.getDouble(5),
                    c.getDouble(4),
                    c.getString(6),
                    c.getString(7),
                    ImgCodec.base64ToBitmap(c.getString(8)),
                    (c.getInt(9) == 1) ? true : false,
                    c.getInt(11)
            );
            b.getVersion().setVersionCarta(c.getInt(12));
            b.getVersion().setVersionOfertas(c.getInt(13));

            ret.add(b);
        }

        if(opcion) rellenaBares(con, ret);

        dbr.close();
        sql.close();

        return ret;
    }

    //Función privada auxiliar. Rellena una lista de objetos Bar con sus cartas y ofertas correspondientes
    private void rellenaBares(Context con, ArrayList<Bar> bares) {
        for(Bar bar : bares) {
            ArrayList<LineaCarta> c = getCarta(con, bar.getIdBar());
            ArrayList<Oferta> o = getOfertas(con, bar.getIdBar());
            bar.setCarta(c);
            bar.setOfertas(o);
        }
    }

    public void actualizaFavoritoBar(Context con, Bar b) {
        SQLiteOpenHelper sql = getSQL(con);
        SQLiteDatabase dbw = sql.getWritableDatabase();

        dbw.execSQL("UPDATE bar SET " +
                "Favorito="+((b.getFavorito()) ? 1 : 0)+" "+
                "WHERE Id_Bar=" +b.getIdBar()+";");

        dbw.close();
        sql.close();
    }

    public void actualizaInfoBar(Context con, ArrayList<Bar> bares) {
        SQLiteOpenHelper sql = getSQL(con);
        SQLiteDatabase dbw = sql.getWritableDatabase();

        for(Bar b : bares) {
            dbw.execSQL("UPDATE bar SET " +
                    "Nombre='" +b.getNombre()+"',"+
                    "Direccion='" +b.getDireccion()+"',"+
                    "Telefono=" +b.getTelefono()+","+
                    "Longitud=" +b.getLongitud()+","+
                    "Latitud=" +b.getLatitud()+","+
                    "Provincia='" +b.getProvincia()+"',"+
                    "Municipio='" +b.getMunicipio()+"',"+
                    "Foto='" +ImgCodec.bitmapToBase64(b.getFoto())+"',"+
                    "Favorito="+((b.getFavorito()) ? 1 : 0)+","+
                    "Correo='" +b.getCorreo()+"',"+
                    "VersionInfoBar=" +b.getVersion().getVersionInfoLocal()+","+
                    "VersionCarta=" +b.getVersion().getVersionCarta()+","+
                    "VersionOfertas=" +b.getVersion().getVersionOfertas()+" "+
                    "WHERE Id_Bar=" +b.getIdBar()+";");

        }
        dbw.close();
        sql.close();
    }

    public boolean existeBar(Context con, int id) {
        SQLiteOpenHelper sql = getSQL(con);
        SQLiteDatabase dbr = sql.getReadableDatabase();
        Cursor c = dbr.rawQuery("SELECT Id_Bar FROM bar WHERE Id_Bar = ?;", new String[]{ Integer.toString(id) });

        if(c.moveToFirst()) {
            dbr.close();
            return true;
        }
        dbr.close();
        return false;
    }

    //Función privada auxiliar. Genera un string adecuado para una consulta "SELECT * WHERE id IN _"
    private String idArrayToSqlSelect(int[] idArray) {
        String ret = "(";
        for(int i : idArray) {
            ret+= i+",";
        }
        ret = ret.substring(0,ret.length()-1);
        ret += ")";
        return ret;
    }

    //Inserta un nuevo bar en la base de datos.
    //Si algún producto de la carta no existe se crea también.
    public boolean insertarBar(Context con, Bar b) {
        if(existeBar(con,b.getIdBar())) {
            return false;
        } else {
            SQLiteOpenHelper sql = getSQL(con);
            SQLiteDatabase dbw = sql.getWritableDatabase();

            try {
                dbw.execSQL("INSERT INTO bar (Id_Bar, Nombre, Direccion, Telefono, Latitud, Longitud, Provincia, Municipio, Foto, Favorito, Correo, VersionInfoBar, VersionCarta, VersionOfertas) VALUES (" +
                        b.getIdBar() + ", " +
                        b.getNombre() + ", " +
                        b.getDireccion() + ", " +
                        b.getTelefono() + ", " +
                        b.getLatitud() + ", " +
                        b.getLongitud() + ", " +
                        b.getProvincia() + ", " +
                        b.getMunicipio() + ", " +
                        ImgCodec.bitmapToBase64(b.getFoto()) + ", " +
                        ((b.getFavorito()) ? 1 : 0) + ", " +
                        b.getCorreo() + ", " +
                        b.getVersion().getVersionInfoLocal() + ", " +
                        b.getVersion().getVersionCarta() + ", " +
                        b.getVersion().getVersionOfertas() + ");");

                for (LineaCarta e : b.getCarta()) {
                    if (!existeProducto((SQLHelper) sql, e.getProducto()))
                        insertaProducto(e.getProducto(), dbw);
                    dbw.execSQL("INSERT INTO carta (Id_Producto, Id_Bar, Precio, Descripcion) VALUES (" +
                            e.getProducto().getId() + ", " +
                            b.getIdBar() + ", " +
                            e.getPrecio() + ", " +
                            e.getDescripcion() + ");");
                }

                Oferta o;
                for (int i = 0; i < b.getOfertas().size(); i++) {
                    o = b.getOfertas().get(i);
                    insertarOferta(con, o);
                }


                return true;
            } catch (Exception e) {
                return false;
            } finally {
                dbw.close();
                sql.close();
            }
        }
    }

    /*
    public boolean eliminarBar(Context con, Bar b) {
        SQLiteOpenHelper sql = getSQL(con);
        SQLiteDatabase dbw = sql.getWritableDatabase();
        try {
            Linea_Carta e;
            for (int i = 0; i < b.getCarta().numLineas(); i++) {
                e = b.getCarta().getLinea(i);
                dbw.execSQL();
            }

            Oferta o;
            for (int i = 0; i < b.getOfertas().size(); i++) {
                o = b.getOfertas().get(i);
                dbw.execSQL("");
            }

            dbw.execSQL("");

            dbw.close();
            return true;
        } catch(Exception e) {
            return false;
        }
        return false;
    }
    */






    //CARTA ----------------------------------------------------------------------------------------
    public ArrayList<LineaCarta> getCarta(Context con, int id_Bar) {
        ArrayList<LineaCarta> carta = new ArrayList<LineaCarta>();
        SQLiteOpenHelper sql = getSQL(con);
        SQLiteDatabase dbr = sql.getReadableDatabase();

        Cursor c = dbr.rawQuery("SELECT carta.Id_Producto," +
                "carta.Precio," +
                "carta.Descripcion," +
                "producto.Nombre," +
                "producto.Categoria," +
                "producto.Foto FROM " +
                "carta INNER JOIN producto ON carta.Id_Producto = producto.Id_Producto " +
                "WHERE carta.Id_Bar = ?;", new String[]{Integer.toString(id_Bar)});

        while (c.moveToNext()) {
            carta.add(new LineaCarta(
                    new Producto(c.getInt(0),c.getString(3),c.getString(4),ImgCodec.base64ToBitmap(c.getString(5))),
                    c.getDouble(1),
                    c.getString(2)));
        }

        return carta;
    }

    public void insertarCarta(Context con, int id_Bar, LineaCarta e) {
        SQLiteOpenHelper sql = getSQL(con);
        SQLiteDatabase dbw = sql.getWritableDatabase();

        dbw.execSQL("INSERT INTO carta (Id_Producto, Id_Bar, Precio, Descripcion) VALUES( " +
                e.getProducto().getId() + ", " +
                id_Bar + ", " +
                e.getPrecio() + ", " +
                "'" + e.getDescripcion() + "');");
        dbw.close();
        sql.close();
    }

    private boolean existeLineaEnCarta(SQLHelper sql, int id_Bar, LineaCarta e) {
        SQLiteDatabase dbr = sql.getReadableDatabase();
        Cursor c = dbr.rawQuery("SELECT Id_Bar FROM carta WHERE Id_Bar="+id_Bar+" AND Id_Producto="+e.getProducto().getId()+";", null);

        if(c.moveToFirst()) {
            dbr.close();
            return true;
        }
        dbr.close();
        return false;
    }

    public void actualizaCarta(Context con, HashMap<Integer,ArrayList<LineaCarta>> cartas) {
        SQLiteOpenHelper sql = getSQL(con);
        SQLiteDatabase dbw = sql.getWritableDatabase();

        for(Map.Entry<Integer,ArrayList<LineaCarta>> tupla : cartas.entrySet()) {
            //Se recorren las entradas de cada carta comprobando si existe el producto
            for(LineaCarta linea : tupla.getValue()) {
                //Si no existe el producto se inserta
                if (!existeProducto((SQLHelper) sql, linea.getProducto())) {
                    insertaProducto(linea.getProducto(), dbw);
                }

                dbw.execSQL("UPDATE carta SET " +
                        "Precio=" + linea.getPrecio() + ", " +
                        "Descripcion='" + linea.getDescripcion() + "', " +
                        "WHERE Id_Producto=" + linea.getProducto().getId() + " AND " +
                        "Id_Bar=" + tupla.getKey() + ");");

            }
        }
        dbw.close();
        sql.close();
    }







    //OFERTAS---------------------------------------------------------------------------------------
    public ArrayList<Oferta> getOfertas(Context con, int id_Bar) {
        ArrayList<Oferta> ofertas = new ArrayList<Oferta>();
        SQLiteOpenHelper sql = getSQL(con);
        SQLiteDatabase dbr = sql.getReadableDatabase();

        Cursor c = dbr.rawQuery("SELECT * FROM oferta WHERE Id_Bar = ?;", new String[]{ ""+id_Bar });

        while(c.moveToNext()) {
            ofertas.add(new Oferta(c.getInt(1), getProduct(sql, c.getInt(0)), c.getString(3),c.getDouble(2)));
        }
        return ofertas;
    }

    public ArrayList<Oferta> getOfertasByProducto(Context con, int id_Producto) {
        ArrayList<Oferta> ofertas = new ArrayList<Oferta>();
        SQLiteOpenHelper sql = getSQL(con);
        SQLiteDatabase dbr = sql.getReadableDatabase();

        Cursor c = dbr.rawQuery("SELECT * FROM oferta WHERE Id_Producto = ?;", new String[]{ ""+id_Producto });

        while(c.moveToNext()) {
            ofertas.add(new Oferta(c.getInt(1), getProduct(sql, c.getInt(0)), c.getString(3),c.getDouble(2)));
        }
        return ofertas;
    }

    public void insertarOferta(Context con, Oferta o) {
        SQLiteOpenHelper sql = getSQL(con);
        SQLiteDatabase dbw = sql.getWritableDatabase();

        if (!existeProducto((SQLHelper) sql, o.getProducto())) {
            insertaProducto(o.getProducto(), dbw);
        }

        dbw.execSQL("INSERT INTO oferta (Id_Bar, Id_Producto, Precio, Descripcion) VALUES (" +
                o.getIdBar() + ", " +
                o.getProducto().getId() + ", " +
                o.getPrecio() + ", " +
                o.getDescripcion() + ");");

        dbw.close();
        sql.close();
    }




    //PRODUCTOS-------------------------------------------------------------------------------------
    private Producto getProduct(SQLiteOpenHelper sql, int id) {
        Producto p = null;

        SQLiteDatabase dbr = sql.getReadableDatabase();
        Cursor c = dbr.rawQuery("SELECT * FROM producto WHERE Id_Producto = ?;", new String[]{ ""+id });

        if(c.moveToFirst()) {
            p = new Producto(c.getInt(0), c.getString(1), c.getString(2), ImgCodec.base64ToBitmap(c.getString(3)));
            dbr.close();
            return p;
        }

        dbr.close();
        return null;
    }

    public Producto getProducto(Context con, int id) {
        SQLiteOpenHelper sql = getSQL(con);
        return getProduct(sql, id);
    }

    public ArrayList<Producto> getProductos(Context con, ArrayList<Integer> ids) {
        ArrayList<Producto> productos = new ArrayList<Producto>();

        SQLiteOpenHelper sql = getSQL(con);
        SQLiteDatabase dbr = sql.getReadableDatabase();

        Cursor c = dbr.rawQuery("SELECT * FROM producto", null);

        while(c.moveToNext()) {
            productos.add(new Producto(c.getInt(0), c.getString(1), c.getString(2), ImgCodec.base64ToBitmap(c.getString(3))));
        }

        dbr.close();
        sql.close();
        return null;
    }

    private boolean existeProducto(SQLHelper sql, Producto p) {
        SQLiteDatabase dbr = sql.getReadableDatabase();
        Cursor c = dbr.rawQuery("SELECT Id_Producto FROM producto WHERE Id_Producto = ?;", new String[]{ Integer.toString(p.getId()) });

        if(c.moveToFirst()) {
            dbr.close();
            return true;
        }
        dbr.close();
        return false;
    }

    public void insertaProducto(Producto p, SQLiteDatabase dbw) {
        dbw.execSQL("INSERT INTO producto (Id_Producto, Nombre, Categoria) VALUES ("+p.getId()+", "+p.getNombre()+", "+p.getCategoria()+");");
    }

}
