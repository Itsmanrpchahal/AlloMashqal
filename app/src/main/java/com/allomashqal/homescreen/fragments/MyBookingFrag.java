package com.allomashqal.homescreen.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

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
import com.allomashqal.homescreen.adapters.BookingAdapter;
import com.allomashqal.homescreen.adapters.NotificationAdapter;
import com.allomashqal.sharedpref.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyBookingFrag extends BaseFragment {

    View view;
    @BindView(R.id.recyler_view)
    RecyclerView recyler_view;
    BookingAdapter bookingAdapter;
    String locale;

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_booking, container, false);
        locale = getStringVal(Constants.LOCALE);
        ButterKnife.bind(this,view);
        Context context = LocaleHelper.setLocale(getContext(), locale);
        Resources resources = context.getResources();

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayout.VERTICAL);
        recyler_view.setLayoutManager(manager);

        bookingAdapter = new BookingAdapter(getContext(),locale,resources);
        recyler_view.setAdapter(bookingAdapter);
        return view;
    }


}