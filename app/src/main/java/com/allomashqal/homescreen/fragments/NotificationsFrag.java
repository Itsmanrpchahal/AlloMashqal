package com.allomashqal.homescreen.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.allomashqal.R;
import com.allomashqal.Utils.Constants;
import com.allomashqal.helper.LocaleHelper;
import com.allomashqal.homescreen.adapters.HomePageAdapter;
import com.allomashqal.homescreen.adapters.NotificationAdapter;
import com.allomashqal.sharedpref.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NotificationsFrag extends BaseFragment {

    View view;
    RecyclerView recyler_view;
    NotificationAdapter notificationAdapter;
    String locale;
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notifications, container, false);
        //ButterKnife.bind(NotificationsFrag.this,view);
        init(view);

        locale = getStringVal(Constants.LOCALE);
        Context context = LocaleHelper.setLocale(getContext(), locale);
        Resources resources = context.getResources();

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayout.VERTICAL);
        recyler_view.setLayoutManager(manager);
        notificationAdapter = new NotificationAdapter(getContext(),locale,resources);
        recyler_view.setAdapter(notificationAdapter);

        return view;
    }

    private void init(View view) {
        recyler_view = view.findViewById(R.id.recyler_view);
    }
}