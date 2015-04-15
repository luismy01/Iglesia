/**
 * Copyright 2015 luismy01 and other contributors
 * https://github.com/luismy01
 *
 * Released under the MIT license
 * http://opensource.org/licenses/MIT
 */

package co.lmejia.iglesia;

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
import ophilbert.animation.ActivityAnimator;


public class AssistanceListActivity extends ActionBarActivity
    implements View.OnClickListener
        , View.OnLongClickListener
        , RecyclerView.OnItemTouchListener {

    public static final String TAG = AssistanceListActivity.class.getSimpleName();
    private static final int ASSISTANCE_REQUEST_CODE = 1;

    private RecyclerView mRecyclerView;
    private ArrayList<Assistance> assistances;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private GestureDetectorCompat gestureDetector;

    private int positionEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        setContentView(R.layout.activity_main);

        positionEdit = 0;

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
        mRecyclerView.addOnItemTouchListener(this);
        gestureDetector = new GestureDetectorCompat(this, new RecyclerViewDemoOnGestureListener());

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");

        if (mAdapter.getItemCount() == 0) {
            populateList();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu");

        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");

        switch (item.getItemId()) {

            case R.id.action_new:
                ShowAssistanceDialog(null);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult");

        Assistance assistance_new;
        String message = "";

        if (requestCode == ASSISTANCE_REQUEST_CODE && data != null) {

            if (data.hasExtra(Assistance.TAG)) {

                assistance_new = (Assistance) data.getSerializableExtra(Assistance.TAG);

                if (resultCode == AssistanceActivity.RESULT_OK) {

                    mAdapter.addItem(assistance_new, 0);
                    message = "Se agregó una asistencia";

                } else if (resultCode == AssistanceActivity.RESULT_EDIT) {

                    mAdapter.updateItem(assistance_new, positionEdit);
                    message = "Se modificó una asistencia";

                }

                Toast t = Toast.makeText(this, message, Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.d(TAG, "onInterceptTouchEvent");

        gestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.d(TAG, "onTouchEvent");
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick");

        positionEdit = mRecyclerView.getChildPosition(view);
        Assistance assistance = mAdapter.getItem(positionEdit);
        ShowAssistanceDialog(assistance);

    }

    @Override
    public boolean onLongClick(View view) {
        Log.d(TAG, "onLongClick");

        int position = mRecyclerView.getChildPosition(view);
        Toast.makeText(this, " long clicked at " + position, Toast.LENGTH_SHORT).show();

        return false;
    }


    private void populateList() {
        Log.d(TAG, "populateList");
        new RetrieveAssistanceListTask(this, mAdapter).execute();
    }

    public void ShowAssistanceDialog(Assistance assistance) {
        Log.d(TAG, "ShowAssistanceDialog");

        Intent startIntent = new Intent(this, AssistanceActivity.class);
        if (assistance != null) {
            startIntent.putExtra(Assistance.TAG, assistance);
        }

        startActivityForResult(startIntent, ASSISTANCE_REQUEST_CODE);

        ActivityAnimator animator = ActivityAnimator.getInstance();
        animator.PullRightPushLeft(this);

    }

    private class RecyclerViewDemoOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        public final String TAG = RecyclerViewDemoOnGestureListener.class.getSimpleName();

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.d(TAG, "onSingleTapConfirmed");

            View view = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            onClick(view);

            return super.onSingleTapConfirmed(e);
        }

        public void onLongPress(MotionEvent e) {
            Log.d(TAG, "onLongPress");

            View view = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            onLongClick(view);

            super.onLongPress(e);
        }
    }

}
