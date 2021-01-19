package com.allomashqal.homescreen.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.allomashqal.MainActivity;
import com.allomashqal.R;
import com.allomashqal.Utils.Constants;
import com.allomashqal.helper.LocaleHelper;
import com.allomashqal.sharedpref.BaseFragment;
import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAccountFrag extends BaseFragment {

    String locale;
    View view;
    MaterialButton logout;
    @BindView(R.id.password_et)
    EditText password_et;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        locale = getStringVal(Constants.LOCALE);
        if (locale.equals("ar")) {
            view = inflater.inflate(R.layout.fragment_my_account_arabic, container, false);

        } else {
            view = inflater.inflate(R.layout.fragment_my_account, container, false);
        }
        logout = view.findViewById(R.id.logout);

        ButterKnife.bind(this, view);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearStringVal(Constants.USERID);
                Intent intent = new Intent(getContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent
                .FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(intent);
                getActivity().finish();

            }
        });

        return view;
    }
}