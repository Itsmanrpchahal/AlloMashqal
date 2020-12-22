package com.allomashqal.homescreen.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.allomashqal.R;
import com.allomashqal.helper.LocaleHelper;
import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    Context context;
    String locale;
    Resources resources;

    public BookingAdapter(Context context, String locale, Resources resources) {
        this.context = context;
        this.locale = locale;
        this.resources = resources;
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
    holder.booking_name.setText(resources.getText(R.string.name));
    holder.booking_status.setText(resources.getText(R.string.bookingstatus));
    holder.booking_orderdetail.setText(resources.getText(R.string.orderdetails));
    holder.booking_date.setText(resources.getText(R.string.bookingdate));
    holder.booking_totalbill.setText(resources.getText(R.string.totalbill));
    }



    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button booking_edit;
        TextView booking_name,booking_status,booking_orderdetail,booking_date,booking_totalbill;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           booking_edit = itemView.findViewById(R.id.booking_edit);
            booking_name = itemView.findViewById(R.id.booking_name);
            booking_status = itemView.findViewById(R.id.booking_status);
            booking_orderdetail = itemView.findViewById(R.id.booking_orderdetail);
            booking_date = itemView.findViewById(R.id.booking_date);
            booking_totalbill = itemView.findViewById(R.id.booking_totalbill);
        }
    }
}
