package com.allomashqal.homescreen.eventservicesscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.allomashqal.R;
import com.allomashqal.Utils.Constants;
import com.allomashqal.helper.LocaleHelper;
import com.allomashqal.homescreen.IF.EventService_IF;
import com.allomashqal.homescreen.adapters.EventServicesAdapter;
import com.allomashqal.sharedpref.BaseActivity;
import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventServicesScreen extends BaseActivity {

    @BindView(R.id.recylerview)
    RecyclerView recyler_view;
    @BindView(R.id.type)
    TextView typetv;
    @BindView(R.id.back)
    ImageButton back;
    @BindView(R.id.salon_name)
    TextView salon_name;
    @BindView(R.id.reviewtv)
    TextView reviewtv;
    String locale;

    EventServicesAdapter eventServicesAdapter;
    EventService_IF eventService_if;
    Dialog dialog, confirmation_dialog;
    String type;
    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_services_screen);
        ButterKnife.bind(this);
        type = getStringVal(Constants.TYPE);
        locale = getStringVal(Constants.LOCALE);
        if (getStringVal(Constants.TYPE).equals("salons")) {
            typetv.setText("Salons");
        } else {
            typetv.setText("Events services");
        }

        listeners();
        UpdateViews(locale);
        recyler_view.setLayoutManager(new GridLayoutManager(this, 2));
        eventServicesAdapter = new EventServicesAdapter(this,locale,resources);
        recyler_view.setAdapter(eventServicesAdapter);
        eventServicesAdapter.EventServicesAdapter(new EventService_IF() {
            @Override
            public void evenServiceID(String id) {
                OpenDetails(id);
            }
        });
    }

    private void UpdateViews(String locale) {
        Context context = LocaleHelper.setLocale(this, locale);
         resources = context.getResources();
        salon_name.setText(resources.getText(R.string.salonsname));
        reviewtv.setText(resources.getText(R.string.review));
        if (getStringVal(Constants.TYPE).equals("salons")) {
            typetv.setText(resources.getText(R.string.salons));
        } else {
            typetv.setText(resources.getText(R.string.eventsservice));
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    private void OpenDetails(String id) {
        dialog = new Dialog(EventServicesScreen.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.bookingdetail_layout);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );

        MaterialButton bookingconfirmation, cancel_bt;
        TextView event_title,bookingdetailtv,servicetype,booking_date,total_bill,location;
        bookingconfirmation = dialog.findViewById(R.id.bookingconfirmation);
        bookingdetailtv = dialog.findViewById(R.id.bookingdetailtv);
        cancel_bt = dialog.findViewById(R.id.cancel_bt);
        event_title = dialog.findViewById(R.id.event_title);
        servicetype = dialog.findViewById(R.id.servicetype);
        booking_date = dialog.findViewById(R.id.booking_date);
        total_bill = dialog.findViewById(R.id.total_bill);
        location = dialog.findViewById(R.id.location);

        bookingdetailtv.setText(resources.getText(R.string.bookingdetail));
        servicetype.setText(resources.getText(R.string.servicetype));
        booking_date.setText(resources.getText(R.string.bookingdate));
        total_bill.setText(resources.getText(R.string.totalbill));
        location.setText(resources.getText(R.string.location));
        cancel_bt.setText(resources.getText(R.string.cancel));
        bookingconfirmation.setText(resources.getText(R.string.bookingconfirmation));
        event_title.setText(type);

        cancel_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        bookingconfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmation_dialog();
            }
        });
        dialog.show();

    }

    private void confirmation_dialog() {
        confirmation_dialog = new Dialog(EventServicesScreen.this);
        confirmation_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirmation_dialog.setCancelable(true);
        confirmation_dialog.setContentView(R.layout.booking_confirmation);
        confirmation_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        Window window = confirmation_dialog.getWindow();
        window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );

        MaterialButton cancel_bt;
        cancel_bt = confirmation_dialog.findViewById(R.id.cancel_bt);
        cancel_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmation_dialog.dismiss();
            }
        });

        confirmation_dialog.show();
    }

    private void listeners() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}