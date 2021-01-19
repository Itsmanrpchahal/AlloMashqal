package com.allomashqal.homescreen.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.allomashqal.R;
import com.allomashqal.Utils.Constants;
import com.allomashqal.Utils.Utility;
import com.allomashqal.controller.Controller;
import com.allomashqal.helper.LocaleHelper;
import com.allomashqal.homescreen.adapters.HomePageAdapter;
import com.allomashqal.homescreen.adapters.PaginationListener;
import com.allomashqal.homescreen.response.SalonListResponse;
import com.allomashqal.sharedpref.BaseFragment;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

import static com.allomashqal.homescreen.adapters.PaginationListener.PAGE_START;


public class HomePageFrag extends BaseFragment implements Controller.SalonListAPI {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.recyler_view)
    RecyclerView recyler_view;
    HomePageAdapter homePageAdapter;

    View view;
    String locale;
    int itemCount = 0;
    ProgressDialog pd;
    Utility utility;
    Controller controller;
    Resources resources;
    ArrayList<SalonListResponse.Data.List> lists;

    private static int currentPage;
    private static int NUM_PAGES;

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_page, container, false);
        ButterKnife.bind(this, view);
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

        controller.SalonList("salon", getStringVal(Constants.LAT), getStringVal(Constants.LNG), "1");


        return view;
    }


    @SuppressLint("WrongConstant")
    @Override
    public void onSuccessSalonList(Response<SalonListResponse> success) {
        pd.dismiss();
        lists = new ArrayList<>();
        if (success.isSuccessful())
        {
            for (int i = 0; i < success.body().getData().getList().size(); i++) {
                SalonListResponse.Data.List list = success.body().getData().getList().get(i);
                lists.add(list);
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                manager.setOrientation(LinearLayout.VERTICAL);
                recyler_view.setLayoutManager(manager);
                homePageAdapter = new HomePageAdapter(getContext(), locale, resources, lists);
                recyler_view.setAdapter(homePageAdapter);
            }
        }else  {
            Toast.makeText(getContext(),""+success.message(),Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onError(String error) {
        pd.dismiss();
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }
}