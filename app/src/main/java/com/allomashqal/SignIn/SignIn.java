package com.allomashqal.SignIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.allomashqal.MapScreen.MapScreen;
import com.allomashqal.R;
import com.allomashqal.sharedpref.BaseActivity;
import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignIn extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.signin_bt)
    MaterialButton signin_bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        listeners();
    }

    private void listeners() {
        signin_bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==signin_bt)
        {
            startActivity(new Intent(this, MapScreen.class));
        }
    }
}