package com.allomashqal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.allomashqal.Utils.Constants;
import com.allomashqal.helper.LocaleHelper;
import com.allomashqal.sharedpref.BaseActivity;
import com.allomashqal.signupsingin.SignUp_SignIn;
import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends BaseActivity implements View.OnClickListener {

     @BindView(R.id.arabic_bt)
    MaterialButton arabic_bt;
     @BindView(R.id.english_bt)
    MaterialButton english_bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        listeners();
    }

    private void listeners() {
        arabic_bt.setOnClickListener(this);
        english_bt.setOnClickListener(this);

//        if (getStringVal(Constants.LOCALE).equals("ar"))
//        {
//            Toast.makeText(this,"Arabic",Toast.LENGTH_LONG).show();
//        }else {
//            Toast.makeText(this,"English",Toast.LENGTH_LONG).show();
//        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    public void onClick(View v) {
        if (v==arabic_bt)
        {
            Context context = LocaleHelper.setLocale(this, "ar");
            Resources resources = context.getResources();
            startActivity(new Intent(this, SignUp_SignIn.class).putExtra(Constants.LOCALE,"ar"));
            setStringVal(Constants.LOCALE,"ar");

        }else if (v==english_bt)
        {
            Context context = LocaleHelper.setLocale(this, "en");
            Resources resources = context.getResources();
            startActivity(new Intent(this,SignUp_SignIn.class).putExtra(Constants.LOCALE,"en"));
            setStringVal(Constants.LOCALE,"en");
        }


    }
}