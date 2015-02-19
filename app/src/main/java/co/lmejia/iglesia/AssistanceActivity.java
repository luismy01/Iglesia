package co.lmejia.iglesia;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class AssistanceActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia_item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_asistencia_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_save) {
            saveAssistence();
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveAssistence() {

        // Gets the data repository in write mode
        AssistanceHelper helper = new AssistanceHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();


        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        EditText txt;


        txt = (EditText) findViewById(R.id.date_asistencia);
        values.put(AssistanceContract.AsistenciaEntry.COLUMN_NAME_FECHA, txt.getText().toString());

        txt = (EditText) findViewById(R.id.num_hermanos);
        values.put(AssistanceContract.AsistenciaEntry.COLUMN_NAME_HERMANOS, Integer.parseInt(txt.getText().toString()));

        txt = (EditText) findViewById(R.id.num_visitas);
        values.put(AssistanceContract.AsistenciaEntry.COLUMN_NAME_VISITAS, Integer.parseInt(txt.getText().toString()));

        txt = (EditText) findViewById(R.id.num_adolescentes);
        values.put(AssistanceContract.AsistenciaEntry.COLUMN_NAME_ADOLESCENTES, Integer.parseInt(txt.getText().toString()));

        txt = (EditText) findViewById(R.id.num_ninos);
        values.put(AssistanceContract.AsistenciaEntry.COLUMN_NAME_NINOS, Integer.parseInt(txt.getText().toString()));

        values.put(AssistanceContract.AsistenciaEntry.COLUMN_NAME_OBSERVACIONES, "");


        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(AssistanceContract.AsistenciaEntry.TABLE_NAME, "null", values);

        db = null;
        helper = null;

    }

}
