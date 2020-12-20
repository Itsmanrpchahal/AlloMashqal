package com.allomashqal.homescreen.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.allomashqal.R;
import com.allomashqal.Utils.Constants;
import com.allomashqal.homescreen.eventservicesscreen.EventServicesScreen;
import com.google.android.material.button.MaterialButton;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePageAdapter  extends RecyclerView.Adapter<HomePageAdapter.ViewHolder>  {

Context context;
String type;
    public HomePageAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_eventservice, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (Constants.TYPE.equals("salons"))
        {
            holder.eventname.setText(R.string.salonsname);
        } else  {
            holder.eventname.setText(R.string.eventname);
        }

        holder.bookingservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, EventServicesScreen.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.eventname)
        TextView eventname;
        @BindView(R.id.servicename)
        TextView servicename;
        @BindView(R.id.cityname)
        TextView cityname;
        @BindView(R.id.serviceimage)
        RoundedImageView serviceimage;
        @BindView(R.id.reviewtv)
        TextView reviewtv;
        @BindView(R.id.rating)
        AppCompatRatingBar rating;
        @BindView(R.id.bookingservice)
        MaterialButton bookingservice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
