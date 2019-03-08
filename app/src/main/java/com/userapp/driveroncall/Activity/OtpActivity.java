package com.userapp.driveroncall.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abc.driveroncall.R;
import com.userapp.driveroncall.response.OtpResponse;
import com.userapp.driveroncall.retrofit.ApiClient;
import com.userapp.driveroncall.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity {
    EditText mobile;

    Button button;
    TextView registereLogin;
    ProgressDialog dialog;
    String mobileNo;
    String mo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        mobile = findViewById(R.id.edt_MobileOtp);
        button = findViewById(R.id.btn_SubmitOtp);
        registereLogin = findViewById(R.id.Tv_registereLogin);


        dialog = new ProgressDialog(this);


        registereLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OtpActivity.this, LoginActivity.class));
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileNo = mobile.getText().toString();

                if (TextUtils.isEmpty(mobileNo)) {
                    mobile.setError("Enter Mobile No");
                } else {
                    otpReqest();
                }

            }
        });


    }

    private void otpReqest() {


        SharedPreferences.Editor editor = getSharedPreferences("MobileNo", MODE_PRIVATE).edit();
        editor.putString("MobileNo", mobile.getText().toString());

        editor.apply();


        dialog.setMessage("Data is Processing...");
        dialog.show();
        final ApiInterface api = ApiClient.getApiService();
        Call<OtpResponse> call = api.getOtp(mobile.getText().toString());
        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                dialog.dismiss();

                if (response != null && response.isSuccessful()) {

                    if (!response.body().getRequestOtp()) {
                        Toast.makeText(OtpActivity.this, response.body().getErrors(), Toast.LENGTH_SHORT).show();
                    } else {


                      /*  Intent intent = new Intent(OtpActivity.this, VarificationOtpActivity.class);
                         startActivity(intent);
*/
                        Intent intent = new Intent(OtpActivity.this, VarificationOtpActivity.class);
                        intent.putExtra("USER_NAME", mobile.getText().toString());

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                        //  startActivity(new Intent(OtpActivity.this, VarificationOtpActivity.class));


                    }
                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                Toast.makeText(OtpActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });


    }

}