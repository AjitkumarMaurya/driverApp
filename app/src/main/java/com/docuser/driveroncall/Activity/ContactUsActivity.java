package com.docuser.driveroncall.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.docuser.driveroncall.R;
import com.docuser.driveroncall.response.ContactUsResponse;
import com.docuser.driveroncall.retrofit.ApiClient;
import com.docuser.driveroncall.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsActivity extends AppCompatActivity {
    TextView contactUsNumber, contactUs;
    ProgressBar ps_br;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        contactUs = findViewById(R.id.tvContactUs);
        contactUsNumber = findViewById(R.id.tvContactUsNumber);
        ps_br = findViewById(R.id.ps_br);


        ps_br.setVisibility(View.VISIBLE);
        contactUs.setVisibility(View.GONE);
        contactUsNumber.setVisibility(View.GONE);

        initCode();


    }

    private void initCode() {

        final ApiInterface api = ApiClient.getApiService();

        Call<ContactUsResponse> call = api.getContactUs();
        call.enqueue(new Callback<ContactUsResponse>() {
            @Override
            public void onResponse(Call<ContactUsResponse> call, Response<ContactUsResponse> response) {
                if (response != null && response.isSuccessful()) {

                    ps_br.setVisibility(View.GONE);
                    contactUs.setVisibility(View.VISIBLE);
                    contactUsNumber.setVisibility(View.VISIBLE);

                    contactUs.setText(response.body().getUserContactUs());
                    contactUsNumber.setText(response.body().getUserContactUsNumber());
                }
            }

            @Override
            public void onFailure(Call<ContactUsResponse> call, Throwable t) {

                initCode();
                Toast.makeText(ContactUsActivity.this, "Server error  "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
