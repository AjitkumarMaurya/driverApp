package com.docuser.driveroncall.Activity;

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

import com.docuser.driveroncall.Adepter.NumAddAdepter;
import com.docuser.driveroncall.R;
import com.docuser.driveroncall.MainActivity;
import com.docuser.driveroncall.common.Common;
import com.docuser.driveroncall.response.CreateTripResponse;
import com.docuser.driveroncall.response.GetTripRateResponse;
import com.docuser.driveroncall.retrofit.ApiClient;
import com.docuser.driveroncall.retrofit.ApiInterface;
import com.docuser.driveroncall.utility.PreferenceManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EstimateCostActivity extends AppCompatActivity {

    TextView tv_type_select, tv_date_time;
    TextView estimateAmount, estimateNote,place1, place2, estimateText;
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
    String totalPrice = "0";

    PreferenceManager preferenceManager;

    String payType = "0";

    LinearLayout lin_cash, lin_online;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimate_cost);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Booking");
        tv_type_select = findViewById(R.id.tv_type_select);
        estimateAmount = findViewById(R.id.estimateAmount);
        estimateNote  = findViewById(R.id.estimateNote);
        tv_date_time = findViewById(R.id.tv_date_time);
        place1 = findViewById(R.id.place1);
        place2 = findViewById(R.id.place2);
        estimateText = findViewById(R.id.estimateText);
        btn_book = findViewById(R.id.btn_book);
        recyclerView = findViewById(R.id.numRecyclerView);

        lin_cash = findViewById(R.id.lin_cash);
        lin_online = findViewById(R.id.lin_online);

        pdialog = new ProgressDialog(this);
        pdialog.setMessage("Please Wait...");

        preferenceManager = new PreferenceManager(this);

        place1.setText(Common.placeName1);
        place2.setText(Common.placeName2);
        Intent intent = getIntent();

        date = Common.date;

        time = Common.time;


        tv_date_time.setText(":" + date + "     @:" + time);


        Log.e("@@", date + " " + time);

        list = new ArrayList<>();


        if (Common.oneOrTwoWay.equalsIgnoreCase("2")) {

            if (Common.indayorhour.equalsIgnoreCase("1")) {

                tv_type_select.setText("Days");

                list.add("1");
                list.add("2");
                list.add("3");
                list.add("4");
                list.add("5");
                list.add("6");
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

                        hourday = String.valueOf(da * 7);
                        getCostingCall(hourday);
                        addAdepter.notifyDataSetChanged();

                    }
                });


            } else if (Common.indayorhour.equalsIgnoreCase("2")) {

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

                    if (totalPrice.equalsIgnoreCase("0")) {
                        Toast.makeText(EstimateCostActivity.this, "Calculate trip fair..", Toast.LENGTH_SHORT).show();
                    } else {

                        if (payType.equalsIgnoreCase("0")) {

                            Toast.makeText(this, "Select Payment type.", Toast.LENGTH_SHORT).show();

                        } else {

                            AlertDialog.Builder builder1 = new AlertDialog.Builder(EstimateCostActivity.this);
                            builder1.setMessage("Sure to book trip ?");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();


                                            pdialog.show();
                                            driverCall(date, time, hourday, type, totalPrice,payType);


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

                    }
                }
        );


        lin_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lin_online.setBackgroundResource(R.drawable.boder_blue);

                lin_cash.setBackgroundResource(R.drawable.boder_white);

                payType = "2";
            }
        });

        lin_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lin_cash.setBackgroundResource(R.drawable.boder_blue);

                lin_online.setBackgroundResource(R.drawable.boder_white);
                payType = "1";

            }
        });
        //numAdepter();


        if (preferenceManager.getPayType().equalsIgnoreCase("1")){

            lin_cash.setBackgroundResource(R.drawable.boder_blue);

            lin_online.setBackgroundResource(R.drawable.boder_white);
            payType = "1";



        }else if(preferenceManager.getPayType().equalsIgnoreCase("2")){

            lin_online.setBackgroundResource(R.drawable.boder_blue);

            lin_cash.setBackgroundResource(R.drawable.boder_white);

            payType = "2";

        }


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
                totalPrice = total + "";
                estimateAmount.setText(total + " Rs.");
                estimateAmount.setVisibility(View.VISIBLE);
                estimateText.setVisibility(View.VISIBLE);
                estimateNote .setVisibility(View.VISIBLE);

                float gst = Float.valueOf(response.body().getTripRate().getTripGstRate());
                float cgst = Float.valueOf(response.body().getTripRate().getTripCgstRate());
                int tax = (int) (gst+cgst);
                estimateNote.setText("Tax apply "+tax+"%"+ " ");
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


    public void driverCall(String date, String time, String strhour, String type, String price,String payType) {
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
        Call<CreateTripResponse> call = api.AddTrip(preferenceManager.getRegisteredUserId(), date, time, type, Common.myLatLong.latitude, Common.myLatLong.longitude, Common.placeName1, Common.placeName1City, Common.myLatLong2.latitude, Common.myLatLong2.longitude, Common.placeName2, Common.placeName2City, strhour, price,payType);
        call.enqueue(new Callback<CreateTripResponse>() {
            @Override
            public void onResponse(Call<CreateTripResponse> call, Response<CreateTripResponse> response) {
                pdialog.dismiss();
                Toast.makeText(EstimateCostActivity.this, "Create Trip SucessFull", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EstimateCostActivity.this, MainActivity.class);

                intent.putExtra("from","2");

                startActivity(intent);

                finish();
                /*if (response.body().getCreateTrip()) {

                } else {
                    Toast.makeText(EstimateCostActivity.this, "Not Sucessfull", Toast.LENGTH_SHORT).show();
                }*/
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
