package com.abc.driveroncall.Activity;

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

import com.abc.driveroncall.MainActivity;
import com.abc.driveroncall.R;
import com.abc.driveroncall.response.PasswordForgetResponse;
import com.abc.driveroncall.retrofit.ApiClient;
import com.abc.driveroncall.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordnavChangeActivity extends AppCompatActivity {
    String old_Password, newPassword, confirm;
    EditText edtNewPassword, edtOldPassword, edtConfirm;
    Button chePassword;
    TextView tvError;
    //  boolean isPasswordSame = false;
    int restoredText;
    String myid;
    ProgressDialog dialog;
    Boolean isPasswordSame = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_forget);
        chePassword = findViewById(R.id.btn_SavePassword);
        dialog = new ProgressDialog(this);

        chePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edtOldPassword = findViewById(R.id.edt_old_Password);
                edtNewPassword = findViewById(R.id.edt_New_Password);
                edtConfirm = findViewById(R.id.edtConfirmPasswordnewProfile);
                tvError = findViewById(R.id.tveroorPass);


                newPassword = edtNewPassword.getText().toString();
                old_Password = edtOldPassword.getText().toString();
                confirm = edtConfirm.getText().toString();

                if (TextUtils.isEmpty(old_Password)) {
                    edtNewPassword.setError("Enter old Password");
                }

                if (TextUtils.isEmpty(newPassword)) {
                    edtOldPassword.setError("Enter New Password");
                }
                if (TextUtils.isEmpty(confirm)) {
                    edtConfirm.setError("Enter Confirm Password");
                }


                if (!TextUtils.isEmpty(newPassword) && !TextUtils.isEmpty(confirm)) {
                    if (!newPassword.equals(confirm)) {
                        edtNewPassword.setError("Enter same password");

                        isPasswordSame = false;
                    } else {


                        isPasswordSame = true;
                    }

                }
                SharedPreferences prefs = getSharedPreferences("MyPref", MODE_PRIVATE);
                restoredText = prefs.getInt("userID", 0);

                myid = Integer.toString(restoredText);

                if (!TextUtils.isEmpty(old_Password) && !TextUtils.isEmpty(newPassword) && !TextUtils.isEmpty(confirm) && isPasswordSame) {


                    forgetPassword();

                }

            }
        });


    }

    private void forgetPassword() {
        dialog.setMessage("Data is Updating...");
        dialog.show();


        final ApiInterface api = ApiClient.getApiService();

        Call<PasswordForgetResponse> call = api.getForegetePassword(myid, old_Password, newPassword);

        call.enqueue(new Callback<PasswordForgetResponse>() {
            @Override
            public void onResponse(Call<PasswordForgetResponse> call, Response<PasswordForgetResponse> response) {

                if (response != null && response.isSuccessful()) {
                    dialog.dismiss();
                    if (!response.body().getUpdatePassword()) {
                        tvError.setText( response.body().getErrors());
                        } else {
                        startActivity(new Intent(PasswordnavChangeActivity.this, MainActivity.class));
                    }
                }


            }

            @Override
            public void onFailure(Call<PasswordForgetResponse> call, Throwable t) {
                dialog.dismiss();
            }
        });


    }
}