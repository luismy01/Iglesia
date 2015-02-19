package co.lmejia.iglesia;

import android.provider.BaseColumns;

/**
 * Created by luis on 1/25/15.
 */
@SuppressWarnings("ALL")
public final class AssistanceContract {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "asistencias.db";


    private static final String DATE_TYPE = " DATE";
    private static final String INT_TYPE = " INTEGER";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public AssistanceContract() {}

    /* Inner class that defines the table contents */
    public static abstract class AsistenciaEntry implements BaseColumns {

        public static final String TABLE_NAME = "asistencia";
        public static final String COLUMN_NAME_FECHA = "fecha";
        public static final String COLUMN_NAME_HERMANOS = "hermanos";
        public static final String COLUMN_NAME_VISITAS = "visitas";
        public static final String COLUMN_NAME_ADOLESCENTES = "adolescentes";
        public static final String COLUMN_NAME_NINOS = "ninos";
        public static final String COLUMN_NAME_OBSERVACIONES = "observaciones";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " ("
                    + _ID + " INTEGER PRIMARY KEY"
                    + COMMA_SEP + COLUMN_NAME_FECHA + DATE_TYPE
                    + COMMA_SEP + COLUMN_NAME_HERMANOS + INT_TYPE
                    + COMMA_SEP + COLUMN_NAME_VISITAS + INT_TYPE
                    + COMMA_SEP + COLUMN_NAME_ADOLESCENTES + INT_TYPE
                    + COMMA_SEP + COLUMN_NAME_NINOS + INT_TYPE
                    + COMMA_SEP + COLUMN_NAME_OBSERVACIONES + TEXT_TYPE
                + " )";

        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

    }
}
