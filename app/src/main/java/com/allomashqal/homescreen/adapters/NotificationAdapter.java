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

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    Context context;
    Resources resources;
    String  locale;

    public NotificationAdapter(Context context, String locale, Resources resources) {
        this.context = context;
        this.locale = locale;
        this.resources = resources;
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
        holder.notificationtitle.setText(resources.getText(R.string.reservationsent));
        holder.bookingstatus.setText(resources.getText(R.string.bookingstatus));
        holder.offerdetail.setText(resources.getText(R.string.orderdetails));
        holder.bookingdate.setText(resources.getText(R.string.bookingdate));
        holder.totalbill.setText(resources.getText(R.string.totalbill));
    }

    @Override
    public int getItemCount() {
        return 10;
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
