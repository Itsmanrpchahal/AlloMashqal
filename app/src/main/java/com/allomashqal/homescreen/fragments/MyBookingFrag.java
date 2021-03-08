package com.allomashqal.homescreen.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

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
import com.allomashqal.homescreen.adapters.BookingAdapter;
import com.allomashqal.homescreen.adapters.HomePageAdapter;
import com.allomashqal.homescreen.adapters.NotificationAdapter;
import com.allomashqal.homescreen.fragments.response.BookingResponse;
import com.allomashqal.homescreen.response.SalonListResponse;
import com.allomashqal.sharedpref.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class MyBookingFrag extends BaseFragment implements Controller.BookingAPI {

    View view;
    @BindView(R.id.recyler_view)
    RecyclerView recyler_view;
    BookingAdapter bookingAdapter;
    String locale;
    ProgressDialog pd;
    Utility utility;
    Controller controller;
    Resources resources;
    ArrayList<BookingResponse.Appointment> lists = new ArrayList<>();

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_booking, container, false);
        locale = getStringVal(Constants.LOCALE);
        ButterKnife.bind(this,view);
        Context context = LocaleHelper.setLocale(getContext(), locale);
         resources = context.getResources();

        utility = new Utility();
        pd = new ProgressDialog(getContext());
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pd.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        pd.isIndeterminate() = true;
        pd.setCancelable(false);


        controller = new Controller(this);
        pd.show();
        pd.setContentView(R.layout.loading);
        controller.Booking(getStringVal(Constants.USERID));

        return view;
    }


    @SuppressLint("WrongConstant")
    @Override
    public void onSuccessBookoing(Response<BookingResponse> success) {
        pd.dismiss();
        if (success.isSuccessful())
        {
            if (success.body().getSuccess()==true)
            {
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                manager.setOrientation(LinearLayout.VERTICAL);
                recyler_view.setLayoutManager(manager);

                for (int i = 0; i < success.body().getData().getAppointments().size(); i++) {
                    BookingResponse.Appointment list = success.body().getData().getAppointments().get(i);
                    lists.add(list);

                    bookingAdapter = new BookingAdapter(getContext(), locale, resources, lists);
                    recyler_view.setAdapter(bookingAdapter);
                }




            } else  {
                Toast.makeText(getContext(),""+success.message(),Toast.LENGTH_SHORT).show();
            }
        }else  {
            Toast.makeText(getContext(),""+success.message(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public  void onError(String error) {
        pd.dismiss();
        Toast.makeText(getContext(),""+error,Toast.LENGTH_SHORT).show();
    }
}