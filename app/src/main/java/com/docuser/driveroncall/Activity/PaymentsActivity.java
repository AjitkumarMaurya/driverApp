package com.docuser.driveroncall.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.docuser.driveroncall.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.docuser.driveroncall.response.CompleteTripResponce;
import com.docuser.driveroncall.response.PaymentInfoResponce;
import com.docuser.driveroncall.retrofit.ApiClient;
import com.docuser.driveroncall.retrofit.ApiInterface;
import com.docuser.driveroncall.utility.PreferenceManager;

import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentsActivity extends AppCompatActivity implements PaymentResultListener {

    String tripId;
    Float amount;
    PreferenceManager preferenceManager;
    Button btn_pay;

    Toolbar toolbar;
    ImageView iv_back;

    LinearLayout food_lin, acc_lin;
    TextView tv_date_time, tv_trip_no, tv_amount_trip, trip_hours, tv_start_trip_time,
            tv_start_trip_add, tv_end_trip_time, tv_end_trip_add,
            tv_trip_amount_2, tv_trip_amount_gst, tv_trip_amount_cgst,
            tv_trip_amount_accomodation, tv_trip_amount_food,
            tv_pay_type, tv_trip_amount_grand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        Checkout.preload(getApplicationContext());
        preferenceManager = new PreferenceManager(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn_pay = findViewById(R.id.btn_pay);

        iv_back= findViewById(R.id.iv_back);

        food_lin= findViewById(R.id.food_lin);
        acc_lin= findViewById(R.id.acc_lin);
        tv_date_time= findViewById(R.id.tv_date_time);
        tv_trip_no= findViewById(R.id.tv_trip_no);
        tv_amount_trip= findViewById(R.id.tv_amount_trip);
        trip_hours= findViewById(R.id.trip_hours);
        tv_start_trip_time= findViewById(R.id.tv_start_trip_time);
        tv_start_trip_add= findViewById(R.id.tv_start_trip_add);
        tv_end_trip_time= findViewById(R.id.tv_end_trip_time);
        tv_end_trip_add= findViewById(R.id.tv_end_trip_add);
        tv_trip_amount_2= findViewById(R.id.tv_trip_amount_2);
        tv_trip_amount_gst= findViewById(R.id.tv_trip_amount_gst);
        tv_trip_amount_cgst= findViewById(R.id.tv_trip_amount_cgst);
        tv_trip_amount_accomodation= findViewById(R.id.tv_trip_amount_accomodation);
        tv_trip_amount_food= findViewById(R.id.tv_trip_amount_food);
        tv_pay_type= findViewById(R.id.tv_pay_type);
        tv_trip_amount_grand= findViewById(R.id.tv_trip_amount_grand);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            amount = Float.valueOf(Objects.requireNonNull(bundle.getString("tripAmount")));
            tripId = bundle.getString("tripId");


            initCode();



            iv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });


        } else {
            finish();
        }


    }

    private void initCode() {

        final ApiInterface api = ApiClient.getApiService();

        Log.e("@@","tripId--"+tripId);

        Call<PaymentInfoResponce>  call= api.getPayInfo(tripId);

        call.enqueue(new Callback<PaymentInfoResponce>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<PaymentInfoResponce> call, Response<PaymentInfoResponce> response) {

                if (response!=null && response.isSuccessful()){

                    PaymentInfoResponce paymentInfoResponce = response.body();


                    if (paymentInfoResponce != null) {

                        tv_date_time.setText("TRIP DATE:" + paymentInfoResponce.getPaymentDetails().getTripStartDate());

                        tv_trip_no.setText("TRIP NO:" + paymentInfoResponce.getPaymentDetails().getTripUniqueId());

                        try {
                            amount = Float.parseFloat(paymentInfoResponce.getPaymentDetails().getTripAmount());
                            amount = amount * 100;

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        tv_amount_trip.setText("" + paymentInfoResponce.getPaymentDetails().getTripAmount());
                        trip_hours.setText("" + paymentInfoResponce.getPaymentDetails().getTripUsage());
                        tv_start_trip_time.setText("" + paymentInfoResponce.getPaymentDetails().getTripStartTime());
                        tv_start_trip_add.setText("" + paymentInfoResponce.getPaymentDetails().getTripPickupPointName());
                        tv_end_trip_time.setText("" + paymentInfoResponce.getPaymentDetails().getTripEndTime());
                        tv_end_trip_add.setText("" + paymentInfoResponce.getPaymentDetails().getTripDropPointName());
                        tv_trip_amount_2.setText("" + paymentInfoResponce.getPaymentDetails().getTripAmount());
                        tv_trip_amount_gst.setText("" + paymentInfoResponce.getPaymentDetails().getGstCharge());
                        tv_trip_amount_cgst.setText("" + paymentInfoResponce.getPaymentDetails().getCgstCharge());
                        tv_trip_amount_accomodation.setText("" + paymentInfoResponce.getPaymentDetails().getTripAccommodationCharges());
                        tv_trip_amount_food.setText("" + paymentInfoResponce.getPaymentDetails().getTripFoodCharges());


                        if (paymentInfoResponce.getPaymentDetails().getTripAmount().equalsIgnoreCase("1")) {
                            tv_pay_type.setText("Cash");

                        } else {
                            tv_pay_type.setText("Online");

                        }

                        tv_pay_type.setText("" + paymentInfoResponce.getPaymentDetails().getTripAmount());


                        tv_trip_amount_grand.setText("" + paymentInfoResponce.getPaymentDetails().getTripAmount());

                        btn_pay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                startPayment();

                            }
                        });

                    }

                }


            }

            @Override
            public void onFailure(Call<PaymentInfoResponce> call, Throwable t) {

            }
        });




    }

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */


        Log.e("@@","payamount"+amount);


        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Trip payment");
            options.put("description", "Demoing Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", amount);

            JSONObject preFill = new JSONObject();
            preFill.put("email", preferenceManager.getKeyValueString("email"));
            preFill.put("contact", preferenceManager.getKeyValueString("mobileNumber"));

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(PaymentsActivity.this);
        builder1.setMessage("Sure to exit payment ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                        finish();
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

    @Override
    public void onPaymentSuccess(String s) {
        try {


            Toast.makeText(this, "Payment Successful: " + s, Toast.LENGTH_SHORT).show();

            final ApiInterface api = ApiClient.getApiService();

            Call<CompleteTripResponce>  call= api.completTrip(tripId);


            call.enqueue(new Callback<CompleteTripResponce>() {
                @Override
                public void onResponse(Call<CompleteTripResponce> call, Response<CompleteTripResponce> response) {

                    startActivity(new Intent(PaymentsActivity.this, MyBookingActivity.class));
                    finish();

                }

                @Override
                public void onFailure(Call<CompleteTripResponce> call, Throwable t) {

                    Toast.makeText(PaymentsActivity.this, ""+t.getMessage(), Toast.LENGTH_LONG).show();

                }
            });


        } catch (Exception e) {
            Log.e("@@", "Exception in onPaymentSuccess", e);
        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        try {
            Toast.makeText(this, "Payment failed: " + i + " " + s, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("@@", "Exception in onPaymentError", e);
        }
    }
}
