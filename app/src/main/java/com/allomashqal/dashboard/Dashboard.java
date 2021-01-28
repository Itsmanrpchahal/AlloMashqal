package com.allomashqal.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.allomashqal.R;
import com.allomashqal.Utils.Constants;
import com.allomashqal.homescreen.HomeScreen;
import com.allomashqal.sharedpref.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Dashboard extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.image1)
    ImageView image1;
    @BindView(R.id.image2)
    ImageView image2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        listeners();
    }

    private void listeners() {
        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==image1)
        {
            startActivity(new Intent(this, HomeScreen.class));
            setStringVal(Constants.TYPE,"salons");
            finish();
        }else {
            startActivity(new Intent(this, HomeScreen.class));
            setStringVal(Constants.TYPE,"eventservices");
            finish();
        }
    }
}