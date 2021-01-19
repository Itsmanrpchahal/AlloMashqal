package com.allomashqal.MapScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.allomashqal.MainActivity;
import com.allomashqal.R;
import com.allomashqal.Utils.Constants;
import com.allomashqal.Utils.Utility;
import com.allomashqal.dashboard.Dashboard;
import com.allomashqal.helper.LocaleHelper;
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
import com.google.android.material.button.MaterialButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapScreen extends BaseActivity implements View.OnClickListener, OnMapReadyCallback {

    @BindView(R.id.next_bt)
    MaterialButton next_bt;
    @BindView(R.id.maploc)
    TextView maploc;
    String locale;
    @BindView(R.id.mapview)
    MapView mapview;
    GoogleMap Gmap;
    Double lat = 0.0;
    Double lng = 0.0;
    String[] permissions = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};
    public static final int MY_PERMISSIONS_REQUEST_READ_LOCATION = 121;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_screen);
        locale = getStringVal(Constants.LOCALE);
        Utility.checkPermissions(this,permissions);
        ButterKnife.bind(this);
        requestStoragePermission();
        mapview.onCreate(savedInstanceState);
        mapview.getMapAsync(this);
        MapsInitializer.initialize(this);
        listeners();
        requestStoragePermission();
        statusCheck();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapview.getMapAsync(this);

    }

    private void listeners() {

        next_bt.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        mapview.onResume();
        mapview.getMapAsync(this);
        super.onResume();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mapview.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapview.onLowMemory();
    }


    private void UpdateViews(String locale) {
        Context context = LocaleHelper.setLocale(this, locale);
        Resources resources = context.getResources();
        maploc.setText(resources.getText(R.string.maploc));
        next_bt.setText(resources.getText(R.string.next));
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(MapScreen.this);
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
        final FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(MapScreen.this);
        if (ActivityCompat.checkSelfPermission(MapScreen.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MapScreen.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

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
                                    Toast.makeText(MapScreen.this, "Unable to get Location", Toast.LENGTH_SHORT).show();
                                } else {
                                    lat = location.getLatitude();
                                    lng = location.getLongitude();
                                }

                                Log.d("Location", "" + lat + "" + lng);
                                Geocoder geocoder = new Geocoder(MapScreen.this, Locale.getDefault());
                                try {
                                    List<Address> address = geocoder.getFromLocation(lat, lng, 1);


                                    if (mapview != null) {
                                        mapview.onCreate(null);
                                        mapview.onResume();
                                        mapview.getMapAsync(MapScreen.this);
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        task.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MapScreen.this, "Location Not Found...,Enter Location Manually...", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == next_bt) {
            setStringVal(Constants.LAT, String.valueOf(lat));
            setStringVal(Constants.LNG, String.valueOf(lng));
            startActivity(new Intent(this, Dashboard.class));
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

        if (ActivityCompat.checkSelfPermission(MapScreen.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapScreen.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Gmap.setMyLocationEnabled(true);
        // getCityName(lat, lng);

        if (lat != null && lng != null) {
            Gmap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                @Override
                public void onCameraIdle() {
                    LatLng midLatLng = Gmap.getCameraPosition().target;

                    //latt = String.valueOf(midLatLng.latitude);
                    //lnng = String.valueOf(midLatLng.longitude);

                    Log.d("cameralatlong", "" + midLatLng.latitude + "    " + midLatLng.longitude);
                    //getCityName(latt, lnng);
                }
            });

        }
    }

    private void getCityName(String lat, String lng) {
        Geocoder geocoder = new Geocoder(MapScreen.this, Locale.getDefault());
        List<Address> address = null;
        try {
            address = geocoder.getFromLocation(Double.parseDouble(lat), Double.parseDouble(lng), 1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void requestStoragePermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                           // Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                        } else {

                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MapScreen.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setCancelable(false);
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

}