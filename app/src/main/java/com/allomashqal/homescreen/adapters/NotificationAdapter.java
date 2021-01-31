package com.allomashqal.homescreen.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.allomashqal.R;
import com.allomashqal.homescreen.fragments.response.NotificationsResponse;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    Context context;
    Resources resources;
    String  locale;
    ArrayList<NotificationsResponse.Datum> list ;

    public NotificationAdapter(Context context, String locale, Resources resources, ArrayList<NotificationsResponse.Datum> lists) {
        this.context = context;
        this.locale = locale;
        this.resources = resources;
        this.list = lists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view =null;
         view = layoutInflater.inflate(R.layout.notificationslayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (locale.equals("ar"))
        {
            holder.notificationtitle.setGravity(Gravity.RIGHT);
            holder.bookingstatus.setGravity(Gravity.RIGHT);
            holder.offerdetail.setGravity(Gravity.RIGHT);
            holder.bookingdate.setGravity(Gravity.RIGHT);
            holder.totalbill.setGravity(Gravity.RIGHT);
        }
        holder.notificationtitle.setText(list.get(position).getTitle());
        holder.bookingstatus.setText(list.get(position).getStatus());
        holder.offerdetail.setText(list.get(position).getBooking());
        holder.bookingdate.setText(list.get(position).getBooking());
        holder.totalbill.setText(list.get(position).getTotalBill());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView notificationtitle,bookingstatus,offerdetail,bookingdate,totalbill;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationtitle = itemView.findViewById(R.id.notificationtitle);
            bookingstatus = itemView.findViewById(R.id.bookingstatus);
            offerdetail = itemView.findViewById(R.id.offerdetail);
            bookingdate = itemView.findViewById(R.id.bookingdate);
            totalbill = itemView.findViewById(R.id.totalbill);
        }
    }
}
