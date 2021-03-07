package com.allomashqal.homescreen.adapters;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.icu.text.SimpleDateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.allomashqal.R;
import com.allomashqal.helper.LocaleHelper;
import com.allomashqal.homescreen.IF.EventService_IF;
import com.allomashqal.homescreen.response.EventServiceDataResponse;
import com.google.android.material.button.MaterialButton;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventServicesAdapter extends RecyclerView.Adapter<EventServicesAdapter.ViewHolder> {
    Context context;
    EventService_IF eventService_if;
    Resources resources;
    String  locale;
    Calendar calendar;
    DatePickerDialog.OnDateSetListener dateSetListener;
    String selectedDate,selectedTime;
    ArrayList<EventServiceDataResponse.Data.ServiceList> eventServiceDataResponseArrayList;

    public EventServicesAdapter(Context context,String locale, Resources resources,ArrayList<EventServiceDataResponse.Data.ServiceList> eventServiceDataResponseArrayList1) {
        this.context = context;
        this.locale = locale;
        this.resources = resources;
        this.eventServiceDataResponseArrayList = eventServiceDataResponseArrayList1;
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
        calendar = Calendar.getInstance();

        Context context1 = LocaleHelper.setLocale(context, locale);
        Resources resources = context1.getResources();
        holder.servicetext.setText(eventServiceDataResponseArrayList.get(position).getTitle());
        holder.pricetext.setText(eventServiceDataResponseArrayList.get(position).getPrice());
        holder.select_bt.setText(resources.getText(R.string.select));


        //holder.select_date_bt.setText(resources.getText(R.string.selectdate));
       // holder.select_time_bt.setText(resources.getText(R.string.selecttime));

        holder.select_date_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.DialogTheme
                        , dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        holder.select_date_bt.setText(dayOfMonth+"/"+month+"/"+year);
                        selectedDate = dayOfMonth+"/"+month+"/"+year;
                        notifyDataSetChanged();
                    }
                };
                datePickerDialog.show();

            }
        });





        holder.select_time_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar normal = Calendar.getInstance();
                //normal.setTimeZone(TimeZone.getTimeZone("GMT"));
                final int hour = normal.get(Calendar.HOUR_OF_DAY);
                int minute = normal.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(context, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                    calendar.set(Calendar.MINUTE,minute);
                        Calendar startCalenderTime = Calendar.getInstance();
                        holder.select_time_bt.setText(hourOfDay+":"+minute);
                        selectedTime = hourOfDay+"/"+minute;
                    }
                },hour,minute,true);
                timePickerDialog.show();
            }
        });


        holder.select_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eventService_if.evenServiceID(String.valueOf(position),selectedDate,selectedTime);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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
