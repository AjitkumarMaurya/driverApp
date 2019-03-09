package com.userapp.driveroncall.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abc.driveroncall.R;
import com.userapp.driveroncall.response.UserProfileResponse;
import com.userapp.driveroncall.response.UserProfileUpdateResponse;
import com.userapp.driveroncall.retrofit.ApiClient;
import com.userapp.driveroncall.retrofit.ApiInterface;
import com.userapp.driveroncall.utility.PreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    EditText edtFname, edtLname, edtGmali, edtAddress, edtBoD, edtPincode,edtPhone;
    Button save;
    int restoredIDUser;
    int restoredText;
    String myid;
    ProgressDialog dialog;
    String eid, eFirst, eLast, eGmail, eBod, eAddress, ePin;
    TextView changePassword;
    DatePickerDialog datePickerDialog;

    PreferenceManager preferenceManager;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        preferenceManager = new PreferenceManager(this);

        edtFname = findViewById(R.id.edtFnamePro);

        edtLname = findViewById(R.id.edtLnamePro);
        edtGmali = findViewById(R.id.edtEmailPro);
        edtAddress = findViewById(R.id.edtPremanentAdderssPro);
        edtBoD = findViewById(R.id.edtBobPro);
        edtPincode = findViewById(R.id.edtPincodePro);
        save = findViewById(R.id.btnSavePro);
        edtPhone = findViewById(R.id.edtPhone);

        dialog = new ProgressDialog(this);

        myid = preferenceManager.getRegisteredUserId();


        edtFname.setText(""+preferenceManager.getKeyValueString("firstName"));
        edtLname.setText(""+preferenceManager.getKeyValueString("lastName"));
        edtGmali.setText(""+preferenceManager.getKeyValueString("email"));
        edtAddress.setText(""+preferenceManager.getKeyValueString("uAddress"));
        edtBoD.setText(""+preferenceManager.getKeyValueString("uBdate"));
        edtPincode.setText(""+preferenceManager.getKeyValueString("uPinCode"));

        edtPhone.setEnabled(true);
        edtPhone.setText(""+preferenceManager.getKeyValueString("mobileNumber"));
        edtPhone.setEnabled(false);


//get Data SharedPreferences
        //   SharedPreferences prefs = getSharedPreferences("MyPref", MODE_PRIVATE);
      /*  final String fname = prefs.getString("shFname", "First name");//"No name defined" is the default value.
        final String lname = prefs.getString("shLname", "Last name");//"No name defined" is the default value.
        final String gmail = prefs.getString("shGmail", "Email");//"No name defined" is the default value.

        edtFname.setText(fname);
        edtLname.setText(lname);
        edtGmali.setText(gmail);
*/


        save.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                        eFirst = edtFname.getText().toString();
                                        eLast = edtLname.getText().toString();
                                        eGmail = edtGmali.getText().toString();
                                        eAddress = edtAddress.getText().toString();
                                        eBod = edtBoD.getText().toString();
                                        ePin = edtPincode.getText().toString();
                                        eGmail = edtGmali.getText().toString().trim();

                                        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

//-------------------------------------------------------


                                        eGmail = edtGmali.getText().toString().trim();


                                        // onClick of button perform this simplest code.
                                        if (eGmail.matches(emailPattern)) {
                                            //   Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
                                        } else {
                                            edtGmali.setError("Invalid email address");
                                            // Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                                        }


                                        if (TextUtils.isEmpty(eFirst)) {

                                            edtFname.setError("Enter First Name");
                                        }

                                        if (TextUtils.isEmpty(eLast)) {

                                            edtLname.setError("Enter last Name");
                                        }


                                        if (TextUtils.isEmpty(eGmail) && isValidEmail(eGmail)) {
                                            edtGmali.setError("Enter Email Address");

                                        }


                                        if (TextUtils.isEmpty(eAddress)) {
                                            edtAddress.setError("Enter Address");
                                        }


                                        if (TextUtils.isEmpty(eBod)) {
                                            edtBoD.setError("BirthDate");
                                        }

                                        if (TextUtils.isEmpty(ePin)) {
                                            edtPincode.setError("Pincode Code");
                                        }


                                        if (!TextUtils.isEmpty(eFirst) && !TextUtils.isEmpty(eLast) && !TextUtils.isEmpty(eGmail) && !TextUtils.isEmpty(eBod) && !TextUtils.isEmpty(eAddress) && !TextUtils.isEmpty(ePin)) {

                                            ProfileUpdate();
                                        }


                                    }


                                    private void ProfileUpdate() {


                                        dialog.setMessage("Data is Updating...");
                                        dialog.show();

                                        final ApiInterface api = ApiClient.getApiService();


                                        Call<UserProfileResponse> call = api.getUserId(myid, edtFname.getText().toString(), edtLname.getText().toString(), edtBoD.getText().toString(), edtGmali.getText().toString(), edtAddress.getText().toString(), edtPincode.getText().toString());
                                        call.enqueue(new Callback<UserProfileResponse>() {
                                            @Override
                                            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
                                                dialog.dismiss();


                                                if (response != null && response.isSuccessful()) {

                                                    if (!response.body().getUpdateProfile()) {

                                                        Toast.makeText(ProfileActivity.this, response.body().getError(), Toast.LENGTH_SHORT).show();

                                                    } else {
                                                        Toast.makeText(ProfileActivity.this, "Save", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                            }

                                            @Override
                                            public void onFailure(Call<UserProfileResponse> call, Throwable t) {
                                                dialog.dismiss();

                                                t.getMessage();
                                            }
                                        });
                                    }
                                }
        );


        edtBoD.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(ProfileActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                edtBoD.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });





        final ApiInterface api = ApiClient.getApiService();
        Call<UserProfileUpdateResponse> call = api.getUpDateProfile(myid);
        call.enqueue(new Callback<UserProfileUpdateResponse>() {
                         @SuppressLint("SetTextI18n")
                         @Override
                         public void onResponse(Call<UserProfileUpdateResponse> call, Response<UserProfileUpdateResponse> response) {

                             if (response != null && response.isSuccessful()) {

                                 try {
                                     edtFname.setText(""+response.body().getUserProfileUpDateModel().getUsersFirstName());
                                     edtLname.setText(""+response.body().getUserProfileUpDateModel().getUsersLastName());
                                     edtGmali.setText(""+response.body().getUserProfileUpDateModel().getUsersEmail());
                                     edtAddress.setText(""+response.body().getUserProfileUpDateModel().getUsersAddress().toString());
                                     edtBoD.setText(""+response.body().getUserProfileUpDateModel().getUsersBirthDate().toString());
                                     edtPincode.setText(""+response.body().getUserProfileUpDateModel().getUsersPincode().toString());


                                     preferenceManager.setKeyValueString("firstName",""+response.body().getUserProfileUpDateModel().getUsersFirstName());
                                     preferenceManager.setKeyValueString("lastName",""+response.body().getUserProfileUpDateModel().getUsersLastName());
                                     preferenceManager.setKeyValueString("email",""+response.body().getUserProfileUpDateModel().getUsersEmail());
                                     preferenceManager.setKeyValueString("uAddress",""+response.body().getUserProfileUpDateModel().getUsersAddress().toString());
                                     preferenceManager.setKeyValueString("uBdate",""+response.body().getUserProfileUpDateModel().getUsersBirthDate().toString());
                                     preferenceManager.setKeyValueString("uPinCode",""+response.body().getUserProfileUpDateModel().getUsersPincode().toString());
                                 } catch (Exception e) {
                                     e.printStackTrace();
                                 }

                             }
                         }

                         @Override
                         public void onFailure(Call<UserProfileUpdateResponse> call, Throwable t) {

                         }
                     }
        );

    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


}




