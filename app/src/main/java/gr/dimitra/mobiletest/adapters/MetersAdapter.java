package gr.dimitra.mobiletest.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import gr.dimitra.mobiletest.R;
import gr.dimitra.mobiletest.activities.MapsActivity;
import gr.dimitra.mobiletest.calls.Meters;

public class MetersAdapter extends RecyclerView.Adapter<MetersAdapter.ViewHolder>{

    List<Meters> meters = Collections.emptyList();;
    private List<Meters> data;
    Context context;

    // Constructor for initialization
    public MetersAdapter(Context context, List<Meters> meters) {
        this.context = context;
        this.meters = meters;
        data = new ArrayList<>();
        data.addAll(meters);
    }

    @NonNull
    @Override
    public MetersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the Layout(Instantiates list_item.xml layout file into View object)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        // Passing view to ViewHolder
        MetersAdapter.ViewHolder viewHolder = new MetersAdapter.ViewHolder(view);
        return viewHolder;
    }

    // Binding data to the into specified position
    @Override
    public void onBindViewHolder(@NonNull MetersAdapter.ViewHolder holder, int position) {
        // TypeCast Object to int type
        String id = meters.get(position).getMeter_id();
        holder.meter_id.setText(id);
        holder.meter_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (meters.get(position).getLat() != null && meters.get(position).getLng() != null){
                    Intent intent = new Intent(context, MapsActivity.class);
                    intent.putExtra("meter", (Serializable) meters.get(position));
                    context.startActivity(intent);
                }
            }
        });
        holder.date.setText(meters.get(position).getLast_entry());
    }

    @Override
    public int getItemCount() {
        // Returns number of items currently available in Adapter
        return meters.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        meters.clear();
        if (charText.length() == 0) {
            meters.addAll(data);
        } else {
            for (Meters mt : data) {
                if (mt.getMeter_id().toLowerCase(Locale.getDefault()).contains(charText)) {
                    meters.add(mt);
                }
            }
        }
        notifyDataSetChanged();
    }
    // Initializing the Views
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView meter_id;
        TextView date;

        public ViewHolder(View view) {
            super(view);
            meter_id = (TextView) view.findViewById(R.id.textView2);
            date = (TextView) view.findViewById(R.id.textView);
        }
    }
}
