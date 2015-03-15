package co.lmejia.iglesia;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by luis on 1/31/15.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<Assistance> assistances;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewHermanos;
        public TextView textViewVisitas;
        public ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewHermanos = (TextView) itemView.findViewById(R.id.textViewHermanos);
            this.textViewVisitas = (TextView) itemView.findViewById(R.id.textViewVisitas);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }


    public MyAdapter(ArrayList<Assistance> dataset) {
        this.assistances = dataset;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_layout, parent, false);

        //view.setOnClickListener(MainActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        TextView textViewHermanos = holder.textViewHermanos;
        TextView textViewVisitas = holder.textViewVisitas;
        ImageView imageView = holder.imageViewIcon;

        Assistance assistance = assistances.get(position);

        textViewHermanos.setText( String.valueOf(assistance.getHermanos()) );
        textViewVisitas.setText( String.valueOf(assistance.getVisitas()) );
        imageView.setImageResource( R.drawable.support_one );

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return assistances.size();
    }

}
