package com.userapp.driveroncall.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.userapp.driveroncall.Adepter.MyBookingAdapter;
import com.userapp.driveroncall.MainActivity;
import com.abc.driveroncall.R;
import com.userapp.driveroncall.response.CancelResponce;
import com.userapp.driveroncall.response.MyBookResponce;
import com.userapp.driveroncall.retrofit.ApiClient;
import com.userapp.driveroncall.retrofit.ApiInterface;
import com.userapp.driveroncall.utility.PreferenceManager;

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



    }

    @Override
    protected void onResume() {
        super.onResume();
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


                        myBookingAdapter.setUPInterFace(new MyBookingAdapter.BookCancelInterface() {
                            @Override
                            public void click(String data, int pos) {

                                Call<CancelResponce> call2 = api.cancelTrip(data);

                                call2.enqueue(new Callback<CancelResponce>() {
                                    @Override
                                    public void onResponse(Call<CancelResponce> call, Response<CancelResponce> response2) {

                                        initCode();

                                    }

                                    @Override
                                    public void onFailure(Call<CancelResponce> call, Throwable t) {

                                    }
                                });


                            }

                            @Override
                            public void pay(String data, int pos, String amount, String tripId) {


                                Intent intent = new Intent(MyBookingActivity.this,PaymentsActivity.class);

                                intent.putExtra("tripId",tripId);
                                intent.putExtra("tripAmount",amount);

                                startActivity(intent);

                            }
                        });



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
    public void onBackPressed() {
        Intent intent = new Intent(MyBookingActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==android.R.id.home){

            Intent intent = new Intent(MyBookingActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();        }
        return super.onOptionsItemSelected(item);
    }
}
