package com.abc.driveroncall;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abc.driveroncall.Activity.AboutActivity;
import com.abc.driveroncall.Activity.ContactUsActivity;
import com.abc.driveroncall.Activity.LoginActivity;
import com.abc.driveroncall.Activity.MyTripsActivity;
import com.abc.driveroncall.Activity.OneWayDateTimeActivity;
import com.abc.driveroncall.Activity.PasswordnavChangeActivity;
import com.abc.driveroncall.Activity.ProfileActivity;
import com.abc.driveroncall.Activity.UserPrivacyPolicyActivity;
import com.abc.driveroncall.common.Common;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.os.Build.VERSION_CODES.M;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks , com.google.android.gms.location.LocationListener, GoogleApiClient.OnConnectionFailedListener {
    TextView userName;
    EditText pickup, drop;
    LocationManager mLocationManager;
    LocationManager locationManager;
    String provider, lati, longi;
    Context context;
    Double wayLatitude = 0.0d;
    Double wayLongitude = 0.0d;
    LocationListener locationListener;
    private GoogleMap mMap;
    MapView mapView;
    CardView myCard;
    LatLng destination, Current_loc;
    TextView current, desti;
    double[] lat = {13.0827, 17.3850, 19.0760};
    double[] lng = {80.2707, 78.4867, 72.8777};
    LinearLayout destin;
    float red = 0, blue = 240;
    private MarkerOptions place1, place2;
    Marker dest, curr , my;
    Polyline line;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Toolbar toolbar;

    SupportMapFragment mapFragment;

    Button showPopupBtn, closePopupBtn;
    PopupWindow popupWindow;
    RelativeLayout linearLayout1;
    Dialog myDialog;
    ImageView btnOneTrip, roundTrip;

    FusedLocationProviderClient fusedLocationProviderClient;
    private String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission
            .ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = MainActivity.this;
        roundTrip = findViewById(R.id.ib_trip_round);

        current = findViewById(R.id.your_location);
        desti = findViewById(R.id.your_destination);
        destin = findViewById(R.id.dest_linear);
        destin.setVisibility(View.GONE);
        myCard = findViewById(R.id.myCard);

        btnOneTrip = (ImageView) findViewById(R.id.imgbtnonetrip);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initCode();


    }

    private void initCode() {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        btnOneTrip.setOnClickListener(v -> {
            if (destination != null && Current_loc != null) {
                Common.oneOrTwoWay = "oneway";
                startActivity(new Intent(MainActivity.this, OneWayDateTimeActivity.class));
            } else {
                Toast.makeText(context, "Please select place first", Toast.LENGTH_SHORT).show();
            }

        });
        roundTrip.setOnClickListener(v -> {
            if (destination != null && Current_loc != null) {
                Common.oneOrTwoWay = "twoway";
                startActivity(new Intent(MainActivity.this, OneWayDateTimeActivity.class));
            } else {
                Toast.makeText(context, "Please select Place first", Toast.LENGTH_SHORT).show();
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (Build.VERSION.SDK_INT >= M) {
            if (arePermissionsEnabled()) {
                getlocation();
            } else {
                requestMultiplePermissions();
            }
        }

        PlaceAutocompleteFragment places = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        ((EditText) places.getView().findViewById(R.id.place_autocomplete_search_input)).setTextSize(12.0f);
        places.setHint("Your Location");

        places.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                Toast.makeText(getApplicationContext(), place.getName(), Toast.LENGTH_SHORT).show();
                destination = place.getLatLng();

                Common.myLatLong = destination;

                Common.placeName1 = place.getAddress().toString();


                //desti.setText(place.getName());
                //destin.setVisibility(View.VISIBLE);
                // place2 = new MarkerOptions().position(destination).title("Place 2");
                mapFragment.getMapAsync(MainActivity.this);

            }

            @Override
            public void onError(Status status) {

                Toast.makeText(getApplicationContext(), status.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        PlaceAutocompleteFragment places_current = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_current);
        ((EditText) places_current.getView().findViewById(R.id.place_autocomplete_search_input)).setTextSize(12.0f);
        places.setHint("Where To Go");
        places_current.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place_c) {
                Current_loc = place_c.getLatLng();

                Common.myLatLong2 = Current_loc;

                Common.placeName2 = place_c.getAddress().toString();
                mapFragment.getMapAsync(MainActivity.this);
                Toast.makeText(getApplicationContext(), place_c.getName(), Toast.LENGTH_SHORT).show();
                // place1 = new MarkerOptions().position(Current_loc).title("Place 1");
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
//---------------------------------navigation Name--------------------------------------------------------------------
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        View header = navigationView.getHeaderView(0);

        userName = (TextView) header.findViewById(R.id.lastName);

        navigationView.setNavigationItemSelectedListener(this);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_ContactUs) {
            startActivity(new Intent(MainActivity.this, ContactUsActivity.class));
            return true;
        }
        if (id == R.id.action_About) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
            return true;
        }
        if (id == R.id.action_Privacy) {
            startActivity(new Intent(MainActivity.this, UserPrivacyPolicyActivity.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("ApplySharedPref")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_Book_Now) {
            startActivity(new Intent(MainActivity.this, MainActivity.class));


            // Handle the camera action
        }

        //code is don but sum problem to code hide
        else if (id == R.id.nav_MyTrips) {

            startActivity(new Intent(MainActivity.this, MyTripsActivity.class));

        } else if (id == R.id.nav_change) {
            startActivity(new Intent(MainActivity.this, PasswordnavChangeActivity.class));

        } else if (id == R.id.nav_Profile) {

            startActivity(new Intent(MainActivity.this, ProfileActivity.class));


        } /*else if (id == R.id.nav_About) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));

        } else if (id == R.id.nav_ContactUs) {

            startActivity(new Intent(MainActivity.this, ContactUsActivity.class));
        } else if (id == R.id.nav_UserPrivacyPolicy) {

        } */ else if (id == R.id.nav_UserLogout) {
            SharedPreferences preferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            finish();

/*

            SharedPreferences preferences =getSharedPreferences("loginPrefs",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            finish();
*/

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);
            // startActivity(new Intent(MainActivity.this, LoginActivity.class));

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @RequiresApi(api = M)

    private boolean arePermissionsEnabled() {
        for (String permission : permissions) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    @RequiresApi(api = M)

    private void requestMultiplePermissions() {
        List<String> remainingPermissions = new ArrayList<>();

        for (String permission : permissions) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                remainingPermissions.add(permission);
            }
        }
        requestPermissions(remainingPermissions.toArray
                (new String[remainingPermissions.size()]), 101);

        initCode();
    }

    @TargetApi(M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    if (shouldShowRequestPermissionRationale(permissions[i])) {
                        new AlertDialog.Builder(this)
                                .setMessage("Unable to get your Location")
                                .setPositiveButton("Allow", (dialog, which) -> requestMultiplePermissions())
                                .create()
                                .show();
                    }
                    return;
                }
            }

            initCode();

        }
    }


    @Override
    public void onLocationChanged(Location location) {
        double lat = (Double) (location.getLatitude());
        double lng = (Double) (location.getLongitude());
        Log.e("mylocation", "" + lat + lng);


        LatLng sydney = new LatLng(lat, lng);
        // LatLng sydney = new LatLng(wayLatitude,wayLongitude);
        //red source blue destination
        if (destination != null) {
            if (dest != null) {
                dest.remove();
                if (line != null) {
                    line.remove();
                }
            }
            dest = mMap.addMarker(new MarkerOptions().position(destination).title(""+Common.placeName1).icon(BitmapDescriptorFactory.defaultMarker(red)));
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
            curr = mMap.addMarker(new MarkerOptions().position(Current_loc).title(""+Common.placeName2).icon(BitmapDescriptorFactory.defaultMarker(blue)));

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Current_loc, 15f));

        }

        if (destination != null && Current_loc != null){


            if (my != null) {
                Log.e("blankkk", " 11111      " + curr);
                my.remove();

            }

           /* line = mMap.addPolyline(new PolylineOptions().geodesic(true)
                    .add(Current_loc, destination).width(10).color(Color.BLACK));*/
            String url = getUrl(destination, Current_loc,"driving");

            Log.e("url", " 11111      " + url);

            FetchURL fetchURL= new FetchURL(this,mMap);

            fetchURL.execute(url);



            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destination, 13f));


        }else if (destination == null && Current_loc == null){

            if (my != null) {
                Log.e("blankkk", " 11111      " + curr);
                my.remove();

            }
                my = mMap.addMarker(new MarkerOptions().position(sydney).title("Your Location").icon(BitmapDescriptorFactory.defaultMarker(200)));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f));

            }


        }




    public void getlocation() {


        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // reuqest for permission

        } else {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener((Activity) context, location -> {
                if (location != null) {
                    wayLatitude = location.getLatitude();
                    wayLongitude = location.getLongitude();
                    Common.wayLatitude = wayLatitude;
                    Common.wayLongitude = wayLongitude;

                    getAddress(wayLatitude, wayLongitude);
                    Log.e("lats_12", "longs" + wayLatitude + wayLongitude);
                    // Toast.makeText(context, ""+wayLatitude, Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(context, "try again", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
            add = add + "\n" + obj.getCountryName();
            add = add + "\n" + obj.getCountryCode();
            add = add + "\n" + obj.getAdminArea();
            add = add + "\n" + obj.getPostalCode();
            add = add + "\n" + obj.getSubAdminArea();
            add = add + "\n" + obj.getLocality();
            add = add + "\n" + obj.getSubThoroughfare();
            String loc = obj.getLocality() + " " + obj.getSubThoroughfare();
            current.setText(loc);
            Log.v("IGA", "Address" + add);
            // Toast.makeText(this, "Address=>" + add,
            // Toast.LENGTH_SHORT).show();

            // TennisAppActivity.showDialog(add);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Double xlat, xlong;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Log.e("latttt", "" + wayLatitude);
                mMap = googleMap;

                buildGoogleApiClient();
                //   mMap.setMyLocationEnabled(true);


        mMap.getUiSettings().setMyLocationButtonEnabled(false);

            }
        }, 1000);

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
