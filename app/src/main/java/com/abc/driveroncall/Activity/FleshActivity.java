package com.abc.driveroncall.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.abc.driveroncall.R;

import java.util.Timer;
import java.util.TimerTask;

public class FleshActivity extends AppCompatActivity {

    long Delay = 1000;
    int restoredText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove the Title Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Get the view from splash_screen.xml
        setContentView(R.layout.activity_falesh);


        // Create a Timer
        Timer RunSplash = new Timer();

        // Task to do when the timer ends
        TimerTask ShowSplash = new TimerTask() {
            @Override
            public void run() {
                // Close SplashScreenActivity.class
                finish();

                // Start MainActivity.class
                SharedPreferences prefs = getSharedPreferences("MyPref", MODE_PRIVATE);
                restoredText = prefs.getInt("userID", 0);

                String myString = Integer.toString(restoredText);


                if (restoredText != 0) {
                    Intent myIntent = new Intent(FleshActivity.this, LoginActivity.class);
                    startActivity(myIntent);
                } else {
                    Intent myIntent = new Intent(FleshActivity.this, OtpActivity.class);
                    startActivity(myIntent);
                }


            }
        };

        // Start the timer
        RunSplash.schedule(ShowSplash, Delay);
    }


}
