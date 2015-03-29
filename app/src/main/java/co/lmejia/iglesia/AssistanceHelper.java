package co.lmejia.iglesia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by luis on 1/25/15.
 */
public class AssistanceHelper extends SQLiteOpenHelper {

    public AssistanceHelper(Context context) {
        super(context, AssistanceContract.DATABASE_NAME, null, AssistanceContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AssistanceContract.Assistance.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(AssistanceContract.Assistance.SQL_DELETE_TABLE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long save(Assistance assistance) {

        long newId = 0;

        if (assistance.getId() == 0) {
            newId = createAssistance(assistance);
        } else {
            updateAssistance(assistance);
            newId = assistance.getId();
        }

        return newId;
    }

    public long createAssistance(Assistance assistance) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(AssistanceContract.Assistance.KEY_FECHA, assistance.getFecha().getTime());
        values.put(AssistanceContract.Assistance.KEY_HERMANOS, assistance.getHermanos());
        values.put(AssistanceContract.Assistance.KEY_VISITAS, assistance.getVisitas());
        values.put(AssistanceContract.Assistance.KEY_ADOLESCENTES, assistance.getAdolescentes());
        values.put(AssistanceContract.Assistance.KEY_NINOS, assistance.getNinos());
        values.put(AssistanceContract.Assistance.KEY_OBSERVACIONES, "");

        long newId = db.insert(AssistanceContract.Assistance.TABLE_NAME, "null", values);
        db.close();

        return newId;
    }

    public boolean updateAssistance(Assistance assistance) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(AssistanceContract.Assistance.KEY_FECHA, assistance.getFecha().getTime());
        values.put(AssistanceContract.Assistance.KEY_HERMANOS, assistance.getHermanos());
        values.put(AssistanceContract.Assistance.KEY_VISITAS, assistance.getVisitas());
        values.put(AssistanceContract.Assistance.KEY_ADOLESCENTES, assistance.getAdolescentes());
        values.put(AssistanceContract.Assistance.KEY_NINOS, assistance.getNinos());
        values.put(AssistanceContract.Assistance.KEY_OBSERVACIONES, "");

        String selection = AssistanceContract.Assistance._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(assistance.getId()) };

        db.update(AssistanceContract.Assistance.TABLE_NAME, values, selection, selectionArgs);
        db.close();

        return true;
    }

    public boolean deleteAssistance(Assistance assistance) {
        SQLiteDatabase db = getWritableDatabase();

        String selection = AssistanceContract.Assistance._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(assistance.getId()) };

        db.delete(AssistanceContract.Assistance.TABLE_NAME, selection, selectionArgs);
        db.close();

        return true;
    }

    public Assistance getAssistance(long id) {

        SQLiteDatabase db = getReadableDatabase();
        Assistance assistance = new Assistance();

        String columns[] = { AssistanceContract.Assistance.KEY_FECHA
            , AssistanceContract.Assistance.KEY_HERMANOS
            , AssistanceContract.Assistance.KEY_VISITAS
            , AssistanceContract.Assistance.KEY_ADOLESCENTES
            , AssistanceContract.Assistance.KEY_NINOS
            , AssistanceContract.Assistance.KEY_OBSERVACIONES};

        String selection = AssistanceContract.Assistance._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf( id ) };

        Cursor cursor = db.query(AssistanceContract.Assistance.TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (!cursor.moveToFirst()) return null;

        long f = cursor.getLong(0);
        int h = cursor.getInt(1);
        int v = cursor.getInt(2);
        int a = cursor.getInt(3);
        int n = cursor.getInt(4);

        assistance.setFecha( new Date(f) );
        assistance.setHermanos(h);
        assistance.setVisitas(v);
        assistance.setAdolescentes(a);
        assistance.setNinos(n);

        cursor.close();
        db.close();

        return assistance;
    }

    public List<Assistance> allAssistances() {

        SQLiteDatabase db = getReadableDatabase();
        List<Assistance> assistances = new ArrayList<Assistance>();

        String columns[] = { AssistanceContract.Assistance.KEY_FECHA
                , AssistanceContract.Assistance.KEY_HERMANOS
                , AssistanceContract.Assistance.KEY_VISITAS
                , AssistanceContract.Assistance.KEY_ADOLESCENTES
                , AssistanceContract.Assistance.KEY_NINOS
                , AssistanceContract.Assistance.KEY_OBSERVACIONES};

        String orderBy = AssistanceContract.Assistance.KEY_FECHA + " ASC";

        Cursor cursor = db.query(AssistanceContract.Assistance.TABLE_NAME, columns, null, null, null, null, orderBy);

        if (!cursor.moveToFirst()) return null;

        int i = 0;

        do {

            long f = cursor.getLong(0);
            int h = cursor.getInt(1);
            int v = cursor.getInt(2);
            int a = cursor.getInt(3);
            int n = cursor.getInt(4);

            Assistance assistance = new Assistance();
            assistance.setFecha(new Date(f));
            assistance.setHermanos(h);
            assistance.setVisitas(v);
            assistance.setAdolescentes(a);
            assistance.setNinos(n);

            assistances.add(i, assistance);
            i++;

        } while (cursor.moveToNext());

        cursor.close();
        db.close();

        return assistances;

    }

}
