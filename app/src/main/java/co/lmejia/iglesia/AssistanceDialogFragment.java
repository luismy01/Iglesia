package co.lmejia.iglesia;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by luis on 2/15/15.
 */

public class AssistanceDialogFragment extends DialogFragment {

    public interface AssistanceDialogListener {
        public void onDialogPositiveClick(AssistanceDialogFragment dialog);
        public void onDialogNegativeClick(AssistanceDialogFragment dialog);
    }

    private Assistance assistance;
    private AssistanceDialogListener listener;
    private DatePickerDialogFragment datePickerDialog;
    private Button btnAssistanceDate;

    public AssistanceDialogFragment () {
        super();

        assistance = new Assistance();

        datePickerDialog = new DatePickerDialogFragment();
        datePickerDialog.setOnDismissListener(new DatePickerDialog.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Date date = datePickerDialog.getDate();
                DateFormat df = DateFormat.getDateInstance();

                assistance.setFecha(date);
                btnAssistanceDate.setText( df.format(date) );
            }
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = this.getActivity().getLayoutInflater();
        final View xmlLayout = inflater.inflate(R.layout.activity_asistencia_item, null);

        btnAssistanceDate = (Button) xmlLayout.findViewById(R.id.btn_date_asistencia);
        btnAssistanceDate.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());

        builder.setTitle("Asistencia");
        builder.setView(xmlLayout);

        builder.setPositiveButton(R.string.dialog_save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int h = getNumber((TextView) xmlLayout.findViewById(R.id.num_hermanos));
                int v = getNumber((TextView) xmlLayout.findViewById(R.id.num_visitas));
                int a = getNumber((TextView) xmlLayout.findViewById(R.id.num_adolescentes));
                int n = getNumber((TextView) xmlLayout.findViewById(R.id.num_ninos));

                assistance.setHermanos(h);
                assistance.setVisitas(v);
                assistance.setAdolescentes(a);
                assistance.setNinos(n);

                if (listener != null) {
                    listener.onDialogPositiveClick(AssistanceDialogFragment.this);
                }
            }
        });


        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null) {
                    listener.onDialogNegativeClick(AssistanceDialogFragment.this);
                }
            }
        });

        return builder.create();
    }

    private int getNumber(TextView textView) {

        String text = textView.getText().toString();

        if (text == null || text.isEmpty()) return 0;
        return Integer.valueOf( text );
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void setListener(AssistanceDialogListener listener) {
        this.listener = listener;
    }

    public Assistance getAssistance() {
        return assistance;
    }

    private void showDatePickerDialog() {
        datePickerDialog.show(this.getFragmentManager(), "datePicker");
    }

    public void reset() {
        assistance = new Assistance();
    }

}
