package com.allomashqal.offers.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.allomashqal.R;
import com.allomashqal.homescreen.adapters.BookingAdapter;
import com.allomashqal.offers.response.OffersResponse;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class OffersAdapter  extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {
    Context context;
    Offers_IF offers_if;
    ArrayList<OffersResponse.Data.Offer> offersResponses = new ArrayList<>();


    public void OffersAdapter(Offers_IF offers_if) {
        this.offers_if = offers_if;
    }

    public OffersAdapter(Context context, ArrayList<OffersResponse.Data.Offer> offersResponses) {
        this.context = context;
        this.offersResponses = offersResponses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.offerlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.eventname.setText(offersResponses.get(position).getProviderName());
        holder.servicename.setText(offersResponses.get(position).getDescription());
        holder.cityname.setText(offersResponses.get(position).getTitle());
        Glide.with(context).load(offersResponses.get(position).getImage()).into(holder.serviceimage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offers_if.getOffer_ID(String.valueOf(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return offersResponses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventname,servicename,cityname;
        RoundedImageView serviceimage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            eventname = itemView.findViewById(R.id.eventname);
            servicename = itemView.findViewById(R.id.servicename);
            cityname = itemView.findViewById(R.id.cityname);
            serviceimage = itemView.findViewById(R.id.serviceimage);
        }
    }
}
