package co.lmejia.iglesia;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;


public class AssistanceListActivity extends ActionBarActivity
        implements AssistanceDialogFragment.AssistanceDialogListener {


    private RecyclerView mRecyclerView;
    private ArrayList<Assistance> assistances;
    private MyAdapterMonthly mAdapter;
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
    protected void onStart() {
        super.onStart();

        if (mAdapter.getItemCount() == 0) {
            populateList();
        }

    }

    private void populateList() {
        new RetrieveAssistanceListTask(this, mAdapter).execute();
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

        if (saveAssistance(dialog.getAssistance())) {
            Toast.makeText(this, "Elemento insertado", Toast.LENGTH_SHORT).show();
        }

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
