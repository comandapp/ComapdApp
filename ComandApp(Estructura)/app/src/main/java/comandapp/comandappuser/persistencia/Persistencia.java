package comandapp.comandappuser.persistencia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import comandapp.comandappuser.objetos.*;
import comandapp.comandappuser.utilidades.ImgCodec;

/**
 * Created by G62 on 13-Mar-15.
 */
public class Persistencia {

    private static Persistencia instancia = null;

    private static SQLHelper sql = null;

    private Persistencia(Context c) {
        sql = new SQLHelper(c, "ComandappClient", null, 1);
    }

    public static Persistencia getInstancia(Context c) {
        if(instancia == null) instancia = new Persistencia(c);
        return instancia;
    }

    public HashMap<Integer,Version> getMain() {
        HashMap<Integer,Version> main = new HashMap<Integer,Version>();

        SQLiteDatabase dbr = sql.getReadableDatabase();
        Cursor c = dbr.rawQuery("SELECT Id_Bar, VersionInfoBar, VersionCarta, VersionOfertas FROM bar", null);
        while(c.moveToNext()) {
            main.put(c.getInt(0), new Version(c.getInt(1), c.getInt(2), c.getInt(3)));
        }

        dbr.close();
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
    public ArrayList<Bar> getBares(int[] idArray, int opcion) {
        ArrayList<Bar> bares = new ArrayList<Bar>();

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

        if(opcion == 1) rellenaBares(bares, dbr);

        dbr.close();
        return bares;
    }

    private void rellenaBares(ArrayList<Bar> bares, SQLiteDatabase dbr) {
        for(Bar bar : bares) {
            Cursor c = dbr.rawQuery("SELECT * FROM carta INNER JOIN producto ON carta.Id_Producto = producto.Id_Producto WHERE carta.Id_Bar = ?;", new String[]{Integer.toString(bar.getId())});
            while (c.moveToNext()) {
                bar.getCarta().aniadeEntrada(new Entrada(
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
                    p = getProducto(s);
                    if (p != null) o.getProductos().add(p);
                    else {/*Producto no existente*/}
                }
                bar.getOfertas().add(o);
            }
        }
    }

    private Producto getProducto(String id) {
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

    public static boolean existeBar(Bar b) {
        SQLiteDatabase dbr = sql.getReadableDatabase();
        Cursor c = dbr.rawQuery("SELECT Id_Bar FROM bar WHERE Id_Bar = ?;", new String[]{ Integer.toString(b.getId()) });

        if(c.moveToFirst()) {
            dbr.close();
            return true;
        }
        dbr.close();
        return false;
    }

    private static boolean existeProducto(Producto p) {
        SQLiteDatabase dbr = sql.getReadableDatabase();
        Cursor c = dbr.rawQuery("SELECT Id_Producto FROM producto WHERE Id_Producto = ?;", new String[]{ Integer.toString(p.getId()) });

        if(c.moveToFirst()) {
            dbr.close();
            return true;
        }
        dbr.close();
        return false;
    }

    public static boolean insertarBar(Bar b) {
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
            dbw.close();
            return false;
        }
    }

    public static boolean eliminarBar(Bar b) {
/*        SQLiteDatabase dbw = sql.getWritableDatabase();
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
            dbw.close();
            return false;
        }*/
        return false;
    }

    private static void insertaProducto(Producto p, SQLiteDatabase dbw) {
        dbw.execSQL("INSERT INTO producto (Id_Producto, Nombre, Categoria) VALUES ("+p.getId()+", "+p.getNombre()+", "+p.getCategoria()+");");
    }

    public void close() {
        if(sql != null) sql.close();
    }
}
