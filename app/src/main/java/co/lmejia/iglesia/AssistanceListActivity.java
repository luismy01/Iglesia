package co.lmejia.iglesia;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import co.lmejia.iglesia.utils.DividerItemDecoration;


public class AssistanceListActivity extends ActionBarActivity
    implements AssistanceDialogFragment.AssistanceDialogListener
        , View.OnClickListener
        , View.OnLongClickListener
        , RecyclerView.OnItemTouchListener {

    public static final String TAG = AssistanceListActivity.class.getSimpleName();
    private static final int ASSISTANCE_REQUEST_CODE = 1;

    private RecyclerView mRecyclerView;
    private ArrayList<Assistance> assistances;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    GestureDetectorCompat gestureDetector;

    private AssistanceHelper helper;

    private AssistanceDialogFragment assistanceDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        setContentView(R.layout.activity_main);

        helper = new AssistanceHelper(getApplicationContext());

        assistances = new ArrayList<Assistance>();
        mAdapter = new MyAdapter(assistances);

        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);

        mRecyclerView.addItemDecoration(itemDecoration);

        // onClickDetection is done in this Activity's onItemTouchListener
        // with the help of a GestureDetector;
        // Tip by Ian Lake on G+ in a comment to this post:
        // https://plus.google.com/+LucasRocha/posts/37U8GWtYxDE
        mRecyclerView.addOnItemTouchListener(this);
        gestureDetector = new GestureDetectorCompat(this, new RecyclerViewDemoOnGestureListener());

        assistanceDialog = new AssistanceDialogFragment();
        assistanceDialog.setListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");

        //if (mAdapter.getItemCount() == 0) {
            populateList();
        //}

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Assistance assistance_new;

        if (requestCode == ASSISTANCE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // I get the new assistance object
                assistance_new = (Assistance) data.getSerializableExtra(Assistance.TAG);
            }
        }
    }

    @Override
    public void onDialogPositiveClick(AssistanceDialogFragment dialog) {
/* @deprecated
        if (saveAssistance(dialog.getAssistance())) {
            Toast.makeText(this, "Elemento insertado", Toast.LENGTH_SHORT).show();
        }
*/
    }

    @Override
    public void onDialogNegativeClick(AssistanceDialogFragment dialog) {
        /* @deprecated */
    }

    public void ShowAssistanceDialog() {

        /*
        assistanceDialog.reset();
        assistanceDialog.show(this.getSupportFragmentManager(), "Asistencia");
        */

        Intent startIntent = new Intent(this, AssistanceActivity.class);
        startActivityForResult(startIntent, ASSISTANCE_REQUEST_CODE);

    }

    private boolean saveAssistance(Assistance assistance) {

        long id = helper.save(assistance);

        if (id != 0) {
            assistance.setId(id);

            int position = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
            position++;

            mAdapter.addItem(assistance, position);
            return true;
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onClick(View view) {

        int position = mRecyclerView.getChildPosition(view);
        Toast.makeText(this, "clicked at " + position, Toast.LENGTH_SHORT).show();
/*
        // item click
        int position = mRecyclerView.getChildPosition(view);
        /*if (actionMode != null) {
            myToggleSelection(idx);
            return;
        }
        DemoModel data = mAdapter.getItem()
        View innerContainer = view.findViewById(R.id.container_inner_item);
        innerContainer.setTransitionName(Constants.NAME_INNER_CONTAINER + "_" + data.id);
        Intent startIntent = new Intent(this, CardViewDemoActivity.class);
        startIntent.putExtra(Constants.KEY_ID, data.id);
        ActivityOptions options = ActivityOptions
                .makeSceneTransitionAnimation(this, innerContainer, Constants.NAME_INNER_CONTAINER);
        this.startActivity(startIntent, options.toBundle());

*/
    }

    @Override
    public boolean onLongClick(View view) {

        int position = mRecyclerView.getChildPosition(view);
        Toast.makeText(this, "long click at " + position, Toast.LENGTH_SHORT).show();

        return false;
    }

    private class RecyclerViewDemoOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {

            View view = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            onClick(view);

            return super.onSingleTapConfirmed(e);
        }

        public void onLongPress(MotionEvent e) {

            View view = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            /*if (actionMode != null) {
                return;
            }*/
            // Start the CAB using the ActionMode.Callback defined above
            //actionMode = startActionMode(RecyclerViewDemoActivity.this);
            int idx = mRecyclerView.getChildPosition(view);
            onLongClick(view);

            super.onLongPress(e);
        }
    }

}
