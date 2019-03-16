package com.docuser.driveroncall.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.docuser.driveroncall.R;
import com.docuser.driveroncall.response.ProfileUsResponse;
import com.docuser.driveroncall.retrofit.ApiClient;
import com.docuser.driveroncall.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutActivity extends AppCompatActivity {
    TextView aboutAs;
    ProgressBar ps_br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        aboutAs = findViewById(R.id.tvAboutAs);
        ps_br = findViewById(R.id.ps_br);

        ps_br.setVisibility(View.VISIBLE);
        aboutAs.setVisibility(View.GONE);

        initCode();


    }

    private void initCode() {

        final ApiInterface api = ApiClient.getApiService();
        Call<ProfileUsResponse> call = api.getAboutAs();

        call.enqueue(new Callback<ProfileUsResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ProfileUsResponse> call, Response<ProfileUsResponse> response) {


                aboutAs.setText(response.body().getUserAboutUs()+"");
                ps_br.setVisibility(View.GONE);
                aboutAs.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<ProfileUsResponse> call, Throwable t) {

                initCode();

                Toast.makeText(AboutActivity.this, "Server error   "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
