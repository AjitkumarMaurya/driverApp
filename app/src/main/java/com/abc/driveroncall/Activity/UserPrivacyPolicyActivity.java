package com.abc.driveroncall.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.abc.driveroncall.R;
import com.abc.driveroncall.response.UserPrivacyPolicyResponse;
import com.abc.driveroncall.retrofit.ApiClient;
import com.abc.driveroncall.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPrivacyPolicyActivity extends AppCompatActivity {
    TextView UserProfile;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_privacy_policy);


        UserProfile = findViewById(R.id.userProfile);
        dialog = new ProgressDialog(this);

        dialog.setMessage("Data is Processing...");
        dialog.show();

        final ApiInterface api = ApiClient.getApiService();


        Call<UserPrivacyPolicyResponse> call=api.getUserPrivacyPolicy();
        call.enqueue(new Callback<UserPrivacyPolicyResponse>() {
            @Override
            public void onResponse(Call<UserPrivacyPolicyResponse> call, Response<UserPrivacyPolicyResponse> response) {

                dialog.dismiss();
                if (response != null && response.isSuccessful()) {


                    UserProfile.setText(response.body().getUserPrivacyPolicy());

                }

            }

            @Override
            public void onFailure(Call<UserPrivacyPolicyResponse> call, Throwable t) {

            }
        });


    }
}
