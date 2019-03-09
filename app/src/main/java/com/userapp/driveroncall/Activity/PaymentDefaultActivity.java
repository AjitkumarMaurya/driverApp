package com.userapp.driveroncall.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.abc.driveroncall.R;
import com.userapp.driveroncall.utility.PreferenceManager;

public class PaymentDefaultActivity extends AppCompatActivity {

    CheckBox ch_online,ch_cash;

    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_default);

        ch_cash = findViewById(R.id.ch_cash);
        ch_online = findViewById(R.id.ch_online);

        preferenceManager = new PreferenceManager(this);

        if (preferenceManager.getPayType().equalsIgnoreCase("1")){

            ch_cash.setChecked(true);
            ch_online.setChecked(true);


        }else if(preferenceManager.getPayType().equalsIgnoreCase("1")){

            ch_online.setChecked(true);

        }

        ch_cash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){


                    preferenceManager.setPayType("1");


                }


            }
        });

        ch_online.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {



                if (isChecked){

                    preferenceManager.setPayType("2");



                }

            }
        });
    }
}
