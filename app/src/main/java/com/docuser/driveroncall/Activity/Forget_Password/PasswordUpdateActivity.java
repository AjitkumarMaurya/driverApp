package com.docuser.driveroncall.Activity.Forget_Password;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.docuser.driveroncall.Activity.LoginActivity;
import com.docuser.driveroncall.R;
import com.docuser.driveroncall.response.ForgotChengPassResponse;
import com.docuser.driveroncall.retrofit.ApiClient;
import com.docuser.driveroncall.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordUpdateActivity extends AppCompatActivity {
    String password, conpassword;
    EditText edtpassword, edtconfirmPassword;
    Button button;
    Boolean isPasswordSame = false;
    String mobileNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_update);

        edtconfirmPassword = findViewById(R.id.forgotPassConfirm);
        edtpassword = findViewById(R.id.forgotPass);
        button = findViewById(R.id.btnForgotPass);


        SharedPreferences prefs = getSharedPreferences("ForPassMobileNo", MODE_PRIVATE);
        mobileNo = prefs.getString("MobileNoForPass", "No name defined");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                password = edtpassword.getText().toString();
                conpassword = edtconfirmPassword.getText().toString();


                boolean isPasswordSame = false;

                if (TextUtils.isEmpty(password)) {
                    edtpassword.setError("Enter Password");
                }

                if (TextUtils.isEmpty(conpassword)) {
                    edtconfirmPassword.setError("Enter Confirm Password");
                }

                if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(conpassword)) {
                    if (!password.equals(conpassword)) {
                        edtconfirmPassword.setError("Enter same password");

                        isPasswordSame = false;
                    } else {


                        isPasswordSame = true;
                    }
                }

                if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(conpassword) && isPasswordSame) {

                    forPassOtp();
                }

            }
        });

    }

    private void forPassOtp() {

        final ApiInterface api = ApiClient.getApiService();

        Call<ForgotChengPassResponse> call = api.getChengePassword(mobileNo, password);
        call.enqueue(new Callback<ForgotChengPassResponse>() {
            @Override
            public void onResponse(Call<ForgotChengPassResponse> call, Response<ForgotChengPassResponse> response) {
                if (response != null && response.isSuccessful()) {
                    if (!response.body().getUpdateNewPasswod()) {
                        Toast.makeText(PasswordUpdateActivity.this, response.body().getErrors(), Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(PasswordUpdateActivity.this, LoginActivity.class));
                    }
                }
            }

            @Override
            public void onFailure(Call<ForgotChengPassResponse> call, Throwable t) {

            }
        });
    }
}