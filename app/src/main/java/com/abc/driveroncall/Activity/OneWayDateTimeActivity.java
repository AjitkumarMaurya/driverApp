package com.abc.driveroncall.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.abc.driveroncall.R;
import com.abc.driveroncall.common.Common;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


public class OneWayDateTimeActivity extends AppCompatActivity implements OnMapReadyCallback {
    Button btnTimeDate;

    TextView place1, place2;
    private GoogleMap mMap;

    Polyline line;
    LatLng destination, Current_loc;
    float red = 0, blue = 240;
    DatePickerDialog datePickerDialog;
    TimePickerDialog picker;
    String dateString = "";
    String timeSTring = "";
    private int mYear, mMonth, mDay, mHour, mMinute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_way_date_time);
        btnTimeDate = findViewById(R.id.btntimeDate);

        if (Common.oneOrTwoWay.equals("oneway")) {
            getSupportActionBar().setTitle("One Way");
        } else {
            getSupportActionBar().setTitle("Two Way");
        }

        place1 = findViewById(R.id.place1);
        place2 = findViewById(R.id.place2);
        place1.setText(Common.placeName1);
        place2.setText(Common.placeName2);

        btnTimeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(OneWayDateTimeActivity.this);
// ...Irrelevant code for customizing the buttons and title
                LayoutInflater inflater = OneWayDateTimeActivity.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alert_label_editor, null);
                dialogBuilder.setView(dialogView);

                EditText date = (EditText) dialogView.findViewById(R.id.date);
                EditText time = (EditText) dialogView.findViewById(R.id.time);
                Button cancel = (Button) dialogView.findViewById(R.id.cancel);
                Button ok = (Button) dialogView.findViewById(R.id.ok);


                dateString = date.getText().toString();
                timeSTring = time.getText().toString();

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(OneWayDateTimeActivity.this, EstimateCostActivity.class).putExtra("date", dateString).putExtra("time", timeSTring));
                    }
                });

                date.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View v) {
                        // calender class's instance and get current date , month and year from calender
                        final Calendar c = Calendar.getInstance();
                        int mYear = c.get(Calendar.YEAR); // current year
                        int mMonth = c.get(Calendar.MONTH); // current month
                        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                        // date picker dialog
                        datePickerDialog = new DatePickerDialog(OneWayDateTimeActivity.this,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        // set day of month , month and year value in the edit text
                                        date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    }
                });

                time.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View v) {
                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR_OF_DAY);
                        mMinute = c.get(Calendar.MINUTE);

                        // Launch Time Picker Dialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(OneWayDateTimeActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {


                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {

                                        Toast.makeText(OneWayDateTimeActivity.this, ""+hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
                                       // txtTime.setText(hourOfDay + ":" + minute);
                                    }
                                }, mHour, mMinute, false);
                        timePickerDialog.show();

                    }
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

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Double xlat, xlong;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Marker dest, curr;

                mMap = googleMap;

                LatLng latLng = new LatLng(Common.wayLatitude, Common.wayLongitude);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                mMap.clear();
                markerOptions.title("Current Position");
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_call_24dp));
                markerOptions.getPosition();


                LatLng sydney = new LatLng(Common.wayLatitude, Common.wayLongitude);
                dest = mMap.addMarker(new MarkerOptions().position(destination).title("Your Destination").icon(BitmapDescriptorFactory.defaultMarker(red)));
                curr = mMap.addMarker(new MarkerOptions().position(Current_loc).title("Your Location").icon(BitmapDescriptorFactory.defaultMarker(blue)));
                // LatLng sydney = new LatLng(wayLatitude,wayLongitude);
                if (destination != null) {
                    if (dest != null) {
                        dest.remove();
                        if (line != null) {
                            line.remove();
                        }
                    }

                    Log.e("deeee", "" + dest);


                }
                if (Current_loc != null) {
                    if (curr != null) {
                        Log.e("blankkk", "" + curr);
                        curr.remove();
                        if (line != null) {
                            line.remove();
                        }

                    }

                }
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10f));
                if (destination != null && Current_loc != null) {
                    line = googleMap.addPolyline(new PolylineOptions().geodesic(true)
                            .add(Current_loc, destination).width(10).color(Color.GREEN));
                }
            }
        }, 500);

    }

}
