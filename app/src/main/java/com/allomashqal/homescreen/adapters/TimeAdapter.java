package com.allomashqal.homescreen.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.allomashqal.R;
import com.allomashqal.homescreen.fragments.IF.getTime_IF;
import com.allomashqal.offers.adpater.Offers_IF;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> {

    Context context;
    ArrayList<String> timeList = new ArrayList<>();
    private int selectedPosition = -1;
    getTime_IF getTime_if;


    public void TimeAdapter(getTime_IF getTime_if) {
        this.getTime_if = getTime_if;
    }

    public TimeAdapter(Context context, ArrayList<String> time) {
        this.context = context;
        this.timeList = time;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.time, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (selectedPosition == position) {
            holder.time.setSelected(true); //using selector drawable
            holder.time.setBackgroundColor(R.drawable.selectroundbt);
        } else {
            holder.time.setSelected(false);
            holder.time.setBackgroundResource(R.drawable.roundbt);
        }

        holder.time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedPosition >= 0)
                    notifyItemChanged(selectedPosition);
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(selectedPosition);
                getTime_if.getTime_ID(timeList.get(selectedPosition));
            }
        });

        holder.time.setText(timeList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return timeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialButton time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.time);
        }
    }
}
