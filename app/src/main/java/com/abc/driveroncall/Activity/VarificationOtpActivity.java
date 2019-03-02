package com.abc.driveroncall.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.abc.driveroncall.R;
import com.abc.driveroncall.response.OtpVerification;
import com.abc.driveroncall.retrofit.ApiClient;
import com.abc.driveroncall.retrofit.ApiInterface;
import com.alahammad.otp_view.OTPListener;
import com.alahammad.otp_view.OtpView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VarificationOtpActivity extends AppCompatActivity {

    OtpView otpview;

    String mobileNo;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varification_otp);
        otpview = findViewById(R.id.otp);
        dialog = new ProgressDialog(this);

        /*

            SharedPreferences preferences =getSharedPreferences("loginPrefs",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            finish();
*/


        otpview.setOnOtpFinished(new OTPListener() {
            @Override
            public void otpFinished(String s) {
                dialog.setMessage("Data is Updating...");
                dialog.show();

                final ApiInterface api = ApiClient.getApiService();

                Call<OtpVerification> call = api.getOtpVarify(mobileNo, s);


                call.enqueue(new Callback<OtpVerification>() {
                    @Override
                    public void onResponse(Call<OtpVerification> call, Response<OtpVerification> response) {
                        dialog.dismiss();
                        if (response != null && response.isSuccessful()) {
                            if (!response.body().getMatchOtp()) {
                                Toast.makeText(VarificationOtpActivity.this, response.body().getErrors(), Toast.LENGTH_SHORT).show();
                            } else {


                                SharedPreferences.Editor editor = getSharedPreferences("dipak", MODE_PRIVATE).edit();
                                editor.putString("dipak", response.body().getMobileNo());

                                editor.apply();


                                Intent intent = new Intent(VarificationOtpActivity.this, RegistrationActivity.class);
                                intent.putExtra("USER_NAME", response.body().getMobileNo());
                                startActivity(intent);


                                //     startActivity(new Intent(VarificationOtpActivity.this, RegistrationActivity.class));
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<OtpVerification> call, Throwable t) {
                        dialog.dismiss();
                    }
                });


            }
        });
    }
}
