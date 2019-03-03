package com.abc.driveroncall.Activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.abc.driveroncall.Adepter.MyTripsAdepter;
import com.abc.driveroncall.R;
import com.abc.driveroncall.model.UserMyTripModel;
import com.abc.driveroncall.response.MyTripsResponse;
import com.abc.driveroncall.retrofit.ApiClient;
import com.abc.driveroncall.retrofit.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTripsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<UserMyTripModel> list = new ArrayList<>();
    MyTripsAdepter myTripsAdepter;
    int restoredText;
    String myid;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trips);
        recyclerView = findViewById(R.id.tripTypeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dialog = new ProgressDialog(MyTripsActivity.this);
        dialog.setMessage("Please Wait...");
        dialog.show();
        SharedPreferences prefs = getSharedPreferences("MyPref", MODE_PRIVATE);
        restoredText = prefs.getInt("userID", 0);

        myid = Integer.toString(restoredText);


        final ApiInterface api = ApiClient.getApiService();

        Call<MyTripsResponse> call = api.getMyTrips(myid);


        call.enqueue(new Callback<MyTripsResponse>() {
            @Override
            public void onResponse(Call<MyTripsResponse> call, Response<MyTripsResponse> response) {
                dialog.dismiss();

                if (response != null && response.isSuccessful()) {


                    list = response.body().getUserMyTripModelList();


                    myTripsAdepter = new MyTripsAdepter(MyTripsActivity.this, list);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MyTripsActivity.this, LinearLayout.VERTICAL, false));
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(myTripsAdepter);
                }
            }

            @Override
            public void onFailure(Call<MyTripsResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(MyTripsActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
