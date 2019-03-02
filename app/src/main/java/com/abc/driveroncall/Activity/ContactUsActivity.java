package com.abc.driveroncall.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.abc.driveroncall.R;
import com.abc.driveroncall.response.ContactUsResponse;
import com.abc.driveroncall.retrofit.ApiClient;
import com.abc.driveroncall.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsActivity extends AppCompatActivity {
    TextView contactUsNumber, contactUs;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        contactUs = findViewById(R.id.tvContactUs);
        contactUsNumber = findViewById(R.id.tvContactUsNumber);
        dialog = new ProgressDialog(this);

        dialog.setMessage("Data is Processing...");
        dialog.show();

        final ApiInterface api = ApiClient.getApiService();

        Call<ContactUsResponse> call = api.getContactUs();
        call.enqueue(new Callback<ContactUsResponse>() {
            @Override
            public void onResponse(Call<ContactUsResponse> call, Response<ContactUsResponse> response) {
                dialog.dismiss();
                if (response != null && response.isSuccessful()) {


                    contactUs.setText(response.body().getUserContactUs());
                    contactUsNumber.setText(response.body().getUserContactUsNumber());
                }
            }

            @Override
            public void onFailure(Call<ContactUsResponse> call, Throwable t) {

            }
        });

    }
}
