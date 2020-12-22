package com.allomashqal.homescreen.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.allomashqal.R;
import com.allomashqal.helper.LocaleHelper;
import com.allomashqal.homescreen.IF.EventService_IF;
import com.allomashqal.homescreen.eventservicesscreen.EventServicesScreen;
import com.google.android.material.button.MaterialButton;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventServicesAdapter extends RecyclerView.Adapter<EventServicesAdapter.ViewHolder> {
    Context context;
    EventService_IF eventService_if;
    Resources resources;
    String  locale;

    public EventServicesAdapter(Context context,String locale, Resources resources) {
        this.context = context;
        this.locale = locale;
        this.resources = resources;
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

        Context context1 = LocaleHelper.setLocale(context, locale);
        Resources resources = context1.getResources();
        holder.servicetext.setText(resources.getText(R.string.servicename));
        holder.pricetext.setText(resources.getText(R.string.price));
        holder.select_bt.setText(resources.getText(R.string.select));
        holder.select_date_bt.setText(resources.getText(R.string.selectdate));
        holder.select_time_bt.setText(resources.getText(R.string.selecttime));
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
        @BindView(R.id.servicetext)
        TextView servicetext;
        @BindView(R.id.pricetext)
        TextView pricetext;
        @BindView(R.id.select_date_bt)
        MaterialButton select_date_bt;
        @BindView(R.id.select_time_bt)
        MaterialButton select_time_bt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
