package com.allomashqal.homescreen.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
String locale;
Resources resources;
    public HomePageAdapter(Context context, String locale, Resources resources) {
        this.context = context;
        this.locale = locale;
        this.resources = resources;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view =null;
        if (locale.equals("en"))
        {
            view = layoutInflater.inflate(R.layout.custom_eventservice, parent, false);
        } else {
            view = layoutInflater.inflate(R.layout.custom_eventservice_arabic, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        if (Constants.TYPE.equals("salons"))
//        {
//            holder.eventname.setText(R.string.salonsname);
//        } else  {
//            holder.eventname.setText(R.string.eventname);
//        }

        holder.eventname.setText(resources.getText(R.string.eventname));
        holder.servicename.setText(resources.getText(R.string.servicename));
        holder.cityname.setText(resources.getText(R.string.cityname));
        holder.reviewtv.setText(resources.getText(R.string.review));
        holder.bookingservice.setText(resources.getText(R.string.bookingservice));

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


        TextView eventname;
        TextView servicename;
        TextView cityname;
        RoundedImageView serviceimage;
        TextView reviewtv;
        AppCompatRatingBar rating;
        MaterialButton bookingservice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventname = itemView.findViewById(R.id.eventname);
            servicename = itemView.findViewById(R.id.servicename);
            cityname = itemView.findViewById(R.id.cityname);
            serviceimage = itemView.findViewById(R.id.serviceimage);
            reviewtv = itemView.findViewById(R.id.reviewtv);
            rating = itemView.findViewById(R.id.rating);
            bookingservice = itemView.findViewById(R.id.bookingservice);
        }
    }
}
