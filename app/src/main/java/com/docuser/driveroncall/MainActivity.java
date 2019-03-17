package com.docuser.driveroncall;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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

import com.docuser.driveroncall.Activity.AboutActivity;
import com.docuser.driveroncall.Activity.ContactUsActivity;
import com.docuser.driveroncall.Activity.LoginActivity;
import com.docuser.driveroncall.Activity.MyBookingActivity;
import com.docuser.driveroncall.Activity.MyTripsActivity;
import com.docuser.driveroncall.Activity.OneWayDateTimeActivity;
import com.docuser.driveroncall.Activity.PasswordnavChangeActivity;
import com.docuser.driveroncall.Activity.PaymentDefaultActivity;
import com.docuser.driveroncall.Activity.ProfileActivity;
import com.docuser.driveroncall.Activity.UserPrivacyPolicyActivity;
import com.docuser.driveroncall.common.Common;
import com.docuser.driveroncall.utility.PreferenceManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static android.os.Build.VERSION_CODES.M;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, com.google.android.gms.location.LocationListener, GoogleApiClient.OnConnectionFailedListener {
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
    LinearLayout destin;
    float red = 0, blue = 240;
    private MarkerOptions place1, place2;
    Marker dest, curr, my;
    Polyline line;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Toolbar toolbar;
    int PLACE_PICKER_REQUEST = 1;

    SupportMapFragment mapFragment;

    Button showPopupBtn, closePopupBtn;
    PopupWindow popupWindow;
    RelativeLayout linearLayout1;
    Dialog myDialog;
    ImageView btnOneTrip, roundTrip;
    PreferenceManager preferenceManager;

    FusedLocationProviderClient fusedLocationProviderClient;
    private String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission
            .ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = MainActivity.this;
        roundTrip = findViewById(R.id.ib_trip_round);

        current = findViewById(R.id.your_location);
        desti = findViewById(R.id.your_destination);
        destin = findViewById(R.id.dest_linear);
        destin.setVisibility(View.GONE);
        myCard = findViewById(R.id.myCard);

        btnOneTrip = findViewById(R.id.imgbtnonetrip);

        preferenceManager = new PreferenceManager(this);



        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Bundle bundle = getIntent().getExtras();

        if (bundle != null){

            if (bundle.getString("from").equalsIgnoreCase("2")){

                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage("We will get you a driver 45 minutes before your scheduled time");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }

        }

        initCode();




    }

    private void initCode() {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        btnOneTrip.setOnClickListener(v -> {
            if (destination != null && Current_loc != null) {
                Common.oneOrTwoWay = "1";
                startActivity(new Intent(MainActivity.this, OneWayDateTimeActivity.class));
            } else {
                Toast.makeText(context, "Please select place first", Toast.LENGTH_SHORT).show();
            }

        });
        roundTrip.setOnClickListener(v -> {
            if (destination != null && Current_loc != null) {
                Common.oneOrTwoWay = "2";
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

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.google_maps_key2));
        }
        AutocompleteSupportFragment autocompleteFragment_to = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment_to.setHint("Where to Go");
        autocompleteFragment_to.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG));
        autocompleteFragment_to.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.e("success", "" + place);
                Toast.makeText(getApplicationContext(), place.getName(), Toast.LENGTH_SHORT).show();
                destination = place.getLatLng();

                Common.myLatLong = destination;

                Common.placeName1 = place.getAddress();

                Common.placeName1City = getAddress(context, destination.latitude, destination.longitude);

                if (mMap != null) {
                    mMap.clear();

                }

                //desti.setText(place.getName());
                //destin.setVisibility(View.VISIBLE);
                // place2 = new MarkerOptions().position(destination).title("Place 2");
                mapFragment.getMapAsync(MainActivity.this);

            }

            @Override
            public void onError(@NonNull Status status) {
                Toast.makeText(context, "try again later", Toast.LENGTH_SHORT).show();
            }
        });

        /*PlaceAutocompleteFragment places = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        ((EditText) places.getView().findViewById(R.id.place_autocomplete_search_input)).setTextSize(12.0f);
        places.setHint("Pickup Point ");

        places.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                Toast.makeText(getApplicationContext(), place.getName(), Toast.LENGTH_SHORT).show();
                destination = place.getLatLng();

                Common.myLatLong = destination;

                Common.placeName1 = place.getAddress().toString();

                Common.placeName1City = getAddress(context, destination.latitude, destination.longitude);

                if (mMap != null) {
                    mMap.clear();

                }

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

*/

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.google_maps_key2));
        }
        AutocompleteSupportFragment autocompleteFragment_from = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_current);
        autocompleteFragment_from.setHint("Your Location");
        autocompleteFragment_from.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG));
        autocompleteFragment_from.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place_c) {
                Log.e("success", "" + place_c);
                Current_loc = place_c.getLatLng();

                Common.myLatLong2 = Current_loc;

                Common.placeName2City = getAddress(context, Current_loc.latitude, Current_loc.longitude);

                Common.placeName2 = place_c.getAddress();

                if (mMap != null) {
                    mMap.clear();

                }

                mapFragment.getMapAsync(MainActivity.this);
                Toast.makeText(getApplicationContext(), place_c.getName(), Toast.LENGTH_SHORT).show();
                // place1 = new MarkerOptions().position(Current_loc).title("Place 1");
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.e("ddd", "" + status);
                Log.e("ddd", "" + "@string/google_maps_key");
            }
        });


       /* PlaceAutocompleteFragment places_current = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_current);
        ((EditText) places_current.getView().findViewById(R.id.place_autocomplete_search_input)).setTextSize(12.0f);
        places_current.setHint("Destination ");
        places_current.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place_c) {
                Current_loc = place_c.getLatLng();

                Common.myLatLong2 = Current_loc;

                Common.placeName2City = getAddress(context, Current_loc.latitude, Current_loc.longitude);

                Common.placeName2 = place_c.getAddress().toString();

                if (mMap != null) {
                    mMap.clear();

                }

                mapFragment.getMapAsync(MainActivity.this);
                Toast.makeText(getApplicationContext(), place_c.getName(), Toast.LENGTH_SHORT).show();
                // place1 = new MarkerOptions().position(Current_loc).title("Place 1");
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });
*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
//---------------------------------navigation Name--------------------------------------------------------------------
        NavigationView navigationView = findViewById(R.id.nav_view);

        View header = navigationView.getHeaderView(0);

        userName = header.findViewById(R.id.lastName);

        userName.setText(preferenceManager.getKeyValueString("firstName") + " " + preferenceManager.getKeyValueString("lastName"));

        navigationView.setNavigationItemSelectedListener(this);


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finishAffinity();
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
        if (id == R.id.nav_payments) {
            startActivity(new Intent(MainActivity.this, PaymentDefaultActivity.class));

        } else if (id == R.id.nav_MyTrips) {

            startActivity(new Intent(MainActivity.this, MyTripsActivity.class));

        } else if (id == R.id.nav_change) {
            startActivity(new Intent(MainActivity.this, PasswordnavChangeActivity.class));

        } else if (id == R.id.nav_Profile) {

            startActivity(new Intent(MainActivity.this, ProfileActivity.class));


        } else if (id == R.id.nav_Mybook) {
            startActivity(new Intent(MainActivity.this, MyBookingActivity.class));

        } /*else if (id == R.id.nav_ContactUs) {

            startActivity(new Intent(MainActivity.this, ContactUsActivity.class));
        } else if (id == R.id.nav_UserPrivacyPolicy) {

        } */ else if (id == R.id.nav_UserLogout) {
            SharedPreferences preferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            finish();

            preferenceManager.clearPreferences();

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);

        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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

        double lat = (location.getLatitude());
        double lng = (location.getLongitude());
        Log.e("mylocation", "" + lat + lng);


        LatLng sydney = new LatLng(lat, lng);
        // LatLng sydney = new LatLng(wayLatitude,wayLongitude);
        //red source blue destination

        if (destination == null && Current_loc == null) {

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
            @SuppressLint("MissingPermission")
            @Override
            public void run() {

                Log.e("latttt", "" + wayLatitude);
                mMap = googleMap;

                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                mMap.getUiSettings().setAllGesturesEnabled(true);




                if (destination != null) {
                    if (dest != null) {
                        dest.remove();
                        if (line != null) {
                            line.remove();
                        }
                    }
                    dest = mMap.addMarker(new MarkerOptions().position(destination).title("" + Common.placeName1).icon(BitmapDescriptorFactory.defaultMarker(red)));
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
                    curr = mMap.addMarker(new MarkerOptions().position(Current_loc).title("" + Common.placeName2).icon(BitmapDescriptorFactory.defaultMarker(blue)));

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Current_loc, 15f));

                }

                if (destination != null && Current_loc != null) {


                    if (my != null) {
                        Log.e("blankkk", " 11111      " + curr);
                        my.remove();

                    }

           /* line = mMap.addPolyline(new PolylineOptions().geodesic(true)
                    .add(Current_loc, destination).width(10).color(Color.BLACK));*/
                    String url = getUrl(destination, Current_loc, "driving");

                    Log.e("url", " 11111      " + url);


                    dest = mMap.addMarker(new MarkerOptions().position(destination).title("" + Common.placeName1).icon(BitmapDescriptorFactory.defaultMarker(red)));
                    Log.e("deeee", "" + dest);
                    curr = mMap.addMarker(new MarkerOptions().position(Current_loc).title("" + Common.placeName2).icon(BitmapDescriptorFactory.defaultMarker(blue)));


                    FetchURL fetchURL = new FetchURL(MainActivity.this, mMap);

                    fetchURL.execute(url);


                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destination, 9f));


                }






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
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key2);
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

    public static String getAddress(Context context, double LATITUDE, double LONGITUDE) {
        String city = "";

        //Set Address
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null && addresses.size() > 0) {


                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                city = addresses.get(0).getLocality();

                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return city;
    }


}
