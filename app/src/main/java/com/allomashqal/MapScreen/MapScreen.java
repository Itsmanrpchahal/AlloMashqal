package com.allomashqal.MapScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.allomashqal.R;
import com.allomashqal.Utils.Constants;
import com.allomashqal.dashboard.Dashboard;
import com.allomashqal.helper.LocaleHelper;
import com.allomashqal.sharedpref.BaseActivity;
import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapScreen extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.next_bt)
    MaterialButton next_bt;
    @BindView(R.id.maploc)
    TextView maploc;
    String locale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_screen);
        locale = getStringVal(Constants.LOCALE);

        ButterKnife.bind(this);
        listeners();
    }

    private void listeners() {

        next_bt.setOnClickListener(this);
    }

    private void UpdateViews(String locale) {
        Context context = LocaleHelper.setLocale(this, locale);
        Resources resources = context.getResources();
        maploc.setText(resources.getText(R.string.maploc));
        next_bt.setText(resources.getText(R.string.next));
    }

    @Override
    public void onClick(View v) {
        if (v==next_bt)
        {
            startActivity(new Intent(this, Dashboard.class));
        }

    }
}