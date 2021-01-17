package com.allomashqal.signupsingin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.allomashqal.MapScreen.MapScreen;
import com.allomashqal.R;
import com.allomashqal.SignIn.SignIn;
import com.allomashqal.SignUp.SignUp;
import com.allomashqal.Utils.Constants;
import com.allomashqal.helper.LocaleHelper;
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
    String locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locale = getStringVal(Constants.LOCALE);

        setContentView(R.layout.activity_sign_up__sign_in);
        ButterKnife.bind(this);

        if (!getStringVal(Constants.USERID).equals(""))
        {
            Intent intent = new Intent(this, MapScreen.class);
            startActivity(intent);
        }

        UpdateViews(locale);
        listeners();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }


    private void UpdateViews(String locale) {
        Context context = LocaleHelper.setLocale(this,locale);
        Resources resources = context.getResources();

        signup_bt.setText(resources.getText(R.string.signup));
        signin_bt.setText(resources.getText(R.string.signin));
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