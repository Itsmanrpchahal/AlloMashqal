package com.allomashqal.homescreen;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allomashqal.MainActivity;
import com.allomashqal.R;
import com.allomashqal.Utils.Constants;
import com.allomashqal.Utils.Utility;
import com.allomashqal.appmapview.AppMapView;
import com.allomashqal.controller.Controller;
import com.allomashqal.helper.LocaleHelper;
import com.allomashqal.homescreen.adapters.TimeAdapter;
import com.allomashqal.homescreen.eventservicesscreen.BookServiceResponse;
import com.allomashqal.homescreen.eventservicesscreen.EventServicesScreen;
import com.allomashqal.homescreen.fragments.HomePageFrag;
import com.allomashqal.homescreen.fragments.IF.getTime_IF;
import com.allomashqal.homescreen.fragments.MyAccountFrag;
import com.allomashqal.homescreen.fragments.MyBookingFrag;
import com.allomashqal.homescreen.fragments.NotificationsFrag;
import com.allomashqal.homescreen.fragments.response.BookingResponse;
import com.allomashqal.offers.adpater.OffersAdapter;
import com.allomashqal.offers.adpater.Offers_IF;
import com.allomashqal.offers.response.OffersResponse;
import com.allomashqal.sharedpref.BaseActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.text.Text;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class HomeScreen extends BaseActivity implements Controller.OffersAPI, Controller.BookServiceAPI, OnMapReadyCallback {

    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.bottomnavigation)
    BottomNavigationView bottomnavigation;
    @BindView(R.id.type)
    TextView typetv;
    @BindView(R.id.offer_bt)
    TextView offer_bt;
    @BindView(R.id.search_et)
    EditText search_et;
    @BindView(R.id.search_bt)
    ImageButton search_bt;
    FragmentManager manager;
    FragmentTransaction fragmentTransaction;
    String type;
    Dialog offerDialog, offerDetails, datetimeDialog, confirmation_dialog;
    OffersAdapter offersAdapter;
    TimeAdapter timeAdapter;
    String locale;
    Toolbar toolbar;
    Controller controller;
    ProgressDialog pd;
    Utility utility;
    DatePickerDialog.OnDateSetListener dateSetListener;
    Calendar calendar;
    ArrayList<OffersResponse.Data.Offer> offersResponses = new ArrayList<OffersResponse.Data.Offer>();
    ArrayList<String> time = new ArrayList<>();
    AppMapView map;
    GoogleMap Gmap;
    Double lat = 0.0;
    Double lng = 0.0;
    public String Selectedtime = "";

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
        controller = new Controller(this, this);
        controller.Offers(getStringVal(Constants.USERID));

        fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.container, new HomePageFrag());
        fragmentTransaction.commit();
        if (type.equals("salons")) {
            typetv.setText(R.string.salons);
        } else {
            typetv.setText(R.string.eventsservice);
        }

        utility = new Utility();
        pd = new ProgressDialog(this);
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pd.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        pd.isIndeterminate() = true;
        pd.setCancelable(false);

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
        offersAdapter = new OffersAdapter(this, offersResponses);
        recyclerView.setAdapter(offersAdapter);
        offersAdapter.OffersAdapter(new Offers_IF() {
            @Override
            public void getOffer_ID(String id) {
                offerDialog.dismiss();
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

        TextView offer_title, offer_detail, offer_period, from;
        MaterialButton booking, cancel_bt;


        booking = offerDetails.findViewById(R.id.booking);
        offer_title = offerDetails.findViewById(R.id.offer_title);
        offer_detail = offerDetails.findViewById(R.id.offer_detail);
        offer_period = offerDetails.findViewById(R.id.offer_period);
        from = offerDetails.findViewById(R.id.from);
        map = offerDetails.findViewById(R.id.map);

        offer_title.setText(offersResponses.get(Integer.parseInt(id)).getTitle());
        offer_detail.setText(offersResponses.get(Integer.parseInt(id)).getDescription());
        offer_period.setText(offersResponses.get(Integer.parseInt(id)).getStartDate());
        from.setText(offersResponses.get(Integer.parseInt(id)).getEndDate());
        map.getMapAsync(this);
        MapsInitializer.initialize(this);
        statusCheck();
        lat = Double.valueOf(offersResponses.get(Integer.parseInt(id)).getLatitude());
        lng = Double.valueOf(offersResponses.get(Integer.parseInt(id)).getLongitude());

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
                TextView offer_detail, offer_price;

                recyler_view = datetimeDialog.findViewById(R.id.recyler_view);
                confirm_button = datetimeDialog.findViewById(R.id.confirm_button);
                select_date = datetimeDialog.findViewById(R.id.select_date);
                offer_detail = datetimeDialog.findViewById(R.id.offer_detail);
                offer_price = datetimeDialog.findViewById(R.id.offer_price);

                offer_detail.setText(offersResponses.get(Integer.parseInt(id)).getTitle());
                offer_price.setText("TN " + offersResponses.get(Integer.parseInt(id)).getPrice());

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

                        if (select_date.getText().toString().isEmpty() && Selectedtime.equals("")) {
                            select_date.requestFocus();
                            select_date.setError("Select Date");
                            Toast.makeText(context, "Select time", Toast.LENGTH_SHORT).show();

                        } else if (select_date.getText().toString().isEmpty()) {
                            select_date.requestFocus();
                            select_date.setError("Select Date");
                        } else if (Selectedtime.equals("")) {
                            Toast.makeText(context, "Select time", Toast.LENGTH_SHORT).show();
                        } else {
                            pd.show();
                            pd.setContentView(R.layout.loading);
                            controller.BookService(getStringVal(Constants.USERID), offersResponses.get(Integer.parseInt(id)).getProvider_id(), offersResponses.get(Integer.parseInt(id)).getId(), offersResponses.get(Integer.parseInt(id)).getPrice(), select_date.getText().toString() + " " + Selectedtime, "offer");

                        }
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
                            select_date.setText(i + "-" + i1 + "-" + i2);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                };

                LinearLayoutManager manager = new LinearLayoutManager(HomeScreen.this);
                manager.setOrientation(LinearLayout.HORIZONTAL);
                recyler_view.setLayoutManager(manager);
                timeAdapter = new TimeAdapter(HomeScreen.this, time);
                recyler_view.setAdapter(timeAdapter);
                timeAdapter.TimeAdapter(new getTime_IF() {
                    @Override
                    public void getTime_ID(String id) {
                        Selectedtime = id;
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


    @Override
    public void onSuccessOffers(Response<OffersResponse> success) {
        pd.dismiss();
        for (int i = 0; i < success.body().getData().getOffers().size(); i++) {
            offersResponses.add(success.body().getData().getOffers().get(i));
        }

        for (int i = 0; i < success.body().getData().getTimeList().size(); i++) {
            time.add(success.body().getData().getTimeList().get(i).toString());
        }

    }

//    @Override
//    public void onSuccessBookoing(Response<BookingResponse> success) {
//        pd.dismiss();
//        if (success.isSuccessful()) {
//           // datetimeDialog.dismiss();
//            confirmation_dialog();
//        }
//
//    }

    @Override
    public void onSuccessBookService(Response<BookServiceResponse> success) {
        pd.dismiss();
        if (success.isSuccessful()) {
            offerDetails.dismiss();
            datetimeDialog.dismiss();
            confirmation_dialog();
        } else {
            Toast.makeText(this, "" + success.message(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(String error) {
        pd.dismiss();
        Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        } else {

            CurrentLocation();

        }

    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void CurrentLocation() {
        final FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        } else {
            Task<Location> locationTask = client.getLastLocation();
            if (locationTask != null) {
                locationTask.addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {

                        task.addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // location(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));

                                if (location == null) {
                                    Toast.makeText(context, "Unable to get Location", Toast.LENGTH_SHORT).show();
                                } else {
                                    lat = location.getLatitude();
                                    lng = location.getLongitude();
                                }

                                Log.d("Location", "" + lat + "" + lng);
                                Geocoder geocoder = new Geocoder(HomeScreen.this, Locale.getDefault());
                                try {
                                    List<Address> address = geocoder.getFromLocation(lat, lng, 1);

                                    if (map != null) {
                                        map.onCreate(null);
                                        map.onResume();
                                        map.getMapAsync(HomeScreen.this);
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        task.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Location Not Found...,Enter Location Manually...", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Gmap = googleMap;
        Gmap.getUiSettings().setMyLocationButtonEnabled(true);
        Gmap.getUiSettings().setZoomControlsEnabled(true);
        Gmap.getUiSettings().setScrollGesturesEnabled(false);
        CameraPosition cameraPosition = CameraPosition.builder().target(new LatLng(lat, lng)).zoom(15).bearing(0).tilt(40).build();
        Gmap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Gmap.setMyLocationEnabled(false);
    }
}