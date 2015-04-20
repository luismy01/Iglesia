package co.lmejia.iglesia;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

/**
 * Created by luis on 4/19/15.
 */
public class DeleteAssistanceDialogFragment extends DialogFragment {

    public final String TAG = DeleteAssistanceDialogFragment.class.getSimpleName();

    private Context context;
    private MyAdapter adapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.title_delete_assistance);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Assistance assistance = adapter.getItem(adapter.getSelectedItem());
                AssistanceHelper helper = new AssistanceHelper(context);

                if (helper.deleteAssistance(assistance)) {
                    adapter.removeItem(adapter.getSelectedItem());
                    Toast.makeText(context, R.string.toast_delete_assistance, Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.removeSelectedItem();
            }
        });

        return builder.create();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setAdapter(MyAdapter adapter) {
        this.adapter = adapter;
    }

    public MyAdapter getAdapter() {
        return adapter;
    }
}
