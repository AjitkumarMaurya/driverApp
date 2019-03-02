package com.abc.driveroncall.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.abc.driveroncall.FetchURL;
import com.abc.driveroncall.R;
import com.abc.driveroncall.common.Common;
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
    float red = 0, blue = 240;
    DatePickerDialog datePickerDialog;
    TimePickerDialog picker;
    String dateString = "";
    String timeSTring = "";
    private int mYear, mMonth, mDay, mHour, mMinute;

    SupportMapFragment mapFragment;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_way_date_time);
        btnTimeDate = findViewById(R.id.btntimeDate);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if (Common.oneOrTwoWay.equals("oneway")) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("One Way");
        } else {
            Objects.requireNonNull(getSupportActionBar()).setTitle("Two Way");
        }

        place1 = findViewById(R.id.place1);
        place2 = findViewById(R.id.place2);
        place1.setText(Common.placeName1);
        place2.setText(Common.placeName2);

        btnTimeDate.setOnClickListener(v -> {


            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(OneWayDateTimeActivity.this);
// ...Irrelevant code for customizing the buttons and title
            LayoutInflater inflater = OneWayDateTimeActivity.this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.alert_label_editor, null);
            dialogBuilder.setView(dialogView);

            EditText date = (EditText) dialogView.findViewById(R.id.date);
            EditText time = (EditText) dialogView.findViewById(R.id.time);
            Button cancel = (Button) dialogView.findViewById(R.id.cancel);
            Button ok = (Button) dialogView.findViewById(R.id.ok);


            ok.setOnClickListener(v1 ->
                    {
                        final Dialog dialog = new Dialog(OneWayDateTimeActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.popupxml);


                        TextView txt_inHours = dialog.findViewById(R.id.txt_inHours);
                        TextView txt_inDays = dialog.findViewById(R.id.txt_inDays);

                        txt_inDays.setOnClickListener(v2 -> {

                            txt_inDays.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                            Common.indayorhour="1";
                        });
                        txt_inHours.setOnClickListener(v2 -> {

                            txt_inHours.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                            Common.indayorhour="2";

                        });

                        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
                        btn_cancel.setOnClickListener(v14 -> dialog.dismiss());
                        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
                        btn_ok.setOnClickListener(v15 -> {


                            startActivity(new Intent(OneWayDateTimeActivity.this, EstimateCostActivity.class));
                            dialog.dismiss();
                        });

                        dialog.show();

                    }
            );

            date.setOnClickListener(v12 -> {
                // calender class's instance and get current date , month and year from calender
                @SuppressLint({"NewApi", "LocalSuppress"}) final Calendar c = Calendar.getInstance();
                @SuppressLint({"NewApi", "LocalSuppress"}) int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(OneWayDateTimeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                                Common.date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            });

            time.setOnClickListener(v13 -> {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(OneWayDateTimeActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {


                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                Toast.makeText(OneWayDateTimeActivity.this, "" + hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
                                time.setText(hourOfDay + ":" + minute);

                                Common.time = hourOfDay + ":" + minute;

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

            });


            //   eText.setInputType(InputType.TYPE_NULL);
           /* time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar cldr = Calendar.getInstance();
                    int hour = cldr.get(Calendar.HOUR_OF_DAY);
                    int minutes = cldr.get(Calendar.MINUTE);
                    // time picker dialog
                    picker = new TimePickerDialog(OneWayDateTimeActivity.this,
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                    time.setText(sHour + ":" + sMinute);
                                }
                            }, hour, minutes, true);
                    picker.show();
                }
            });*/


            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();

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




           /* line = mMap.addPolyline(new PolylineOptions().geodesic(true)
                    .add(Current_loc, destination).width(10).color(Color.BLACK));*/
                    String url = getUrl(destination, Current_loc, "driving");

                    Log.e("url", " 11111      " + url);

                    FetchURL fetchURL = new FetchURL(OneWayDateTimeActivity.this, mMap);

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
