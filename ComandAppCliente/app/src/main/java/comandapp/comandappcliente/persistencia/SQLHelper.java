package comandapp.comandappcliente.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by G62 on 13-Mar-15.
 * Clase encargada de crear/actualizar la base de datos SQLite en el cliente.
 */
public class SQLHelper extends SQLiteOpenHelper {

    String sqlCreateBar = "CREATE TABLE bar ("+
            "Id_Bar INTEGER,"+
            "Nombre VARCHAR,"+
            "Direccion VARCHAR,"+
            "Telefono VARCHAR,"+
            "Longitud DOUBLE,"+
            "Latitud DOUBLE,"+
            "Provincia VARCHAR,"+
            "Municipio VARCHAR,"+
            "Foto VARCHAR," +
            "Favorito INTEGER," +
            "Correo VARCHAR,"+
            "VersionInfoBar INTEGER,"+
            "VersionCarta INTEGER,"+
            "VersionOfertas INTEGER,"+
            "CONSTRAINT pk_Bar PRIMARY KEY(Id_Bar));";

    String sqlCreateOferta = "CREATE TABLE oferta ("+
            "Id_Producto INTEGER,"+
            "Id_Bar INTEGER,"+
            "Precio DOUBLE,"+
            "Descripcion VARCHAR,"+
            "CONSTRAINT pk_Oferta PRIMARY KEY(Id_Producto,Id_Bar),"+
            "CONSTRAINT fk_Oferta_Bar FOREIGN KEY(Id_Bar) REFERENCES bar(Id_Bar));";

    String sqlCreateProducto = "CREATE TABLE producto ("+
            "Id_Producto INTEGER,"+
            "Nombre VARCHAR,"+
            "Categoria VARCHAR,"+
            "Foto VARCHAR,"+
            "CONSTRAINT pk_Producto PRIMARY KEY(Id_Producto));";

    String sqlCreateComanda = "CREATE TABLE comanda ("+
            "Nombre_comanda VARCHAR,"+
            "Bar INTEGER,"+
            "Fecha datetime default current_timestamp,"+
            "CONSTRAINT pk_Comanda PRIMARY KEY(Nombre_comanda)," +
            "CONSTRAINT fk_LineaBar FOREIGN KEY(Bar) REFERENCES bar(Id_Bar));";

    String sqlCreateLineaComanda = "CREATE TABLE lineaComanda ("+
            "Nombre_comanda VARCHAR,"+
            "Id_Producto INTEGER,"+
            "Cantidad INTEGER,"+
            "CONSTRAINT pk_LineaComanda PRIMARY KEY(Nombre_comanda,Id_Producto),"+
            "CONSTRAINT fk_Comanda FOREIGN KEY(Nombre_comanda) REFERENCES comanda(Nombre_comanda),"+
            "CONSTRAINT fk_LineaProducto FOREIGN KEY(Id_Producto) REFERENCES bar(Id_Producto));";

    String sqlCreateCarta = "CREATE TABLE lineaCarta ("+
            "Id_Producto INTEGER,"+
            "Id_Bar INTEGER,"+
            "Precio DOUBLE,"+
            "Descripcion VARCHAR,"+
            "CONSTRAINT pk_LineaCarta PRIMARY KEY(Id_Producto,Id_Bar),"+
            "CONSTRAINT fk_LineaProducto FOREIGN KEY(Id_Producto) REFERENCES bar(Id_Producto),"+
            "CONSTRAINT fk_LineaBar FOREIGN KEY(Id_Bar) REFERENCES bar(Id_Bar));";

    String sqlCreateLineaComandaEnCurso = "CREATE TABLE lineaComandaEnCurso ("+
            "Id_Producto INTEGER,"+
            "Cantidad INTEGER,"+
            "CONSTRAINT pk_ComandaEnCurso PRIMARY KEY(Id_Producto));";

    public SQLHelper(Context contexto, String nombre, SQLiteDatabase.CursorFactory factory,
                       int version) {
        super(contexto, nombre, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("DROP TABLE IF EXISTS oferta;");
        db.execSQL("DROP TABLE IF EXISTS carta;");
        db.execSQL("DROP TABLE IF EXISTS bar;");
        db.execSQL("DROP TABLE IF EXISTS producto;");
        db.execSQL("DROP TABLE IF EXISTS comanda;");
        db.execSQL("DROP TABLE IF EXISTS lineaComanda;");
        db.execSQL("DROP TABLE IF EXISTS lineaCarta;");
        db.execSQL("DROP TABLE IF EXISTS lineaComandaEnCurso;");

        db.execSQL(sqlCreateBar);
        db.execSQL(sqlCreateProducto);
        db.execSQL(sqlCreateOferta);
        db.execSQL(sqlCreateCarta);
        db.execSQL(sqlCreateComanda);
        db.execSQL(sqlCreateLineaComanda);
        db.execSQL(sqlCreateLineaComandaEnCurso);
}

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS oferta;");
        db.execSQL("DROP TABLE IF EXISTS carta;");
        db.execSQL("DROP TABLE IF EXISTS bar;");
        db.execSQL("DROP TABLE IF EXISTS producto;");
        db.execSQL("DROP TABLE IF EXISTS comanda;");
        db.execSQL("DROP TABLE IF EXISTS lineaComanda;");
        db.execSQL("DROP TABLE IF EXISTS lineaCarta;");
        db.execSQL("DROP TABLE IF EXISTS lineaComandaEnCurso;");

        db.execSQL(sqlCreateBar);
        db.execSQL(sqlCreateCarta);
        db.execSQL(sqlCreateOferta);
        db.execSQL(sqlCreateProducto);
        db.execSQL(sqlCreateComanda);
        db.execSQL(sqlCreateLineaComanda);
        db.execSQL(sqlCreateLineaComandaEnCurso);
    }

}
