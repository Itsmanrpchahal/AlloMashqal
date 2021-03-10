package com.allomashqal.homescreen.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.allomashqal.R;
import com.allomashqal.chat.ChatScreen;
import com.allomashqal.helper.LocaleHelper;
import com.allomashqal.homescreen.fragments.response.BookingResponse;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    Context context;
    String locale;
    Resources resources;
    ArrayList<BookingResponse.Appointment> bookingResponses;

    public BookingAdapter(Context context, String locale, Resources resources, ArrayList<BookingResponse.Appointment> bookingResponses1) {
        this.context = context;
        this.locale = locale;
        this.resources = resources;
        this.bookingResponses = bookingResponses1;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = null;
        if (locale.equals("en"))
        {
             view = layoutInflater.inflate(R.layout.mybookinglayout, parent, false);
        } else {
            view = layoutInflater.inflate(R.layout.mybookinglayout_arabic, parent, false);
        }

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.booking_edit.setText(resources.getText(R.string.edit));
    holder.booking_name.setText(bookingResponses.get(position).getProviderName());
    holder.booking_status.setText(bookingResponses.get(position).getStatus());
    holder.booking_orderdetail.setText(bookingResponses.get(position).getTitle());
    holder.booking_date.setText(bookingResponses.get(position).getDateTime());
    holder.booking_totalbill.setText("KD "+bookingResponses.get(position).getPrice());


    holder.startchat.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            context.startActivity(new Intent(context, ChatScreen.class).putExtra("vendorID",bookingResponses.get(position).getVendor_id()));
        }
    });
    }



    @Override
    public int getItemCount() {
        return bookingResponses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button booking_edit,startchat;
        TextView booking_name,booking_status,booking_orderdetail,booking_date,booking_totalbill;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           booking_edit = itemView.findViewById(R.id.booking_edit);
            booking_name = itemView.findViewById(R.id.booking_name);
            booking_status = itemView.findViewById(R.id.booking_status);
            booking_orderdetail = itemView.findViewById(R.id.booking_orderdetail);
            booking_date = itemView.findViewById(R.id.booking_date);
            booking_totalbill = itemView.findViewById(R.id.booking_totalbill);
            startchat = itemView.findViewById(R.id.startchat);
        }
    }
}
