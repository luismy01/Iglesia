package co.lmejia.iglesia;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by luis on 1/25/15.
 */
public class AssistanceHelper extends SQLiteOpenHelper {

    public AssistanceHelper(Context context) {
        super(context, AssistanceContract.DATABASE_NAME, null, AssistanceContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AssistanceContract.AsistenciaEntry.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(AssistanceContract.AsistenciaEntry.SQL_DELETE_TABLE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long save(Assistance assistance) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        long newId = 0;

        if (assistance.id == 0) {

            values.put(AssistanceContract.AsistenciaEntry.COLUMN_NAME_FECHA, assistance.fecha.getTime());
            values.put(AssistanceContract.AsistenciaEntry.COLUMN_NAME_HERMANOS, assistance.hermanos);
            values.put(AssistanceContract.AsistenciaEntry.COLUMN_NAME_VISITAS, assistance.visitas);
            values.put(AssistanceContract.AsistenciaEntry.COLUMN_NAME_ADOLESCENTES, assistance.adolescentes);
            values.put(AssistanceContract.AsistenciaEntry.COLUMN_NAME_NINOS, assistance.ninos);
            values.put(AssistanceContract.AsistenciaEntry.COLUMN_NAME_OBSERVACIONES, "");

            newId = db.insert(AssistanceContract.AsistenciaEntry.TABLE_NAME, "null", values);

        } else {

            String selection = AssistanceContract.AsistenciaEntry._ID + " LIKE ?";
            String[] selectionArgs = { String.valueOf(assistance.id) };

            db.update(AssistanceContract.AsistenciaEntry.TABLE_NAME, values, selection, selectionArgs);

        }

        db = null;

        return newId;
    }
}
