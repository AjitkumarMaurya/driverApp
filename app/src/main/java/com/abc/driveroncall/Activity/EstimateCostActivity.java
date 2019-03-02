package com.abc.driveroncall.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abc.driveroncall.Adepter.NumAddAdepter;
import com.abc.driveroncall.R;
import com.abc.driveroncall.common.Common;
import com.abc.driveroncall.response.CreateTripResponse;
import com.abc.driveroncall.response.GetTripRateResponse;
import com.abc.driveroncall.retrofit.ApiClient;
import com.abc.driveroncall.retrofit.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EstimateCostActivity extends AppCompatActivity {

    EditText enterHour;
    Button calculate;
    TextView estimateAmount, place1, place2, estimateText;
    String hrBasedCost;
    ProgressDialog dialog;
    Button btn_book;
    NumAddAdepter addAdepter;
    ArrayList<String> list;
    RecyclerView recyclerView;
    String date = "";
    String time = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimate_cost);
        enterHour = findViewById(R.id.enterHour);
        calculate = findViewById(R.id.calculate);
        estimateAmount = findViewById(R.id.estimateAmount);
        place1 = findViewById(R.id.place1);
        place2 = findViewById(R.id.place2);
        estimateText = findViewById(R.id.estimateText);
        btn_book = findViewById(R.id.btn_book);
        recyclerView = findViewById(R.id.numRecyclerView);
        dialog = new ProgressDialog(this);

        place1.setText(Common.placeName1);
        place2.setText(Common.placeName2);
        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        time = intent.getStringExtra("time");

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setMessage("Data is Updating...");
                dialog.show();
                getCostingCall(enterHour.getText().toString());


            }
        });

        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                driverCall(date, time);

            }
        });


        //numAdepter();
    }

   /* private void numAdepter() {

        addAdepter = new NumAddAdepter(EstimateCostActivity.this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(EstimateCostActivity.this, LinearLayout.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(addAdepter);
    }
*/

    public void getCostingCall(String editText) {
        final ApiInterface api = ApiClient.getApiService();
        Call<GetTripRateResponse> call = api.getTripRate();
        call.enqueue(new Callback<GetTripRateResponse>() {
            @Override
            public void onResponse(Call<GetTripRateResponse> call, Response<GetTripRateResponse> response) {
                dialog.dismiss();

                hrBasedCost = response.body().getTripHourlyRate();

                Integer hrBasedCostInt = Integer.valueOf(hrBasedCost);

                Integer editTextInt = Integer.valueOf(editText);

                Integer total = hrBasedCostInt * editTextInt;

                estimateAmount.setText(total.toString());
                estimateAmount.setVisibility(View.VISIBLE);
                estimateText.setVisibility(View.VISIBLE);


            }

            @Override
            public void onFailure(Call<GetTripRateResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(EstimateCostActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


            }
        });

    }


    public void driverCall(String date, String time) {
        Log.e("MYDATA", "date === " + date);
        Log.e("MYDATA", " time === " + time);
        Log.e("MYDATA", " Common.myLatLong.latitude === " + Common.myLatLong.latitude);
        Log.e("MYDATA", " Common.myLatLong.longitude === " + Common.myLatLong.longitude);
        Log.e("MYDATA", " Common.placeName1 === " + Common.placeName1);
        Log.e("MYDATA", " Common.myLatLong2.longitude === " + Common.myLatLong2.longitude);
        Log.e("MYDATA", " Common.myLatLong2.longitude === " + Common.myLatLong2.longitude);
        Log.e("MYDATA", " Common.placeName2 === " + Common.placeName2);
        Log.e("MYDATA", " enterHour.getText().toString() === " + enterHour.getText().toString());
        final ApiInterface api = ApiClient.getApiService();
        Call<CreateTripResponse> call = api.AddTrip("1", "15-1-2019", "7:30", "1", Common.myLatLong.latitude, Common.myLatLong.longitude, Common.placeName1, "Bhopal", Common.myLatLong2.latitude, Common.myLatLong2.longitude, Common.placeName2, "1Bhopal", enterHour.getText().toString());
        call.enqueue(new Callback<CreateTripResponse>() {
            @Override
            public void onResponse(Call<CreateTripResponse> call, Response<CreateTripResponse> response) {
                dialog.dismiss();

                if (response.body().getCreateTrip()) {
                    Toast.makeText(EstimateCostActivity.this, "Create Trip SucessFull", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EstimateCostActivity.this, COngratulationScreen.class));
                } else {
                    Toast.makeText(EstimateCostActivity.this, "Not Sucessfull", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<CreateTripResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(EstimateCostActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


            }
        });

    }


}
