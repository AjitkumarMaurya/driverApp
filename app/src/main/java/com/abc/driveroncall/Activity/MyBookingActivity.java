package com.abc.driveroncall.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.abc.driveroncall.Adepter.MyBookingAdapter;
import com.abc.driveroncall.R;
import com.abc.driveroncall.response.GetTripRateResponse;
import com.abc.driveroncall.response.MyBookResponce;
import com.abc.driveroncall.retrofit.ApiClient;
import com.abc.driveroncall.retrofit.ApiInterface;
import com.abc.driveroncall.utility.PreferenceManager;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBookingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    TextView textView;

    PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking);

        preferenceManager = new PreferenceManager(this);
        recyclerView = findViewById(R.id.recy_my_book);
        progressBar = findViewById(R.id.ps_br);
        textView = findViewById(R.id.tv_no_data);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initCode();


    }

    private void initCode() {

        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        textView.setVisibility(View.GONE);

        final ApiInterface api = ApiClient.getApiService();
        Call<MyBookResponce> call = api.getMyBooking(preferenceManager.getRegisteredUserId());
        call.enqueue(new Callback<MyBookResponce>() {
            @Override
            public void onResponse(Call<MyBookResponce> call, Response<MyBookResponce> response) {


                try {
                    if (response.body().getMyBookings().size()>0){
                        recyclerView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        textView.setVisibility(View.GONE);

                        recyclerView.setLayoutManager(new LinearLayoutManager(MyBookingActivity.this, LinearLayout.VERTICAL, false));
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());

                        MyBookingAdapter myBookingAdapter =new MyBookingAdapter(MyBookingActivity.this,response.body());
                        recyclerView.setAdapter(myBookingAdapter);



                    }else {
                        recyclerView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    recyclerView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);


                }


            }

            @Override
            public void onFailure(Call<MyBookResponce> call, Throwable t) {

                Toast.makeText(MyBookingActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


                recyclerView.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);


            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==android.R.id.home){

            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
