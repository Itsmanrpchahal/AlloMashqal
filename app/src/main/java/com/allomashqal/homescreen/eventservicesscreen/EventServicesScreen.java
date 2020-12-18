package com.allomashqal.homescreen.eventservicesscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.allomashqal.R;
import com.allomashqal.homescreen.adapters.EventServicesAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventServicesScreen extends AppCompatActivity {

    @BindView(R.id.recyler_view)
    RecyclerView recyler_view;
    EventServicesAdapter eventServicesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_services_screen);
        ButterKnife.bind(this);

        listeners();
        recyler_view.setLayoutManager(new GridLayoutManager(this,2));
        eventServicesAdapter = new EventServicesAdapter(this);
        recyler_view.setAdapter(eventServicesAdapter);
    }

    private void listeners() {

    }
}