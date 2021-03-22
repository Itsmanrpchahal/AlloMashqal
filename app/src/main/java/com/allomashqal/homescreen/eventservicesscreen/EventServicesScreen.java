package com.allomashqal.homescreen.eventservicesscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.allomashqal.MapScreen.MapScreen;
import com.allomashqal.R;
import com.allomashqal.Utils.Constants;
import com.allomashqal.Utils.Utility;
import com.allomashqal.appmapview.AppMapView;
import com.allomashqal.controller.Controller;
import com.allomashqal.helper.LocaleHelper;
import com.allomashqal.homescreen.IF.EventService_IF;
import com.allomashqal.homescreen.adapters.EventServicesAdapter;
import com.allomashqal.homescreen.response.EventServiceDataResponse;
import com.allomashqal.sharedpref.BaseActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class EventServicesScreen extends BaseActivity implements OnMapReadyCallback, Controller.EventServiceAPI ,Controller.BookServiceAPI{

    @BindView(R.id.recylerview)
    RecyclerView recyler_view;
    @BindView(R.id.type)
    TextView typetv;
    @BindView(R.id.back)
    ImageButton back;
    @BindView(R.id.salon_name)
    TextView salon_name;
    @BindView(R.id.reviewtv)
    TextView reviewtv;
    String locale;
    AppMapView map;
    GoogleMap Gmap;
    Double lat = 0.0;
    Double lng = 0.0;
    Intent intent;

    @BindView(R.id.rating)
    RatingBar rating;

    String[] permissions = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    EventServicesAdapter eventServicesAdapter;
    EventService_IF eventService_if;
    Dialog dialog, confirmation_dialog;
    String type, providerID,serviceID;
    Resources resources;
    Controller controller;
    ProgressDialog pd;
    Utility utility;
    ArrayList<EventServiceDataResponse.Data.ServiceList> eventServiceDataResponses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_services_screen);
        ButterKnife.bind(this);
        type = getStringVal(Constants.TYPE);
        locale = getStringVal(Constants.LOCALE);
        utility = new Utility();
        intent = getIntent();
        providerID = intent.getStringExtra("providerID").toString();
        pd = new ProgressDialog(this);
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pd.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        pd.isIndeterminate() = true;
        pd.setCancelable(false);
        utility = new Utility();
        pd = new ProgressDialog(this);
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pd.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        pd.isIndeterminate() = true;
        pd.setCancelable(false);

        controller = new Controller(this,this);
        controller.EventServices(getStringVal(Constants.USERID), providerID);
        pd.show();
        pd.setContentView(R.layout.loading);

        if (getStringVal(Constants.TYPE).equals("salons")) {
            typetv.setText("Salons");
        } else {
            typetv.setText("Events services");
        }

        listeners();
        UpdateViews(locale);

    }

    private void UpdateViews(String locale) {
        Context context = LocaleHelper.setLocale(this, locale);
        resources = context.getResources();

        reviewtv.setText(resources.getText(R.string.review));
        if (getStringVal(Constants.TYPE).equals("salons")) {
            typetv.setText(resources.getText(R.string.salons));
            //  salon_name.setText(resources.getText(R.string.salonsname));
        } else {
            typetv.setText(resources.getText(R.string.eventsservice));
            //salon_name.setText(resources.getText(R.string.eventname));
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    private void OpenDetails(String id, String selectedDate, String selectedTime, Response<EventServiceDataResponse> success) {
        dialog = new Dialog(EventServicesScreen.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.bookingdetail_layout);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );


        MaterialButton bookingconfirmation, cancel_bt;
        TextView event_title, bookingdetailtv, servicetype, booking_date, total_bill, location;
        bookingconfirmation = dialog.findViewById(R.id.bookingconfirmation);
        bookingdetailtv = dialog.findViewById(R.id.bookingdetailtv);
        cancel_bt = dialog.findViewById(R.id.cancel_bt);
        event_title = dialog.findViewById(R.id.event_title);
        servicetype = dialog.findViewById(R.id.servicetype);
        booking_date = dialog.findViewById(R.id.booking_date);
        total_bill = dialog.findViewById(R.id.total_bill);
        location = dialog.findViewById(R.id.location);
        map = dialog.findViewById(R.id.map);
        map.getMapAsync(this);
        MapsInitializer.initialize(this);
        statusCheck();
        if (locale.equals("ar")) {
            bookingdetailtv.setGravity(Gravity.RIGHT);
            servicetype.setGravity(Gravity.RIGHT);
            booking_date.setGravity(Gravity.RIGHT);
            total_bill.setGravity(Gravity.RIGHT);
            location.setGravity(Gravity.RIGHT);
        }
        bookingdetailtv.setText(resources.getText(R.string.bookingdetail));
        servicetype.setText(success.body().getData().getServiceList().get(Integer.parseInt(id)).getTitle());
        booking_date.setText(selectedDate + " , " + selectedTime);
        total_bill.setText(resources.getText(R.string.totalbill)+"KD "+success.body().getData().getServiceList().get(Integer.parseInt(id)).getPrice());
        location.setText(resources.getText(R.string.location));
        cancel_bt.setText(resources.getText(R.string.cancel));
        bookingconfirmation.setText(resources.getText(R.string.bookingconfirmation));

        lat = Double.valueOf(success.body().getData().getProviderDetail().getLatitude());
        lng = Double.valueOf(success.body().getData().getProviderDetail().getLongitude());

        if (type.equals("salons")) {
            event_title.setText(R.string.salons);
        } else {
            event_title.setText(R.string.eventsservice);
        }


        cancel_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        bookingconfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                controller.BookService(getStringVal(Constants.USERID),providerID,success.body().getData().getServiceList().get(Integer.parseInt(id)).getId(),success.body().getData().getServiceList().get(Integer.parseInt(id)).getPrice(),selectedDate+" "+selectedTime,"service");
            }
        });

        if (selectedTime == null || selectedDate == null) {
            Toast.makeText(this, "Select date or time", Toast.LENGTH_SHORT).show();
        } else {
            dialog.show();
        }


    }

    private void confirmation_dialog() {
        confirmation_dialog = new Dialog(EventServicesScreen.this);
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
                dialog.dismiss();
            }
        });

        confirmation_dialog.show();
    }

    private void listeners() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Gmap = googleMap;
        Gmap.getUiSettings().setMyLocationButtonEnabled(true);
        Gmap.getUiSettings().setZoomControlsEnabled(true);
        Gmap.getUiSettings().setScrollGesturesEnabled(false);
        CameraPosition cameraPosition = CameraPosition.builder().target(new LatLng(lat, lng)).zoom(15).bearing(0).tilt(40).build();
        Gmap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        if (ActivityCompat.checkSelfPermission(EventServicesScreen.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EventServicesScreen.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Gmap.setMyLocationEnabled(false);

        // getCityName(lat, lng);

//        if (lat != null && lng != null) {
//            Gmap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
//                @Override
//                public void onCameraIdle() {
//                    LatLng midLatLng = Gmap.getCameraPosition().target;
//
//                    //latt = String.valueOf(midLatLng.latitude);
//                    //lnng = String.valueOf(midLatLng.longitude);
//
//                    Log.d("cameralatlong", "" + midLatLng.latitude + "    " + midLatLng.longitude);
//                    //getCityName(latt, lnng);
//                }
//            });
//        }
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(EventServicesScreen.this);
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
        final FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(EventServicesScreen.this);
        if (ActivityCompat.checkSelfPermission(EventServicesScreen.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(EventServicesScreen.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

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
                                    Toast.makeText(EventServicesScreen.this, "Unable to get Location", Toast.LENGTH_SHORT).show();
                                } else {
                                    lat = location.getLatitude();
                                    lng = location.getLongitude();
                                }

                                Log.d("Location", "" + lat + "" + lng);
                                Geocoder geocoder = new Geocoder(EventServicesScreen.this, Locale.getDefault());
                                try {
                                    List<Address> address = geocoder.getFromLocation(lat, lng, 1);


                                    if (map != null) {
                                        map.onCreate(null);
                                        map.onResume();
                                        map.getMapAsync(EventServicesScreen.this);
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        task.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EventServicesScreen.this, "Location Not Found...,Enter Location Manually...", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        }
    }

    @Override
    public void onSuccessEventService(Response<EventServiceDataResponse> success) {
        pd.dismiss();
        eventServiceDataResponses = new ArrayList<EventServiceDataResponse.Data.ServiceList>();
        if (success.isSuccessful()) {
            if (success.body().getSuccess() == true) {

                if (!success.body().getMessage().equals("No service.")) {
                    salon_name.setText(success.body().getData().getProviderDetail().getProviderName());
                    rating.setRating(success.body().getData().getProviderDetail().getRating());
                    for (int i = 0; i <= success.body().getData().getServiceList().size(); i++) {
                        eventServiceDataResponses = (ArrayList<EventServiceDataResponse.Data.ServiceList>) success.body().getData().getServiceList();
                    }
                    recyler_view.setLayoutManager(new GridLayoutManager(this, 2));
                    eventServicesAdapter = new EventServicesAdapter(this, locale, resources, eventServiceDataResponses);

                    recyler_view.setAdapter(eventServicesAdapter);
                    eventServicesAdapter.EventServicesAdapter(new EventService_IF() {
                        @Override
                        public void evenServiceID(String id, String selectedDate, String selectedTime) {
                            OpenDetails(id, selectedDate, selectedTime,success);
                        }
                    });
                } else {
                    Toast.makeText(this, success.body().getMessage(), Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(this, "" + success.message(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "" + success.message(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccessBookService(Response<BookServiceResponse> success) {
        pd.dismiss();
        if (success.isSuccessful())
        {
            if (success.body().getSuccess()==true)
            {
                confirmation_dialog();
            }else {
                Toast.makeText(this, success.message(), Toast.LENGTH_LONG).show();
            }
        } else  {
            Toast.makeText(this, success.message(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onError(String error) {
        pd.dismiss();
        Toast.makeText(this, "" + error, Toast.LENGTH_SHORT);
    }
}