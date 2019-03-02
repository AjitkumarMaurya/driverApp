package com.abc.driveroncall.Activity.Forget_Password;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.abc.driveroncall.R;
import com.abc.driveroncall.response.ForgotOtpResponse;
import com.abc.driveroncall.retrofit.ApiClient;
import com.abc.driveroncall.retrofit.ApiInterface;
import com.alahammad.otp_view.OTPListener;
import com.alahammad.otp_view.OtpView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotOtpActivity extends AppCompatActivity {

    OtpView otpviewForPass;

    String mobileNo;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_otp);

        otpviewForPass = findViewById(R.id.otpForPass);
        //     dialog = new ProgressDialog(this);
        SharedPreferences prefs = getSharedPreferences("ForPassMobileNo", MODE_PRIVATE);
        mobileNo = prefs.getString("MobileNoForPass", "No name defined");

    //    Toast.makeText(this, mobileNo, Toast.LENGTH_SHORT).show();
        otpviewForPass.setOnOtpFinished(new OTPListener() {
            @Override
            public void otpFinished(String s) {

                //     dialog.setMessage("Data is Updating...");
                //    dialog.show();

                final ApiInterface api = ApiClient.getApiService();




                Call<ForgotOtpResponse> call = api.getOtpForPassVarify(mobileNo, s);
                call.enqueue(new Callback<ForgotOtpResponse>() {
                                 @Override
                                 public void onResponse(Call<ForgotOtpResponse> call, Response<ForgotOtpResponse> response) {
                                     //               dialog.dismiss();
                                     if (response != null && response.isSuccessful()) {
                                         if (!response.body().getMatchOtp()) {
                                             Toast.makeText(ForgotOtpActivity.this, response.body().getErrors(), Toast.LENGTH_SHORT).show();
                                         } else {

                                             //     startActivity(new Intent(ForgotOtpActivity.this,PasswordUpdateActivity.class));
                                             Intent intent = new Intent(ForgotOtpActivity.this, PasswordUpdateActivity.class);
                                             startActivity(intent);
                                         }
                                     }
                                 }

                                 @Override
                                 public void onFailure(Call<ForgotOtpResponse> call, Throwable t) {
                                     t.getMessage();
                                     //                       dialog.dismiss();
                                 }
                             }
                );


            }
        });

    }
}
