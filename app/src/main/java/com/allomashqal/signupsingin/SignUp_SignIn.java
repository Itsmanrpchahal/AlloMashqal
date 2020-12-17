package com.allomashqal.signupsingin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.allomashqal.R;
import com.allomashqal.SignIn.SignIn;
import com.allomashqal.SignUp.SignUp;
import com.allomashqal.Utils.Constants;
import com.allomashqal.sharedpref.BaseActivity;
import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUp_SignIn extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.signup_bt)
    MaterialButton signup_bt;
    @BindView(R.id.signin_bt)
    MaterialButton signin_bt;
    @BindView(R.id.signinasvisitor_bt)
    MaterialButton signinasvisitor_bt;
    String locale="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__sign_in);
        ButterKnife.bind(this);
        locale = getStringVal(Constants.LOCALE);


        listeners();
    }

    private void listeners() {

        signup_bt.setOnClickListener(this);
        signin_bt.setOnClickListener(this);
        signinasvisitor_bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==signin_bt)
        {
        startActivity(new Intent(this, SignIn.class));
        }else  if (v==signup_bt)
        {
            startActivity(new Intent(this, SignUp.class));
        }else if (v== signinasvisitor_bt)
        {

        }
    }
}