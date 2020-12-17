package com.allomashqal.MapScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.allomashqal.R;
import com.allomashqal.dashboard.Dashboard;
import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapScreen extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.next_bt)
    MaterialButton next_bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_screen);

        ButterKnife.bind(this);
        listeners();
    }

    private void listeners() {

        next_bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==next_bt)
        {
            startActivity(new Intent(this, Dashboard.class));
        }

    }
}