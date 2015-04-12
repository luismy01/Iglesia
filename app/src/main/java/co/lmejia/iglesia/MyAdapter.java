package co.lmejia.iglesia;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

/**
 * Created by luis on 2/14/15.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.CardViewHolder> {

    private List<Assistance> assistances;

    public MyAdapter(List<Assistance> dataset) {
        assistances = dataset;
    }

    public void setAssistances(List<Assistance> assistances) {
        this.assistances = assistances;
        notifyDataSetChanged();
    }

    public void addItem(Assistance item, int position) {
        assistances.add(position, item);
        notifyItemInserted(position);
    }

    public void updateItem(Assistance item, int position) {
        assistances.set(position, item);
        notifyItemChanged(position);
    }

    public void removeItem(int position) {
        assistances.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        assistances.clear();
        notifyDataSetChanged();
    }

    public Assistance getItem(int position) {
        return assistances.get(position);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {

        View xmlLayout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_cardview_layout, parent, false);

        return new CardViewHolder(xmlLayout);

    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {

        Assistance assistance = assistances.get(position);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(assistance.getFecha());

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        holder.txtAssistance.setText( String.valueOf(assistance.getTotal()) );
        holder.txtMonth.setText(MyData.getMonthName(month));
        holder.txtDayYear.setText("" + day + ", " + year);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return assistances.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        public TextView txtAssistance;
        public TextView txtMonth;
        public TextView txtDayYear;

        public CardViewHolder(View xmlLayout) {
            super(xmlLayout);

            this.txtAssistance = (TextView) xmlLayout.findViewById(R.id.txtAssistance);
            this.txtMonth = (TextView) xmlLayout.findViewById(R.id.txtMonth);
            this.txtDayYear = (TextView) xmlLayout.findViewById(R.id.txtDayYear);
        }
    }
}
