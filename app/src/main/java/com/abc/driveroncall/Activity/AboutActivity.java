package com.abc.driveroncall.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.abc.driveroncall.R;
import com.abc.driveroncall.response.ProfileUsResponse;
import com.abc.driveroncall.retrofit.ApiClient;
import com.abc.driveroncall.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutActivity extends AppCompatActivity {
    TextView aboutAs;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        aboutAs = findViewById(R.id.tvAboutAs);
        dialog = new ProgressDialog(this);

        dialog.setMessage("Data is Processing...");
        dialog.show();

        final ApiInterface api = ApiClient.getApiService();
        Call<ProfileUsResponse> call = api.getAboutAs();

        call.enqueue(new Callback<ProfileUsResponse>() {
            @Override
            public void onResponse(Call<ProfileUsResponse> call, Response<ProfileUsResponse> response) {

                dialog.dismiss();
                aboutAs.setText(response.body().getUserAboutUs());

            }

            @Override
            public void onFailure(Call<ProfileUsResponse> call, Throwable t) {
                dialog.dismiss();

            }
        });


    }
}
