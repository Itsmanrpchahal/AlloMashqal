package com.allomashqal.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.allomashqal.R;
import com.allomashqal.Utils.Constants;
import com.allomashqal.helper.LocaleHelper;
import com.allomashqal.sharedpref.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUp extends BaseActivity {

    @BindView(R.id.signup_tv)
    TextView signup_tv;
    @BindView(R.id.username_et)
    EditText username_et;
    @BindView(R.id.password_et)
    EditText password_et;
    @BindView(R.id.mobileno_et)
    EditText mobileno_et;
    @BindView(R.id.email_et)
    EditText email_et;

    String locale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locale = getStringVal(Constants.LOCALE);
        if (locale.equals("en"))
        {
            setContentView(R.layout.activity_sign_up);
        }    else  {
            setContentView(R.layout.activity_sign_up_arabic);
        }

        ButterKnife.bind(this);


        UpdateViews(locale);
    }

    private void UpdateViews(String locale) {
        Context context = LocaleHelper.setLocale(this,locale);
        Resources resources = context.getResources();
        signup_tv.setText(resources.getText(R.string.signup));
        username_et.setHint(resources.getText(R.string.username));
        password_et.setHint(resources.getText(R.string.password));
        mobileno_et.setHint(resources.getText(R.string.mobileno));
        email_et.setHint(resources.getText(R.string.email));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
}