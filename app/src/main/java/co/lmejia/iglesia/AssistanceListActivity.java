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

    private AssistanceDialogFragment assistanceDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new AssistanceHelper(getApplicationContext());

        assistances = new ArrayList<Assistance>();
        mAdapter = new MyAdapterMonthly(assistances);

        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        assistanceDialog = new AssistanceDialogFragment();
        assistanceDialog.setListener(this);

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
    public void onDialogPositiveClick(AssistanceDialogFragment dialog) {
        saveAssistance(dialog.getAssistance());
    }

    @Override
    public void onDialogNegativeClick(AssistanceDialogFragment dialog) {

    }

    public void ShowAssistanceDialog() {
        assistanceDialog.reset();
        assistanceDialog.show(this.getSupportFragmentManager(), "Asistencia");
    }

    private boolean saveAssistance(Assistance assistance) {

        long id = helper.save(assistance);

        if (id != 0) {
            assistance.setId(id);
            assistances.add(assistance);
            mAdapter.notifyItemInserted(assistances.size()-1);
            return true;
        }

        return false;
    }


}
