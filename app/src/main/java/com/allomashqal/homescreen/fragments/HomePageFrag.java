package com.allomashqal.homescreen.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.allomashqal.R;
import com.allomashqal.Utils.Constants;
import com.allomashqal.Utils.Utility;
import com.allomashqal.controller.Controller;
import com.allomashqal.helper.LocaleHelper;
import com.allomashqal.homescreen.adapters.HomePageAdapter;
import com.allomashqal.homescreen.response.SalonListResponse;
import com.allomashqal.sharedpref.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;


public class HomePageFrag extends BaseFragment implements Controller.SalonListAPI {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.recyler_view)
    RecyclerView recyler_view;
    HomePageAdapter homePageAdapter;
    NestedScrollView nestedScrollView;
    ProgressBar progress_bar;
    View view;
    String locale;
    int itemCount = 0;
    ProgressDialog pd;
    Utility utility;
    Controller controller;
    Resources resources;
    ArrayList<SalonListResponse.Data.List> lists = new ArrayList<>();
    int page = 1, limit;

    private static int currentPage;
    private static int NUM_PAGES;

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_page, container, false);
        ButterKnife.bind(this, view);
        nestedScrollView = view.findViewById(R.id.nestedScrollView);
        progress_bar = view.findViewById(R.id.progress_bar);
        utility = new Utility();
        pd = new ProgressDialog(getContext());
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pd.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        pd.isIndeterminate() = true;
        pd.setCancelable(false);

        controller = new Controller(this);
        pd.show();
        pd.setContentView(R.layout.loading);

        locale = getStringVal(Constants.LOCALE);
        Context context = LocaleHelper.setLocale(getContext(), locale);
        resources = context.getResources();
        // type = getStringVal(Constants.TYPE);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayout.VERTICAL);
        recyler_view.setLayoutManager(manager);
        homePageAdapter = new HomePageAdapter(getContext(), locale, resources, lists);
        recyler_view.setAdapter(homePageAdapter);
        getData(page, limit);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    page++;
                    if (page <= limit) {
                        progress_bar.setVisibility(View.VISIBLE);
                        getData(page, limit);
                    } else {
                        progress_bar.setVisibility(View.GONE);
                    }
                }
            }
        });

        return view;
    }


    @SuppressLint("WrongConstant")
    @Override
    public void onSuccessSalonList(Response<SalonListResponse> success) {
        pd.dismiss();
        //lists = new ArrayList<>();
        if (success.isSuccessful()) {
            limit = success.body().getData().getTotalPage();
            for (int i = 0; i < success.body().getData().getList().size(); i++) {
                SalonListResponse.Data.List list = success.body().getData().getList().get(i);
                lists.add(list);

                homePageAdapter = new HomePageAdapter(getContext(), locale, resources, lists);
                recyler_view.setAdapter(homePageAdapter);
            }
        } else {
            Toast.makeText(getContext(), "" + success.message(), Toast.LENGTH_LONG).show();
        }
    }

    private void getData(int pagee, int limit) {
        progress_bar.setVisibility(View.VISIBLE);
        controller.SalonList("salon", getStringVal(Constants.LAT), getStringVal(Constants.LNG), String.valueOf(pagee));
    }

    @Override
    public void onError(String error) {
        pd.dismiss();
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }
}