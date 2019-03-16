package com.docuser.driveroncall.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;

import com.docuser.driveroncall.FetchURL;
import com.docuser.driveroncall.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

public class TripDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    TextView tvTripType, tvDate, tvKm, tvAmount, tvTime, pickupLoc, dropLoc;
    RatingBar tvReting;
    private GoogleMap mMap;
    Marker dest, curr, my;
    Polyline line;
    float red = 0, blue = 240;
    SupportMapFragment mapFragment;
    String pickuppointlat, pickuppointlang, droppointlat, droppointlang, PickupPointName, dropPointName;
    Double pickupLat, pickupLng, dropLat, dropLatd;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Booking Details");

        tvTripType = findViewById(R.id.tvTripType);
        tvDate = findViewById(R.id.tvTrip_Date);
        tvKm = findViewById(R.id.tvDistonsh_Km);
        tvAmount = findViewById(R.id.tvPayment);
        tvTime = findViewById(R.id.tvTime);
        tvReting = findViewById(R.id.retingBar);
        pickupLoc = findViewById(R.id.txt_pickupPoint);
        dropLoc = findViewById(R.id.txt_dropLocation);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Intent intent = getIntent();
        String pickupCityName = intent.getStringExtra("pickupCityName");
        String dropCityName = intent.getStringExtra("dropCityName");
        PickupPointName = intent.getStringExtra("PickupPointName");
        dropPointName = intent.getStringExtra("dropPointName");
        String tripType = intent.getStringExtra("tripType");
        String tripUsage = intent.getStringExtra("tripUsage");
        String tripAmount = intent.getStringExtra("tripAmount");
        String tripEndDate = intent.getStringExtra("tripEndDate");
        String tripEndTime = intent.getStringExtra("tripEndTime");
        String kms = intent.getStringExtra("kms");
        pickuppointlat = intent.getStringExtra("pickuppointlat");
        pickuppointlang = intent.getStringExtra("pickuppointlang");
        droppointlat = intent.getStringExtra("droppointlat");
        droppointlang = intent.getStringExtra("droppointlang");


        pickupLat = Double.parseDouble(pickuppointlat);
        pickupLng = Double.parseDouble(pickuppointlang);
        dropLat = Double.parseDouble(droppointlat);
        dropLatd = Double.parseDouble(droppointlang);

        tvKm.setText(kms + "Km");
        pickupLoc.setText(PickupPointName);
        dropLoc.setText(dropPointName);
        tvDate.setText(tripEndDate);
        tvTime.setText(tripEndTime);
        tvAmount.setText("₹" + tripAmount);
        if (tripType.equalsIgnoreCase("1")) {
            tvTripType.setText("One-Way Trip");

        } else if (tripType.equalsIgnoreCase("2")) {
            tvTripType.setText("Round Trip");

        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Double xlat, xlong;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                mMap = googleMap;
                LatLng destination = new LatLng(pickupLat, pickupLng);
                LatLng Current_loc = new LatLng(dropLat, dropLatd);

                if (destination != null) {
                    if (dest != null) {
                        dest.remove();
                        if (line != null) {
                            line.remove();
                        }
                    }
                    dest = mMap.addMarker(new MarkerOptions().position(destination).title(PickupPointName).icon(BitmapDescriptorFactory.defaultMarker(red)));
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
                    curr = mMap.addMarker(new MarkerOptions().position(Current_loc).title(dropPointName).icon(BitmapDescriptorFactory.defaultMarker(blue)));

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Current_loc, 15f));

                }

                if (destination != null && Current_loc != null) {




           /* line = mMap.addPolyline(new PolylineOptions().geodesic(true)
                    .add(Current_loc, destination).width(10).color(Color.BLACK));*/
                    String url = getUrl(destination, Current_loc, "driving");

                    Log.e("url", " 11111      " + url);

                    FetchURL fetchURL = new FetchURL(TripDetailsActivity.this, mMap);

                    fetchURL.execute(url);


                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destination, 11f));


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
