package com.abc.driveroncall.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.abc.driveroncall.MainActivity;
import com.abc.driveroncall.R;
import com.abc.driveroncall.utility.PreferenceManager;

import java.util.Timer;
import java.util.TimerTask;

public class FleshActivity extends AppCompatActivity {

    long Delay = 1000;

    PreferenceManager preferenceManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove the Title Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        // Get the view from splash_screen.xml
        setContentView(R.layout.activity_falesh);
        preferenceManager = new PreferenceManager(this);

        // Create a Timer
        Timer RunSplash = new Timer();

        // Task to do when the timer ends
        TimerTask ShowSplash = new TimerTask() {
            @Override
            public void run() {


                if (preferenceManager.getLoginSession()) {
                    Intent myIntent = new Intent(FleshActivity.this, MainActivity.class);
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
