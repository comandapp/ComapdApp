package comandapp.comandappuser.persistencia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import comandapp.comandappuser.objetos.Bar;
import comandapp.comandappuser.objetos.Carta;
import comandapp.comandappuser.objetos.Linea_Carta;
import comandapp.comandappuser.objetos.Oferta;
import comandapp.comandappuser.objetos.Producto;
import comandapp.comandappuser.objetos.Version;
import comandapp.comandappuser.utilidades.ImgCodec;

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

    public HashMap<Integer,Version> getMain(Context con) {
        SQLiteOpenHelper sql = new SQLHelper(con, "ComandappClient", null, 1);
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

    public HashMap<Integer,Version> getBaresMain(Context con, int[] idArray) {
        SQLiteOpenHelper sql = new SQLHelper(con, "ComandappClient", null, 1);
        HashMap<Integer,Version> main = new HashMap<Integer,Version>();

        SQLiteDatabase dbr = sql.getReadableDatabase();
        Cursor c;
        String idArrayString = null;
        if(idArray != null && idArray.length > 0) {
            idArrayString = idArray.toString();
            //idArrayString ahora vale = "[23,343,33,55,43]"
            idArrayString = idArrayString.replace("[", "(");
            idArrayString = idArrayString.replace("]", ")");
            //Ahora "(23,343,33,55,43)" por lo que sirve para un SELECT
            c = dbr.rawQuery("SELECT Id_Bar, VersionInfoBar, VersionCarta, VersionOfertas FROM bar WHERE Id_Bar in "+idArrayString+";", null);
        } else {
            c = dbr.rawQuery("SELECT Id_Bar, VersionInfoBar, VersionCarta, VersionOfertas FROM bar;", null);
        }

        Version v;
        while(c.moveToNext()) {
            v = new Version(
                    c.getInt(1),
                    c.getInt(2),
                    c.getInt(3)
            );

            main.put(c.getInt(0),v);
        }

        dbr.close();
        sql.close();
        return main;
    }

    //Obtiene los objetos Bar almacenados.
    //Argumentos:
    // - idArray
    //Si se le pasa un array de identificadores devuelve los bares correspondientes
    //Si se le pasa null o la lista vacía devuelve todos los existentes
    // - opcion
    // 0 = Devuelve los bares sin cartas ni ofertas
    // 1 = Devuelve los bares con sus cartas y ofertas
    public void getBaresIntoArray(Context con, int[] idArray, ArrayList<Bar> bares, int opcion) {
        SQLiteOpenHelper sql = new SQLHelper(con, "ComandappClient", null, 1);

        SQLiteDatabase dbr = sql.getReadableDatabase();
        Cursor c;
        String idArrayString = null;
        if(idArray != null && idArray.length > 0) {
            idArrayString = idArray.toString();
            //idArrayString ahora vale = "[23,343,33,55,43]"
            idArrayString = idArrayString.replace("[", "(");
            idArrayString = idArrayString.replace("]", ")");
            //Ahora "(23,343,33,55,43)" por lo que sirve para un SELECT
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
                    c.getInt(3),
                    c.getString(9),
                    c.getDouble(5),
                    c.getDouble(4),
                    c.getString(6),
                    c.getString(7),
                    ImgCodec.base64ToBitmap(c.getString(8)),
                    c.getInt(10)
            );
            b.getVersion().setVersionCarta(c.getInt(11));
            b.getVersion().setVersionOfertas(c.getInt(12));

            bares.add(b);
        }

        if(opcion == 1) rellenaBares((SQLHelper)sql, bares, dbr);

        dbr.close();
        sql.close();
    }

    private void rellenaBares(SQLHelper sql, ArrayList<Bar> bares, SQLiteDatabase dbr) {
        for(Bar bar : bares) {
            Cursor c = dbr.rawQuery("SELECT * FROM carta INNER JOIN producto ON carta.Id_Producto = producto.Id_Producto WHERE carta.Id_Bar = ?;", new String[]{Integer.toString(bar.getId())});
            while (c.moveToNext()) {
                bar.getCarta().aniadeEntrada(new Linea_Carta(
                        new Producto(
                                c.getInt(0),
                                c.getString(6),
                                c.getString(7)),
                        c.getDouble(2),
                        c.getString(3),
                        c.getString(4)));
            }

            Oferta o;
            Producto p;

            c = dbr.rawQuery("SELECT * FROM oferta WHERE Id_Bar = ?;", new String[]{Integer.toString(bar.getId())});
            while (c.moveToNext()) {
                o = new Oferta(
                        c.getInt(0),
                        c.getDouble(2),
                        "",
                        ImgCodec.base64ToBitmap(c.getString(4))
                );
                String[] idArray = c.getString(3).split(",");
                for (String s : idArray) {
                    p = getProducto(sql, s);
                    if (p != null) o.getProductos().add(p);
                    else {/*Producto no existente*/}
                }
                bar.getOfertas().add(o);
            }
        }
        dbr.close();
    }

    private Producto getProducto(SQLiteOpenHelper sql, String id) {
        Producto p = null;

        SQLiteDatabase dbr = sql.getReadableDatabase();
        Cursor c = dbr.rawQuery("SELECT * FROM producto WHERE Id_Producto = ?;", new String[]{ id });

        if(c.moveToFirst()) {
            p = new Producto(c.getInt(0), c.getString(1), c.getString(2));
            dbr.close();
            return p;
        }

        dbr.close();
        return null;
    }

    public static boolean existeBar(Context con, int id) {
        SQLiteOpenHelper sql = new SQLHelper(con, "ComandappClient", null, 1);
        SQLiteDatabase dbr = sql.getReadableDatabase();
        Cursor c = dbr.rawQuery("SELECT Id_Bar FROM bar WHERE Id_Bar = ?;", new String[]{ Integer.toString(id) });

        if(c.moveToFirst()) {
            dbr.close();
            return true;
        }
        dbr.close();
        return false;
    }

    private static boolean existeProducto(SQLHelper sql, Producto p) {
        SQLiteDatabase dbr = sql.getReadableDatabase();
        Cursor c = dbr.rawQuery("SELECT Id_Producto FROM producto WHERE Id_Producto = ?;", new String[]{ Integer.toString(p.getId()) });

        if(c.moveToFirst()) {
            dbr.close();
            return true;
        }
        dbr.close();
        return false;
    }

    public static boolean insertarBar(Context con, Bar b) {
        SQLiteOpenHelper sql = new SQLHelper(con, "ComandappClient", null, 1);
        SQLiteDatabase dbw = sql.getWritableDatabase();
        try {
            Linea_Carta e;
            for (int i = 0; i < b.getCarta().numEntradas(); i++) {
                e = b.getCarta().getEntrada(i);
                if (!existeProducto((SQLHelper)sql, e.getProducto())) insertaProducto(e.getProducto(), dbw);
                dbw.execSQL("INSERT INTO carta (Id_Producto, Id_Bar, Precio, Descripcion, Foto) VALUES (" +
                        e.getProducto().getId() + ", " +
                        b.getId() + ", " +
                        e.getPrecio() + ", " +
                        e.getDescripcion() + ", " +
                        e.getFoto() + ");");
            }

            Oferta o;
            for (int i = 0; i < b.getOfertas().size(); i++) {
                o = b.getOfertas().get(i);
                String productString = "";
                for (Producto p : o.getProductos()) {
                    productString += p.getId() + ",";
                }
                productString = productString.substring(0, productString.length() - 1);
                dbw.execSQL("INSERT INTO oferta (Id_Oferta, Id_Bar, Precio, Descripcion, Productos, Foto) VALUES (" +
                        o.getId() + ", " +
                        b.getId() + ", " +
                        o.getPrecio() + ", " +
                        o.getDescripcion() + ", " +
                        productString + ", " +
                        o.getFoto() + ");");
            }



            return true;
        } catch(Exception e) {
            return false;
        } finally {
            dbw.close();
            sql.close();
        }
    }

    public static void actualizaInfoBar(Context con, ArrayList<Bar> bares) {
        SQLiteOpenHelper sql = new SQLHelper(con, "ComandappClient", null, 1);
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
                    "Foto='" +b.getFoto()+"',"+
                    "Correo='" +b.getCorreo()+"',"+
                    "VersionInfoBar=" +b.getVersion().getVersionInfoLocal()+","+
                    "VersionCarta=" +b.getVersion().getVersionCarta()+","+
                    "VersionOfertas=" +b.getVersion().getVersionOfertas()+" "+
                    "WHERE Id_Bar=" +b.getId()+");");

        }
        dbw.close();
        sql.close();
    }

    public static void insertarCarta(Context con, int id_Bar, Linea_Carta e) {
        SQLiteOpenHelper sql = new SQLHelper(con, "ComandappClient", null, 1);
        SQLiteDatabase dbw = sql.getWritableDatabase();

        dbw.execSQL("INSERT INTO carta(Id_Producto, Id_Bar, Precio, Descripcion, Foto) VALUES( " +
                e.getProducto().getId() + ", " +
                id_Bar + ", " +
                e.getPrecio() + ", " +
                "'" + e.getDescripcion() + "', " +
                "'" + ImgCodec.bitmapToBase64(e.getFoto()) + "');");
        dbw.close();
        sql.close();
    }

    public static void actualizaCarta(Context con, HashMap<Integer,Carta> cartas) {
        SQLiteOpenHelper sql = new SQLHelper(con, "ComandappClient", null, 1);
        SQLiteDatabase dbw = sql.getWritableDatabase();

        for(Map.Entry<Integer,Carta> tupla : cartas.entrySet()) {
            Linea_Carta e;//Se recorren las entradas de cada carta comprobando si existe el producto
            for(int i=0;i<tupla.getValue().numEntradas();i++) {
                e = tupla.getValue().getEntrada(i);
                //Si no existe el producto se inserta
                if (!existeProducto((SQLHelper) sql, e.getProducto())) {
                    insertaProducto(e.getProducto(), dbw);
                }

                dbw.execSQL("UPDATE carta SET " +
                        "Precio=" + e.getPrecio() + ", " +
                        "Descripcion='" + e.getDescripcion() + "', " +
                        "Foto='" + ImgCodec.bitmapToBase64(e.getFoto()) + "' " +
                        "WHERE Id_Producto=" + e.getProducto().getId() + " AND " +
                        "Id_Bar=" + tupla.getKey() + ");");

            }
        }
        dbw.close();
        sql.close();
    }

    private static boolean existeEntradaEnCarta(SQLHelper sql, int id_Bar, Linea_Carta e) {
        SQLiteDatabase dbr = sql.getReadableDatabase();
        Cursor c = dbr.rawQuery("SELECT Id_Bar FROM carta WHERE Id_Bar="+id_Bar+" AND Id_Producto="+e.getProducto().getId()+";", null);

        if(c.moveToFirst()) {
            dbr.close();
            return true;
        }
        dbr.close();
        return false;
    }

    private static void actualizarOfertas(Context con, HashMap<Integer, ArrayList<Oferta>> listaOfertas) {
        SQLiteOpenHelper sql = new SQLHelper(con, "ComandappClient", null, 1);
        SQLiteDatabase dbw = sql.getWritableDatabase();

        for(Map.Entry<Integer, ArrayList<Oferta>> tupla : listaOfertas.entrySet()) {
            for(Oferta o : tupla.getValue()) {

                String idArrayProductos = "";
                for(Producto p : o.getProductos()) {
                    //Si no existe el producto se inserta
                    if (!existeProducto((SQLHelper) sql, p)) {
                        insertaProducto(p, dbw);
                    }
                    idArrayProductos += p.getId()+",";
                }
                idArrayProductos = idArrayProductos.substring(0, idArrayProductos.length()-1);

                dbw.execSQL("UPDATE oferta SET " +
                        "Precio=" + o.getPrecio() + ", " +
                        "Descripcion='" + o.getDescripcion() + "', " +
                        "Productos='" + idArrayProductos + "', " +
                        "Foto='" + ImgCodec.bitmapToBase64(o.getFoto()) + "' " +
                        "WHERE Id_Oferta=" + o.getId() + " AND " +
                        "Id_Bar=" + tupla.getKey() + ");");
            }
        }
    }

    public static boolean eliminarBar(Context con, Bar b) {
/*      SQLiteOpenHelper sql = new SQLHelper(con, "ComandappClient", null, 1);
        SQLiteDatabase dbw = sql.getWritableDatabase();
        try {
            Entrada e;
            for (int i = 0; i < b.getCarta().numEntradas(); i++) {
                e = b.getCarta().getEntrada(i);
                if (!existeProducto(e.getProducto())) insertaProducto(e.getProducto(), dbw);
                dbw.execSQL("INSERT INTO carta (Id_Producto, Id_Bar, Precio, Descripcion, Foto) VALUES (" +
                        e.getProducto().getId() + ", " +
                        b.getId() + ", " +
                        e.getPrecio() + ", " +
                        e.getDescripcion() + ", " +
                        e.getFoto() + ");");
            }

            Oferta o;
            for (int i = 0; i < b.getOfertas().size(); i++) {
                o = b.getOfertas().get(i);
                String productString = "";
                for (Producto p : o.getProductos()) {
                    productString += p.getId() + ",";
                }
                productString = productString.substring(0, productString.length() - 1);
                dbw.execSQL("INSERT INTO oferta (Id_Oferta, Id_Bar, Precio, Descripcion, Productos, Foto) VALUES (" +
                        o.getId() + ", " +
                        b.getId() + ", " +
                        o.getPrecio() + ", " +
                        o.getDescripcion() + ", " +
                        productString + ", " +
                        o.getFoto() + ");");
            }

            dbw.execSQL("INSERT INTO bar(" +
                    "Id_Bar, Nombre, Direccion, Telefono, Longitud, Latitud," +
                    " Provincia, Municipio, Foto, Correo, VersionInfoBar, VersionCarta, VersionOfertas) VALUES (" +
                    b.getId() + ", " +
                    b.getNombre() + ", " +
                    b.getDireccion() + ", " +
                    b.getTelefono() + ", " +
                    b.getLongitud() + ", " +
                    b.getLatitud() + ", " +
                    b.getProvincia() + ", " +
                    b.getMunicipio() + ", " +
                    b.getFoto() + ", " +
                    b.getCorreo() + ", " +
                    b.getVersion().getVersionInfoLocal() + ", " +
                    b.getVersion().getVersionCarta() + ", " +
                    b.getVersion().getVersionOfertas() + ");");

            dbw.close();
            return true;
        } catch(Exception e) {
            return false;
        }*/
        return false;
    }

    private static void insertaProducto(Producto p, SQLiteDatabase dbw) {
        dbw.execSQL("INSERT INTO producto (Id_Producto, Nombre, Categoria) VALUES ("+p.getId()+", "+p.getNombre()+", "+p.getCategoria()+");");
    }
}
