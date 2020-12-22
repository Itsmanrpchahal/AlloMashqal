package com.allomashqal.homescreen.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.allomashqal.R;
import com.allomashqal.Utils.Constants;
import com.allomashqal.helper.LocaleHelper;
import com.allomashqal.sharedpref.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAccountFrag extends BaseFragment {

    String locale;
    View view;
    @BindView(R.id.password_et)
    EditText password_et;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_my_account, container, false);
        ButterKnife.bind(this,view);
        locale = getStringVal(Constants.LOCALE);
        if (locale.equals("ar"))
        {
            password_et.setGravity(Gravity.END);
        }


        return view;
    }
}