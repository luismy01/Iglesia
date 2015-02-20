package co.lmejia.iglesia;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;


public class AssistanceListActivity extends ActionBarActivity
        implements AssistanceDialogFragment.AssistanceDialogListener {


    private RecyclerView mRecyclerView;
    private ArrayList<Assistance> assistances;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private AssistanceHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new AssistanceHelper(this);

        assistances = new ArrayList<Assistance>();

        /*
        for (int i = 0; i < MyData.hermanos.length; i++) {
            assistances.add(new Assistance(new Date(),
                    MyData.hermanos[i],
                    MyData.visitas[i],
                    MyData.adolescentes[i],
                    MyData.ninos[i],
                    R.drawable.goose
            ));
        }
        */

        mAdapter = new MyAdapterMonthly(assistances);
        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_new:
                ShowAssistanceDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

        AlertDialog alertDialog = (AlertDialog) dialog.getDialog();
        saveAssistance(alertDialog);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
    }

    public void ShowAssistanceDialog() {
        AssistanceDialogFragment dialog = new AssistanceDialogFragment();
        dialog.setListener(this);
        dialog.show(this.getSupportFragmentManager(), "Asistencia");
    }

    private boolean saveAssistance(AlertDialog dialog) {

        int h = Integer.parseInt(((TextView) dialog.findViewById(R.id.num_hermanos)).getText().toString());
        int v = Integer.parseInt(((TextView) dialog.findViewById(R.id.num_visitas)).getText().toString());
        int a = Integer.parseInt(((TextView) dialog.findViewById(R.id.num_adolescentes)).getText().toString());
        int n = Integer.parseInt(((TextView) dialog.findViewById(R.id.num_ninos)).getText().toString());

        Assistance assistance = new Assistance(new Date(), h, v, a, n, 0);
        assistance.id = helper.save(assistance);

        if (assistance.id != 0) {
            assistances.add(assistance);
            mAdapter.notifyItemInserted(assistances.size()-1);
            return true;
        }

        return false;
    }


}
