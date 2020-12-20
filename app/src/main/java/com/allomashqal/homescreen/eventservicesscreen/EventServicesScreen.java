package com.allomashqal.homescreen.eventservicesscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.allomashqal.R;
import com.allomashqal.Utils.Constants;
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


    EventServicesAdapter eventServicesAdapter;
    EventService_IF eventService_if;
    Dialog dialog, confirmation_dialog;
    Intent intent;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_services_screen);
        ButterKnife.bind(this);
        type = getStringVal(Constants.TYPE);

        if (getStringVal(Constants.TYPE).equals("salons"))
        {
            typetv.setText("Salons");
        } else  {
            typetv.setText("Events services");
        }

        listeners();
        recyler_view.setLayoutManager(new GridLayoutManager(this, 2));
        eventServicesAdapter = new EventServicesAdapter(this);
        recyler_view.setAdapter(eventServicesAdapter);
        eventServicesAdapter.EventServicesAdapter(new EventService_IF() {
            @Override
            public void evenServiceID(String id) {
                OpenDetails(id);
            }
        });
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

        MaterialButton bookingconfirmation,cancel_bt;
        TextView event_title;
        bookingconfirmation = dialog.findViewById(R.id.bookingconfirmation);
        cancel_bt = dialog.findViewById(R.id.cancel_bt);
        event_title = dialog.findViewById(R.id.event_title);

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