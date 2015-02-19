package co.lmejia.iglesia;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by luis on 2/15/15.
 */

public class AssistanceDialogFragment extends DialogFragment {

    private AssistanceDialogListener listener;

    public interface AssistanceDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    public void setListener(AssistanceDialogListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = this.getActivity().getLayoutInflater();
        View xmlLayout = inflater.inflate(R.layout.activity_asistencia_item, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());

        builder.setView(xmlLayout);

        builder.setPositiveButton(R.string.dialog_save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
}
