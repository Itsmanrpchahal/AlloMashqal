package com.allomashqal.homescreen.adapters;

import android.annotation.SuppressLint;
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
import com.allomashqal.homescreen.eventservicesscreen.EventServicesScreen;
import com.allomashqal.homescreen.response.SalonListResponse;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class HomePageAdapter  extends RecyclerView.Adapter<HomePageAdapter.ViewHolder>  {

    Context context;
    String locale;
    Resources resources;
    ArrayList<SalonListResponse.Data.List> salonListResponses ;
    public HomePageAdapter(Context context, String locale, Resources resources, ArrayList<SalonListResponse.Data.List> salonListResponses1) {
        this.context = context;
        this.locale = locale;
        this.resources = resources;
        this.salonListResponses = salonListResponses1;
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //SalonListResponse.Data.List salonListResponse = salonListResponses.get(position);
//        if (Constants.TYPE.equals("salons"))
//        {
//            holder.eventname.setText(R.string.salonsname);
//        } else  {
//            holder.eventname.setText(R.string.eventname);
//        }

        Glide.with(context).load("https://professionaler.com/salon/public/"+salonListResponses.get(position).getImage()).placeholder(R.drawable.main1);
        holder.eventname.setText(resources.getText(R.string.eventname)+" "+salonListResponses.get(position).getName().toString());
        holder.servicename.setText(resources.getText(R.string.servicename)+" "+salonListResponses.get(position).getServiceType().toString());
        if (salonListResponses.get(position).getAddress()!=null || salonListResponses.get(position).getAddress().equals(null) || salonListResponses.get(position).getAddress().equals("null"))
        {
            holder.cityname.setText(resources.getText(R.string.cityname)+" "+salonListResponses.get(position).getAddress().toString());
        }
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
        return salonListResponses.size();
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
            cityname = itemView.findViewById(R.id.cityname1);
            serviceimage = itemView.findViewById(R.id.serviceimage);
            reviewtv = itemView.findViewById(R.id.reviewtv);
            rating = itemView.findViewById(R.id.rating);
            bookingservice = itemView.findViewById(R.id.bookingservice);
        }
    }
}
