package com.allomashqal.homescreen.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.allomashqal.R;
import com.allomashqal.homescreen.IF.EventService_IF;
import com.allomashqal.homescreen.eventservicesscreen.EventServicesScreen;
import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventServicesAdapter extends RecyclerView.Adapter<EventServicesAdapter.ViewHolder> {
    Context context;
    EventService_IF eventService_if;
    public EventServicesAdapter(Context context) {
        this.context = context;
    }

    public void EventServicesAdapter(EventService_IF eventService_if) {
        this.eventService_if = eventService_if;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.customevenetservices, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.select_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventService_if.evenServiceID(String.valueOf(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 12;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.select_bt)
        MaterialButton select_bt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
