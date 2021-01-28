package com.allomashqal.homescreen;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allomashqal.MainActivity;
import com.allomashqal.R;
import com.allomashqal.Utils.Constants;
import com.allomashqal.helper.LocaleHelper;
import com.allomashqal.homescreen.adapters.TimeAdapter;
import com.allomashqal.homescreen.eventservicesscreen.EventServicesScreen;
import com.allomashqal.homescreen.fragments.HomePageFrag;
import com.allomashqal.homescreen.fragments.MyAccountFrag;
import com.allomashqal.homescreen.fragments.MyBookingFrag;
import com.allomashqal.homescreen.fragments.NotificationsFrag;
import com.allomashqal.offers.adpater.OffersAdapter;
import com.allomashqal.offers.adpater.Offers_IF;
import com.allomashqal.sharedpref.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeScreen extends BaseActivity {

    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.bottomnavigation)
    BottomNavigationView bottomnavigation;
    @BindView(R.id.type)
    TextView typetv;
    @BindView(R.id.offer_bt)
    TextView offer_bt;
    FragmentManager manager;
    FragmentTransaction fragmentTransaction;
    String type;
    Dialog offerDialog, offerDetails, datetimeDialog, confirmation_dialog;
    OffersAdapter offersAdapter;
    TimeAdapter timeAdapter;
    String locale;
    Toolbar toolbar;

    DatePickerDialog.OnDateSetListener dateSetListener;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);
        locale = getStringVal(Constants.LOCALE);
        type = getStringVal(Constants.TYPE);
        manager = getSupportFragmentManager();
        calendar = Calendar.getInstance();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.container, new HomePageFrag());
        fragmentTransaction.commit();
        if (type.equals("salons")) {
            typetv.setText(R.string.salons);
        } else {
            typetv.setText(R.string.eventsservice);
        }

        listeners();
        UpdateViews(locale);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.logout:
                clearStringVal(Constants.USERID);
                Intent intent = new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void UpdateViews(String locale) {
        Context context = LocaleHelper.setLocale(this, locale);
        Resources resources = context.getResources();
        offer_bt.setText(resources.getText(R.string.offers));
    }

    private void listeners() {

        offer_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOffersDialog();
            }
        });

        bottomnavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        fragmentTransaction = manager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, new HomePageFrag());
                        fragmentTransaction.commit();

                        return true;

                    case R.id.booking:
                        fragmentTransaction = manager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, new MyBookingFrag());
                        fragmentTransaction.commit();

                        return true;

                    case R.id.Notifications:
                        fragmentTransaction = manager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, new NotificationsFrag());
                        fragmentTransaction.commit();

                        return true;

                    case R.id.myaccount:
                        fragmentTransaction = manager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, new MyAccountFrag());
                        fragmentTransaction.commit();

                        return true;

                }

                return false;
            }
        });
    }

    @SuppressLint("WrongConstant")
    private void showOffersDialog() {

        offerDialog = new Dialog(this);
        offerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        offerDialog.setCancelable(true);
        offerDialog.setContentView(R.layout.offerdialog);
        offerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = offerDialog.getWindow();
        window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );

        RecyclerView recyclerView;
        recyclerView = offerDialog.findViewById(R.id.recyler_view);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(manager);
        offersAdapter = new OffersAdapter(this);
        recyclerView.setAdapter(offersAdapter);
        offersAdapter.OffersAdapter(new Offers_IF() {
            @Override
            public void getOffer_ID(String id) {
                offerDetailDialog(id);
            }
        });

        offerDialog.show();
    }

    private void offerDetailDialog(String id) {
        offerDetails = new Dialog(this);
        offerDetails.requestWindowFeature(Window.FEATURE_NO_TITLE);
        offerDetails.setCancelable(true);
        offerDetails.setContentView(R.layout.offerdetails_layout);
        offerDetails.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = offerDetails.getWindow();
        window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );

        MaterialButton booking, cancel_bt;
        booking = offerDetails.findViewById(R.id.booking);


        booking.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                datetimeDialog = new Dialog(HomeScreen.this);
                datetimeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                datetimeDialog.setCancelable(true);
                datetimeDialog.setContentView(R.layout.startbooking_dialog);
                datetimeDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Window window = datetimeDialog.getWindow();
                window.setLayout(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT
                );

                MaterialButton cancel_bt, confirm_button;
                RecyclerView recyler_view;
                EditText select_date;

                recyler_view = datetimeDialog.findViewById(R.id.recyler_view);
                confirm_button = datetimeDialog.findViewById(R.id.confirm_button);
                select_date = datetimeDialog.findViewById(R.id.select_date);

                if (locale.equals("ar")) {
                    select_date.setGravity(Gravity.RIGHT);
                }

                cancel_bt = datetimeDialog.findViewById(R.id.cancel_bt);
                cancel_bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datetimeDialog.dismiss();
                    }
                });

                confirm_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmation_dialog();
                    }
                });

                select_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(HomeScreen.this, R.style.DialogTheme
                                , dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                        datePickerDialog.show();
                    }
                });

                dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(Calendar.YEAR, i);
                        calendar.set(Calendar.MONTH, i1);
                        calendar.set(Calendar.DAY_OF_MONTH, i2);
                        String format = "MM/dd/yyyy";
                        DateFormat simpleDateFormat = new java.text.SimpleDateFormat(format, Locale.US);
                        SimpleDateFormat format1 = new SimpleDateFormat("DD//MM/YYYY");
                        Date date, date1 = null;
                        try {
                            date = simpleDateFormat.parse(simpleDateFormat.format(calendar.getTime()));
                            date1 = format1.parse(format1.format(calendar.getTime()));
                            select_date.setText(simpleDateFormat.format(calendar.getTime()));

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                };

                LinearLayoutManager manager = new LinearLayoutManager(HomeScreen.this);
                manager.setOrientation(LinearLayout.HORIZONTAL);
                recyler_view.setLayoutManager(manager);
                timeAdapter = new TimeAdapter(HomeScreen.this);
                recyler_view.setAdapter(timeAdapter);
                offersAdapter.OffersAdapter(new Offers_IF() {
                    @Override
                    public void getOffer_ID(String id) {

                    }
                });


                datetimeDialog.show();
            }
        });

        offerDetails.show();
    }

    private void confirmation_dialog() {
        confirmation_dialog = new Dialog(this);
        confirmation_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirmation_dialog.setCancelable(true);
        confirmation_dialog.setContentView(R.layout.booking_confirmation);
        confirmation_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        Window window = confirmation_dialog.getWindow();
        window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );

        MaterialButton cancel_bt;
        cancel_bt = confirmation_dialog.findViewById(R.id.cancel_bt);
        cancel_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmation_dialog.dismiss();
            }
        });

        confirmation_dialog.show();
    }


}