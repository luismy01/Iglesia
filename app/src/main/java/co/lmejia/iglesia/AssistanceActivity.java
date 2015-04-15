/**
 * Copyright 2015 luismy01 and other contributors
 * https://github.com/luismy01
 *
 * Released under the MIT license
 * http://opensource.org/licenses/MIT
 */

package co.lmejia.iglesia;

import android.app.DatePickerDialog;
import android.content.Intent;
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

import ophilbert.animation.ActivityAnimator;

public class AssistanceActivity extends ActionBarActivity implements DatePickerDialog.OnDateSetListener {

    public static final String TAG = AssistanceActivity.class.getSimpleName();
    public static final int RESULT_EDIT = 1;

    private Assistance assistance;

    private EditText viewTxtH, viewTxtV, viewTxtA, viewTxtN;
    private Button viewBtnDate;

    private boolean isEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        setContentView(R.layout.activity_assistance);

        assistance = new Assistance();
        isEditing = false;

        viewTxtH = (EditText) findViewById(R.id.num_hermanos);
        viewTxtV = (EditText) findViewById(R.id.num_visitas);
        viewTxtA = (EditText) findViewById(R.id.num_adolescentes);
        viewTxtN = (EditText) findViewById(R.id.num_ninos);
        viewBtnDate = (Button) findViewById(R.id.btn_date_asistencia);
        viewBtnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        receiveAssistance();

        CharSequence date_format = DateFormat.format("yyyy/MM/dd", assistance.getFecha());
        String title = getResources().getString(R.string.assistance_when_date) + " - " + date_format;
        viewBtnDate.setText(title);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "onBackPressed");

        back();
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

                    cargarPropiedades();
                    saveAssistence();

                    Intent intentData = new Intent();
                    intentData.putExtra(Assistance.TAG, assistance);

                    int resultCode = isEditing ? RESULT_EDIT : RESULT_OK;
                    setResult(resultCode, intentData);

                    this.finish();

                    back();

                }

        }

        return super.onOptionsItemSelected(item);
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

    private boolean validar() {
        Log.d(TAG, "validar");

        if (viewTxtH.getText().toString().isEmpty()) {
            Toast.makeText(this, "Falta el número de hermanos", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (viewTxtV.getText().toString().isEmpty()) {
            Toast.makeText(this, "Falta el número de visitas", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (viewTxtA.getText().toString().isEmpty()) {
            Toast.makeText(this, "Falta el número de adolescentes", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (viewTxtN.getText().toString().isEmpty()) {
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

    private void cargarPropiedades() {
        Log.d(TAG, "cargarPropiedades");

        assistance.setHermanos(Integer.parseInt(viewTxtH.getText().toString()));
        assistance.setVisitas(Integer.parseInt(viewTxtV.getText().toString()));
        assistance.setAdolescentes(Integer.parseInt(viewTxtA.getText().toString()));
        assistance.setNinos(Integer.parseInt(viewTxtN.getText().toString()));
    }

    private void descargarPropiedades() {
        Log.d(TAG, "descargarPropiedades");

        viewTxtH.setText( String.valueOf(assistance.getHermanos()) );
        viewTxtV.setText( String.valueOf(assistance.getVisitas()) );
        viewTxtA.setText( String.valueOf(assistance.getAdolescentes()) );
        viewTxtN.setText( String.valueOf(assistance.getNinos()) );

    }

    private void receiveAssistance() {
        Log.d(TAG, "receiveAssistance");

        Intent intent = getIntent();

        if (intent.hasExtra(Assistance.TAG)) {
            assistance = (Assistance) intent.getSerializableExtra(Assistance.TAG);
            isEditing = true;

            descargarPropiedades();
        }

    }

    private void back() {
        Log.d(TAG, "back");

        ActivityAnimator animator = ActivityAnimator.getInstance();
        animator.PullLeftPushRight(this);
    }

    private void saveAssistence() {
        Log.d(TAG, "saveAssistence");

        AssistanceHelper helper = new AssistanceHelper(this);
        helper.save(assistance);
        helper = null;

    }

}
