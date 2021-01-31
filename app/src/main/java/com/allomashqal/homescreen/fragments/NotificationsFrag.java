package com.allomashqal.homescreen.fragments;

import android.annotation.SuppressLint;
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
import com.allomashqal.homescreen.adapters.NotificationAdapter;
import com.allomashqal.homescreen.fragments.response.NotificationsResponse;
import com.allomashqal.homescreen.response.SalonListResponse;
import com.allomashqal.sharedpref.BaseFragment;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;


public class NotificationsFrag extends BaseFragment implements Controller.NotificationsAPI {

    View view;
    RecyclerView recyler_view;
    NotificationAdapter notificationAdapter;
    String locale;
    ProgressDialog pd;
    Utility utility;
    Controller controller;
    ArrayList<NotificationsResponse.Datum> lists = new ArrayList<>();
    Resources resources;

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
        resources = context.getResources();


        return view;
    }

    private void init(View view) {
        recyler_view = view.findViewById(R.id.recyler_view);
        utility = new Utility();
        pd = new ProgressDialog(getContext());
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pd.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        pd.isIndeterminate() = true;
        pd.setCancelable(false);

        controller = new Controller(this);
        pd.show();
        pd.setContentView(R.layout.loading);
        controller.Notifications(getStringVal(Constants.USERID));
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onSuccessNotifications(Response<NotificationsResponse> success) {
        pd.dismiss();
        if (success.isSuccessful()) {
            if (success.body().getSuccess()==true)
            {
                for (int i = 0; i < success.body().getData().size(); i++) {
                    NotificationsResponse.Datum list = success.body().getData().get(i);
                    lists.add(list);

                    LinearLayoutManager manager = new LinearLayoutManager(getContext());
                    manager.setOrientation(LinearLayout.VERTICAL);
                    recyler_view.setLayoutManager(manager);
                    notificationAdapter = new NotificationAdapter(getContext(), locale, resources,lists);
                    recyler_view.setAdapter(notificationAdapter);

                }
            } else  {
                Toast.makeText(getContext(), "" + success.message(), Toast.LENGTH_SHORT).show();
            }


        } else {
            Toast.makeText(getContext(), "" + success.message(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(String error) {
        pd.dismiss();
        Toast.makeText(getContext(), "" + error, Toast.LENGTH_SHORT).show();
    }
}