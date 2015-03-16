package co.lmejia.iglesia;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by luis on 3/15/15.
 */
public class RetrieveAssistanceListTask extends AsyncTask<Void, Void, List<Assistance>> {

    private Context context;
    private MyAdapterMonthly mAdapter;

    public RetrieveAssistanceListTask(Context context, MyAdapterMonthly mAdapter) {
        this.context = context;
        this.mAdapter = mAdapter;
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(this.context, R.string.txt_loading, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected List<Assistance> doInBackground(Void... params) {

        AssistanceHelper helper = new AssistanceHelper(this.context);
        return helper.allAssistances();

    }

    @Override
    protected void onPostExecute(List<Assistance> assistances) {

        this.mAdapter.setAssistances(assistances);
        this.mAdapter.notifyItemRangeInserted(0, assistances.size()-1);

        Toast.makeText(this.context, "Carga finalizada", Toast.LENGTH_SHORT).show();

    }
}
