package com.userapp.driveroncall.Activity.Forget_Password;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abc.driveroncall.R;
import com.userapp.driveroncall.response.ForgotOtpPassResponse;
import com.userapp.driveroncall.retrofit.ApiClient;
import com.userapp.driveroncall.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetMobileNoActivity extends AppCompatActivity {
    EditText edtMobileForPass;
    Button otpSendForPass;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_mobile_no);

        edtMobileForPass = findViewById(R.id.edt_MobileForPassOtp);
        otpSendForPass = findViewById(R.id.btn_SubmitForPassOtp);
        dialog = new ProgressDialog(this);

        otpSendForPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobile = edtMobileForPass.getText().toString();

                if (TextUtils.isEmpty(mobile)) {
                    edtMobileForPass.setError("Entter Mobile no");

                } else {
                    forPass();
                }
            }
        });


    }

    private void forPass() {

        SharedPreferences.Editor editor = getSharedPreferences("ForPassMobileNo", MODE_PRIVATE).edit();
        editor.putString("MobileNoForPass", edtMobileForPass.getText().toString());

        editor.apply();


        dialog.setMessage("Data is Updating...");
        dialog.show();

        final ApiInterface api = ApiClient.getApiService();
        Call<ForgotOtpPassResponse> call = api.getForPassOtp(edtMobileForPass.getText().toString());
        call.enqueue(new Callback<ForgotOtpPassResponse>() {
            @Override
            public void onResponse(Call<ForgotOtpPassResponse> call, Response<ForgotOtpPassResponse> response) {
                if (response != null && response.isSuccessful()) {
                    dialog.dismiss();
                    if (!response.body().getOtpRequest()) {
                        Toast.makeText(ForgetMobileNoActivity.this, response.body().getErrors(), Toast.LENGTH_SHORT).show();
                    } else {


                      /*  Intent intent = new Intent(OtpActivity.this, VarificationOtpActivity.class);
                         startActivity(intent);
*/
                        Intent intent = new Intent(ForgetMobileNoActivity.this, ForgotOtpActivity.class);
                        //           intent.putExtra("USER_NAME", edtMobileForPass.getText().toString());

                        //   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                        //  startActivity(new Intent(OtpActivity.this, VarificationOtpActivity.class));


                    }
                }
            }

            @Override
            public void onFailure(Call<ForgotOtpPassResponse> call, Throwable t) {
                dialog.dismiss();
            }
        });

    }
}
