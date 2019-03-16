package com.docuser.driveroncall.Activity;

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

import com.docuser.driveroncall.Activity.Forget_Password.ForgetMobileNoActivity;
import com.docuser.driveroncall.MainActivity;
import com.docuser.driveroncall.R;
import com.docuser.driveroncall.response.LoginResponse;
import com.docuser.driveroncall.retrofit.ApiClient;
import com.docuser.driveroncall.retrofit.ApiInterface;
import com.docuser.driveroncall.utility.PreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText edtUser, edtPassword;
    String user, password;
    Button btnLogin;
    ProgressDialog dialog;
    TextView registration, tvForgetPass, error;
    String shFname = "", shLname = "", shGmail = "", mobileNoPass = "";
    int userID = 0;

    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferenceManager = new PreferenceManager(this);
        edtUser = findViewById(R.id.edtname);
        edtPassword = findViewById(R.id.edtpassword);
        btnLogin = findViewById(R.id.btnlogin);
        registration = findViewById(R.id.tvRegistration_User);
        tvForgetPass = findViewById(R.id.tvForgetPass);
        error = findViewById(R.id.error);

        tvForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgetMobileNoActivity.class));
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, OtpActivity.class));

            }
        });

        dialog = new ProgressDialog(LoginActivity.this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = edtUser.getText().toString();
                password = edtPassword.getText().toString();

                //-------
                if (TextUtils.isEmpty(user)) {

                    edtUser.setError("Enter First Name");
                }
                if (TextUtils.isEmpty(password)) {

                    edtPassword.setError("Enter First Name");
                }
                if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)) {
                    login();
                }

                //  Toast.makeText(LoginActivity.this, edtUser.getText().toString() + edtPassword.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void login() {
        dialog.setMessage("Data is Updating...");
        dialog.show();


        final ApiInterface api = ApiClient.getApiService();
        Call<LoginResponse> call = api.GetLogin(edtUser.getText().toString(), edtPassword.getText().toString());
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                dialog.dismiss();

                if (response != null && response.isSuccessful()) {

                    if (!response.body().getLogin()) {
                        error.setText(response.body().getErrors());
                        //   Toast.makeText(LoginActivity.this, response.body().getErrors(), Toast.LENGTH_SHORT).show();

                    } else {

                        shFname = ""+response.body().getUserResponse().getUsersFirstName();
                        shLname = ""+response.body().getUserResponse().getUsersLastName();
                        shGmail = ""+response.body().getUserResponse().getUsersEmail();
                        mobileNoPass = ""+response.body().getUserResponse().getUsersContactNo();
                        userID = response.body().getUserResponse().getUsersId();


                        SharedPreferences.Editor editor = getSharedPreferences("MyPref", MODE_PRIVATE).edit();
                        editor.putString("firstName", shFname);
                        editor.putString("lastName", shLname);
                        editor.putString("email", shGmail);
                        editor.putString("mobileNumber", mobileNoPass);
                        editor.putInt("userID", userID);
                        editor.apply();

                        preferenceManager.setRegisteredUserId(String.valueOf(userID));

                        preferenceManager.setKeyValueString("firstName",shFname);
                        preferenceManager.setKeyValueString("lastName",shLname);

                        preferenceManager.setKeyValueString("email",shGmail);

                        try {
                            preferenceManager.setKeyValueString("uAddress", "" + response.body().getUserResponse().getUsersAddress().toString());
                            preferenceManager.setKeyValueString("uBdate", "" + response.body().getUserResponse().getUsersBirthDate().toString());
                            preferenceManager.setKeyValueString("uPinCode", "" + response.body().getUserResponse().getUsersPincode().toString());
                        }catch (Exception e){
                            e.getMessage();
                        }
                        preferenceManager.setKeyValueString("mobileNumber",mobileNoPass);

                        preferenceManager.setLoginSession();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        //       startActivity(new Intent(LoginActivity.this, MainActivity.class));


                        edtUser.setText("");
                        edtPassword.setText("");
                    }

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                dialog.dismiss();

            }
        });
    }
}
