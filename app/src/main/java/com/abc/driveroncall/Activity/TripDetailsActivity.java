package com.abc.driveroncall.Activity;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.abc.driveroncall.R;

public class TripDetailsActivity extends AppCompatActivity {
    TextView tvGreenLocationName, tvRedLocationNamen, tvTripType, tvDate, tvKm, tvAmount, tvTime;
    RatingBar tvReting;

   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        tvGreenLocationName = findViewById(R.id.tvGreenText);
        tvRedLocationNamen = findViewById(R.id.tvradeText);
        tvTripType = findViewById(R.id.tvTripType);
        tvDate = findViewById(R.id.tvTrip_Date);
        tvKm = findViewById(R.id.tvDistonsh_Km);
        tvAmount = findViewById(R.id.tvPayment);
        tvTime = findViewById(R.id.tvTime);
        tvReting = findViewById(R.id.retingBar);

    }
}
