package com.allomashqal.SignIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.allomashqal.MapScreen.MapScreen;
import com.allomashqal.R;
import com.allomashqal.Utils.Constants;
import com.allomashqal.helper.LocaleHelper;
import com.allomashqal.sharedpref.BaseActivity;
import com.google.android.material.button.MaterialButton;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignIn extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.signin_tv)
    TextView signin_tv;
    @BindView(R.id.username_et)
    EditText username_et;
    @BindView(R.id.password_et)
    EditText password_et;
    @BindView(R.id.signin_bt)
    MaterialButton signin_bt;
    @BindView(R.id.forgotpassword)
    MaterialButton forgotpassword;

    String locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locale = getStringVal(Constants.LOCALE);
        if (locale.equals("en")) {
            setContentView(R.layout.activity_sign_in);
        } else {
            setContentView(R.layout.activity_sign_in_arabic);
        }
        ButterKnife.bind(this);
        listeners();
        UpdateViews(locale);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }


    private void UpdateViews(String locale) {
        Context context = LocaleHelper.setLocale(this, locale);
        Resources resources = context.getResources();
        signin_tv.setHint(resources.getText(R.string.signin));
        username_et.setHint(resources.getText(R.string.username));
        password_et.setHint(resources.getText(R.string.password));
        signin_bt.setText(resources.getText(R.string.signin));
        forgotpassword.setText(resources.getText(R.string.forgot));
    }

    private void listeners() {
        signin_bt.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == signin_bt) {
            startActivity(new Intent(this, MapScreen.class));
        }
    }
}