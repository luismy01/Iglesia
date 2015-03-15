package co.lmejia.iglesia;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by luis on 2/21/15.
 */
public class DatePickerDialogFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private final Calendar calendar = Calendar.getInstance();
    private Date date;

    private DialogInterface.OnDismissListener onDismissListener;

    public DatePickerDialogFragment () {
        super();

        date = calendar.getTime();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(this.getActivity(), this, year, month, day);
        datePicker.setOnDismissListener(this);
        return datePicker;

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        date = calendar.getTime();
        this.dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) onDismissListener.onDismiss(dialog);
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public Date getDate() {
        return date;
    }
}
