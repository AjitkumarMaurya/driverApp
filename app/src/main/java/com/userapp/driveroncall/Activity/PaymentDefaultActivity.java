package com.userapp.driveroncall.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (preferenceManager.getPayType().equalsIgnoreCase("1")){

            ch_cash.setChecked(true);
            ch_online.setChecked(false);


        }else if(preferenceManager.getPayType().equalsIgnoreCase("2")){

            ch_online.setChecked(true);
            ch_cash.setChecked(false);

        }

        ch_cash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){


                    preferenceManager.setPayType("1");

                    ch_online.setChecked(false);

                }


            }
        });

        ch_online.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {



                if (isChecked){

                    preferenceManager.setPayType("2");
                    ch_cash.setChecked(false);



                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
