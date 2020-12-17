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
import com.allomashqal.homescreen.EventServicesScreen;
import com.allomashqal.homescreen.fragments.HomePageFrag;
import com.google.android.material.button.MaterialButton;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePageAdapter  extends RecyclerView.Adapter<HomePageAdapter.ViewHolder> implements View.OnClickListener {

Context context;
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

        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public void onClick(View v) {
        context.startActivity(new Intent(context, EventServicesScreen.class));
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
