package com.docuser.driveroncall.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.docuser.driveroncall.R;


public class COngratulationScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulation_screen);
        getSupportActionBar().setTitle("Trip Booked");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(COngratulationScreen.this, MyBookingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}
