package com.allomashqal.homescreen.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.allomashqal.R;
import com.allomashqal.homescreen.adapters.HomePageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomePageFrag extends Fragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.recyler_view) RecyclerView recyler_view;
    HomePageAdapter homePageAdapter;
    View view;

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_page, container, false);

        ButterKnife.bind(this,view);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayout.VERTICAL);
        recyler_view.setLayoutManager(manager);
        homePageAdapter = new HomePageAdapter(getContext());
        recyler_view.setAdapter(homePageAdapter);

        return view;
    }
}