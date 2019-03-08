package com.userapp.driveroncall.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.abc.driveroncall.R;
import com.userapp.driveroncall.response.UserPrivacyPolicyResponse;
import com.userapp.driveroncall.retrofit.ApiClient;
import com.userapp.driveroncall.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPrivacyPolicyActivity extends AppCompatActivity {
    TextView UserProfile;
    ProgressBar ps_br;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_privacy_policy);


        UserProfile = findViewById(R.id.userProfile);
        ps_br = findViewById(R.id.ps_br);

        ps_br.setVisibility(View.VISIBLE);
        UserProfile.setVisibility(View.GONE);

        initCode();




    }

    private void initCode() {

        final ApiInterface api = ApiClient.getApiService();


        Call<UserPrivacyPolicyResponse> call=api.getUserPrivacyPolicy();
        call.enqueue(new Callback<UserPrivacyPolicyResponse>() {
            @Override
            public void onResponse(Call<UserPrivacyPolicyResponse> call, Response<UserPrivacyPolicyResponse> response) {

                if (response != null && response.isSuccessful()) {

                    ps_br.setVisibility(View.GONE);
                    UserProfile.setVisibility(View.VISIBLE);

                    UserProfile.setText(response.body().getUserPrivacyPolicy());

                }

            }

            @Override
            public void onFailure(Call<UserPrivacyPolicyResponse> call, Throwable t) {

                initCode();
                Toast.makeText(UserPrivacyPolicyActivity.this, "Server error  "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
