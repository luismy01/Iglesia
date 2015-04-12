package co.lmejia.iglesia;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;


public class AssistanceActivity extends ActionBarActivity implements DatePickerDialog.OnDateSetListener {

    private final String TAG = AssistanceActivity.class.getSimpleName();

    private Assistance assistance;
    private Button viewBtnDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        setContentView(R.layout.activity_assistance);

        assistance = new Assistance();

        viewBtnDate = (Button) findViewById(R.id.btn_date_asistencia);
        viewBtnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        CharSequence date_format = DateFormat.format("yyyy/MM/dd", assistance.getFecha());
        String title = getResources().getString(R.string.assistance_when_date) + " - " + date_format;
        viewBtnDate.setText(title);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu");

        getMenuInflater().inflate(R.menu.menu_asistencia_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");

        int id = item.getItemId();

        switch (id) {

            case R.id.btn_date_asistencia:
                showDatePickerDialog();

            case R.id.action_save:

                if (validar()) {

                    saveAssistence();

                    // I send the new assistance object
                    Intent intentData = new Intent();
                    intentData.putExtra(Assistance.TAG, assistance);
                    setResult(RESULT_OK, intentData);

                    this.finish();
                }

        }

        return super.onOptionsItemSelected(item);
    }

    private boolean validar() {
        Log.d(TAG, "validar");

        EditText txt;

        txt = (EditText) findViewById(R.id.num_hermanos);
        if (txt.getText().toString().isEmpty()) {
            Toast.makeText(this, "Falta el número de hermanos", Toast.LENGTH_SHORT).show();
            return false;
        }

        txt = (EditText) findViewById(R.id.num_visitas);
        if (txt.getText().toString().isEmpty()) {
            Toast.makeText(this, "Falta el número de visitas", Toast.LENGTH_SHORT).show();
            return false;
        }

        txt = (EditText) findViewById(R.id.num_adolescentes);
        if (txt.getText().toString().isEmpty()) {
            Toast.makeText(this, "Falta el número de adolescentes", Toast.LENGTH_SHORT).show();
            return false;
        }

        txt = (EditText) findViewById(R.id.num_ninos);
        if (txt.getText().toString().isEmpty()) {
            Toast.makeText(this, "Falta el número de niños", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void showDatePickerDialog() {
        Log.d(TAG, "showDatePickerDialog");

        int y, m, d;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(assistance.getFecha());

        y = calendar.get(Calendar.YEAR);
        m = calendar.get(Calendar.MONTH);
        d = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this,this, y, m, d);
        dialog.setTitle(R.string.assistance_when_date);
        dialog.show();

    }

    private void saveAssistence() {
        Log.d(TAG, "saveAssistence");

        AssistanceHelper helper = new AssistanceHelper(this);

        EditText txt;

        txt = (EditText) findViewById(R.id.num_hermanos);
        assistance.setHermanos(Integer.parseInt(txt.getText().toString()));

        txt = (EditText) findViewById(R.id.num_visitas);
        assistance.setVisitas(Integer.parseInt(txt.getText().toString()));

        txt = (EditText) findViewById(R.id.num_adolescentes);
        assistance.setAdolescentes(Integer.parseInt(txt.getText().toString()));

        txt = (EditText) findViewById(R.id.num_ninos);
        assistance.setNinos(Integer.parseInt(txt.getText().toString()));

        Toast t = Toast.makeText(this, "Guardando asistencia", Toast.LENGTH_SHORT);
        t.show();

        helper.save(assistance);

        t = Toast.makeText(this, "Se agregó una asistencia", Toast.LENGTH_SHORT);
        t.show();

        helper = null;

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Log.d(TAG, "onDateSet");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        assistance.setFecha(calendar.getTime());

        CharSequence date_format = DateFormat.format("yyyy/MM/dd", assistance.getFecha());
        String title = getResources().getString(R.string.assistance_when_date) + " - " + date_format;
        viewBtnDate.setText(title);

    }
}
