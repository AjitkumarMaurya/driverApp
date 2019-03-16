package com.docuser.driveroncall.Activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.docuser.driveroncall.FetchURL;
import com.docuser.driveroncall.R;
import com.docuser.driveroncall.common.Common;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import java.util.Objects;


public class OneWayDateTimeActivity extends AppCompatActivity implements OnMapReadyCallback {
    Button btnTimeDate;

    TextView place1, place2;
    private GoogleMap mMap;
    Marker dest, curr, my;

    Polyline line;
    float red = 0, blue = 240,green =120;

    SupportMapFragment mapFragment;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_way_date_time);
        btnTimeDate = findViewById(R.id.btntimeDate);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if (Common.oneOrTwoWay.equals("1")) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("One Way");
        } else {
            Objects.requireNonNull(getSupportActionBar()).setTitle("Round trip");
        }

        place1 = findViewById(R.id.place1);
        place2 = findViewById(R.id.place2);
        place1.setText(Common.placeName1);
        place2.setText(Common.placeName2);

        btnTimeDate.setOnClickListener(v -> {


            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment prev = getSupportFragmentManager().findFragmentByTag("paydialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);

            DialogFragment dialogFragment = new DialogDateTimeFragment();
            dialogFragment.setCancelable(false);
            dialogFragment.show(ft, "paydialog");

        });


        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_2);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Double xlat, xlong;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                mMap = googleMap;
                LatLng destination = Common.myLatLong;
                LatLng Current_loc = Common.myLatLong2;

                if (destination != null) {
                    if (dest != null) {
                        dest.remove();
                        if (line != null) {
                            line.remove();
                        }
                    }
                    dest = mMap.addMarker(new MarkerOptions().position(destination).title("" + Common.placeName1).icon(BitmapDescriptorFactory.defaultMarker(green)));
                    Log.e("deeee", "" + dest);

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destination, 15f));


                }
                if (Current_loc != null) {
                    if (curr != null) {
                        Log.e("blankkk", "" + curr);
                        curr.remove();
                        if (line != null) {
                            line.remove();
                        }

                    }
                    curr = mMap.addMarker(new MarkerOptions().position(Current_loc).title("" + Common.placeName2).icon(BitmapDescriptorFactory.defaultMarker(red)));

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Current_loc, 15f));

                }

                if (destination != null && Current_loc != null) {


                    String url = getUrl(destination, Current_loc, "driving");

                    Log.e("url", " 11111      " + url);

                    FetchURL fetchURL = new FetchURL(OneWayDateTimeActivity.this, mMap);

                    fetchURL.execute(url);


                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destination, 9f));


                }
            }
        }, 1000);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;

    }

}
