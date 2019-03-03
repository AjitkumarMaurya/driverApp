package com.abc.driveroncall.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abc.driveroncall.Adepter.NumAddAdepter;
import com.abc.driveroncall.R;
import com.abc.driveroncall.common.Common;
import com.abc.driveroncall.response.CreateTripResponse;
import com.abc.driveroncall.response.GetTripRateResponse;
import com.abc.driveroncall.retrofit.ApiClient;
import com.abc.driveroncall.retrofit.ApiInterface;
import com.abc.driveroncall.utility.PreferenceManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EstimateCostActivity extends AppCompatActivity {

    TextView tv_type_select, tv_date_time;
    TextView estimateAmount, place1, place2, estimateText;
    String hrBasedCost;
    ProgressDialog pdialog;
    Button btn_book;
    NumAddAdepter addAdepter;
    ArrayList<String> list;
    RecyclerView recyclerView;
    String date = "";
    String time = "";
    String type = "";
    String hourday = "";

    PreferenceManager preferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimate_cost);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Booking");
        tv_type_select = findViewById(R.id.tv_type_select);
        estimateAmount = findViewById(R.id.estimateAmount);
        tv_date_time = findViewById(R.id.tv_date_time);
        place1 = findViewById(R.id.place1);
        place2 = findViewById(R.id.place2);
        estimateText = findViewById(R.id.estimateText);
        btn_book = findViewById(R.id.btn_book);
        recyclerView = findViewById(R.id.numRecyclerView);
        pdialog = new ProgressDialog(this);
        pdialog.setMessage("Please Wait...");

        preferenceManager = new PreferenceManager(this);

        place1.setText(Common.placeName1);
        place2.setText(Common.placeName2);
        Intent intent = getIntent();

        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd-MMM-yyyy h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date1 = null;
        String str = null;

        try {
            date1 = inputFormat.parse(time);
            Date date = inputFormat.parse(outputPattern);
            String outputDateStr = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        str = date;
        date = Common.date;
        time = Common.time;


        tv_date_time.setText("" + date1 + " " + str);

        list = new ArrayList<>();


        if (Common.oneOrTwoWay.equalsIgnoreCase("2")) {

            if (Common.indayorhour.equalsIgnoreCase("1")) {

                tv_type_select.setText("Days");

                list.add("1");
                list.add("1.5");
                list.add("2");
                list.add("2.5");
                list.add("3");
                list.add("3.5");
                list.add("4");
                list.add("4.5");
                list.add("5");
                list.add("5.5");
                list.add("6");
                list.add("6.5");
                list.add("7");

                addAdepter = new NumAddAdepter(EstimateCostActivity.this, list);
                recyclerView.setLayoutManager(new LinearLayoutManager(EstimateCostActivity.this, LinearLayout.HORIZONTAL, false));
                recyclerView.setHasFixedSize(true);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(addAdepter);

                addAdepter.SetupInterface(new NumAddAdepter.NumClick() {
                    @Override
                    public void click(String data, int pos) {


                        float da = Float.valueOf(data);

                        hourday = String.valueOf(da * 24);
                        getCostingCall(hourday);
                        addAdepter.notifyDataSetChanged();

                    }
                });


            } else if (Common.indayorhour.equalsIgnoreCase("2")) {

                list.add("1");
                list.add("2");
                list.add("3");
                list.add("4");
                list.add("5");
                list.add("6");
                list.add("7");
                list.add("8");
                list.add("9");
                list.add("10");
                list.add("11");
                list.add("12");
                list.add("13");
                list.add("14");
                list.add("15");
                list.add("16");
                list.add("17");
                list.add("18");
                list.add("19");
                list.add("20");
                list.add("21");
                list.add("22");
                list.add("23");
                list.add("24");

                tv_type_select.setText("Hours");

                addAdepter = new NumAddAdepter(EstimateCostActivity.this, list);
                recyclerView.setLayoutManager(new LinearLayoutManager(EstimateCostActivity.this, LinearLayout.HORIZONTAL, false));
                recyclerView.setHasFixedSize(true);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(addAdepter);

                addAdepter.SetupInterface(new NumAddAdepter.NumClick() {
                    @Override
                    public void click(String data, int pos) {
                        getCostingCall(data);
                        hourday = data;
                        addAdepter.notifyDataSetChanged();

                    }
                });


            }

        } else {

            list.add("1");
            list.add("2");
            list.add("3");
            list.add("4");
            list.add("5");
            list.add("6");
            list.add("7");
            list.add("8");
            list.add("9");
            list.add("10");
            list.add("11");
            list.add("12");
            list.add("13");
            list.add("14");
            list.add("15");
            list.add("16");
            list.add("17");
            list.add("18");
            list.add("19");
            list.add("20");
            list.add("21");
            list.add("22");
            list.add("23");
            list.add("24");


            tv_type_select.setText("Hours");

            addAdepter = new NumAddAdepter(EstimateCostActivity.this, list);
            recyclerView.setLayoutManager(new LinearLayoutManager(EstimateCostActivity.this, LinearLayout.HORIZONTAL, false));
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(addAdepter);

            addAdepter.SetupInterface(new NumAddAdepter.NumClick() {
                @Override
                public void click(String data, int pos) {
                    getCostingCall(data);
                    hourday = data;
                    addAdepter.notifyDataSetChanged();

                }
            });


        }

        type = Common.oneOrTwoWay;





       /* calculate.setOnClickListener(v -> {
            dialog.setMessage("Data is Updating...");
            dialog.show();



        });
*/
        btn_book.setOnClickListener(v -> {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(EstimateCostActivity.this);
                    builder1.setMessage("Sure to book trip ?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    pdialog.show();
                                    driverCall(date, time, hourday, type);
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                }
        );


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

        ProgressDialog progressDialog = new ProgressDialog(EstimateCostActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Calculating faire..");
        progressDialog.show();

        final ApiInterface api = ApiClient.getApiService();
        Call<GetTripRateResponse> call = api.getTripRate();
        call.enqueue(new Callback<GetTripRateResponse>() {
            @Override
            public void onResponse(Call<GetTripRateResponse> call, Response<GetTripRateResponse> response) {
                pdialog.dismiss();

                hrBasedCost = response.body().getTripRate().getTripHourlyRate();

                Log.e("@@", hrBasedCost + "");

                float hrBasedCostInt = Float.valueOf(hrBasedCost);

                float editTextInt = Float.valueOf(editText);

                float total = hrBasedCostInt * editTextInt;

                estimateAmount.setText(total + " Rs.");
                estimateAmount.setVisibility(View.VISIBLE);
                estimateText.setVisibility(View.VISIBLE);

                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<GetTripRateResponse> call, Throwable t) {
                pdialog.dismiss();
                Toast.makeText(EstimateCostActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();

            }
        });

    }


    public void driverCall(String date, String time, String strhour, String type) {
        Log.e("MYDATA", "date === " + date);
        Log.e("MYDATA", " time === " + time);
        Log.e("MYDATA", " Common.myLatLong.latitude === " + Common.myLatLong.latitude);
        Log.e("MYDATA", " Common.myLatLong.longitude === " + Common.myLatLong.longitude);
        Log.e("MYDATA", " Common.placeName1 === " + Common.placeName1);
        Log.e("MYDATA", " Common.myLatLong2.longitude === " + Common.myLatLong2.longitude);
        Log.e("MYDATA", " Common.myLatLong2.longitude === " + Common.myLatLong2.longitude);
        Log.e("MYDATA", " Common.placeName2 === " + Common.placeName2);
        Log.e("MYDATA", " enterHour.getText().toString() === " + strhour);
        final ApiInterface api = ApiClient.getApiService();
        Call<CreateTripResponse> call = api.AddTrip(preferenceManager.getRegisteredUserId(), date, time, type, Common.myLatLong.latitude, Common.myLatLong.longitude, Common.placeName1, Common.placeName1City, Common.myLatLong2.latitude, Common.myLatLong2.longitude, Common.placeName2, Common.placeName2City, strhour);
        call.enqueue(new Callback<CreateTripResponse>() {
            @Override
            public void onResponse(Call<CreateTripResponse> call, Response<CreateTripResponse> response) {
                pdialog.dismiss();

                if (response.body().getCreateTrip()) {
                    Toast.makeText(EstimateCostActivity.this, "Create Trip SucessFull", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EstimateCostActivity.this, COngratulationScreen.class));
                } else {
                    Toast.makeText(EstimateCostActivity.this, "Not Sucessfull", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateTripResponse> call, Throwable t) {
                pdialog.dismiss();
                Toast.makeText(EstimateCostActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
