package co.lmejia.iglesia;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

/**
 * Created by luis on 3/15/15.
 */
public class RetrieveAssistanceListTask extends AsyncTask<Void, Void, List<Assistance>> {

    private Context context;
    private MyAdapter mAdapter;

    public RetrieveAssistanceListTask(Context context, MyAdapter mAdapter) {
        this.context = context;
        this.mAdapter = mAdapter;
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(context, R.string.loading, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected List<Assistance> doInBackground(Void... params) {

        AssistanceHelper helper = new AssistanceHelper(context);
        return helper.allAssistances();

    }

    @Override
    protected void onPostExecute(List<Assistance> assistances) {

        //mAdapter.setAssistances(assistances);
        mAdapter.clear();

        for (int i = 0, size = assistances.size(); i < size; i++) {
            mAdapter.addItem(assistances.get(i), i);
        }

        Toast.makeText(context, "Carga finalizada", Toast.LENGTH_SHORT).show();

    }
}
