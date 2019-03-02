package com.abc.driveroncall.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abc.driveroncall.MainActivity;
import com.abc.driveroncall.R;
import com.abc.driveroncall.response.RegistesonResponse;
import com.abc.driveroncall.retrofit.ApiClient;
import com.abc.driveroncall.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    private static final String TAG = "test";
    EditText edtFname, edtLname, edtmobile, edtGmail, edtpassword, edtconfirmPassword;
    ProgressDialog dialog;
    Button submmit;
    private String firstName, lastName, mobile, eMail, password, conpassword;
    TextView tvLoginMove, tv_errorMail;
    int userID;
    String shFname, shLname, shGmail;
    String mobileNo;
    String restoredText;
    String no;
    String fisrt;

    String mobileNoPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        edtFname = findViewById(R.id.edt_Fname);
        edtLname = findViewById(R.id.edt_Lname);
        edtGmail = findViewById(R.id.edt_Email);
        edtmobile = findViewById(R.id.edt_Mobile);
        edtpassword = findViewById(R.id.edt_Password);
        edtconfirmPassword = findViewById(R.id.edt_Confirm_Password);
        submmit = findViewById(R.id.btn_Submit);
        tv_errorMail = findViewById(R.id.tv_eroorEmail);
        dialog = new ProgressDialog(this);
        // apiInterface = ApiClient.getClient().create(ApiInterface.class);
        tvLoginMove = findViewById(R.id.tv_Login);

        firstName = edtFname.getText().toString();
        lastName = edtLname.getText().toString();
        eMail = edtGmail.getText().toString();
        mobile = edtmobile.getText().toString();
        password = edtpassword.getText().toString();
        conpassword = edtconfirmPassword.getText().toString();


        Intent intent = getIntent();
        mobileNoPass = intent.getStringExtra("USER_NAME");

        edtmobile.setText(mobileNoPass);


        tvLoginMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });


        submmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = edtFname.getText().toString();
                lastName = edtLname.getText().toString();
                eMail = edtGmail.getText().toString();
                mobile = edtmobile.getText().toString();
                password = edtpassword.getText().toString();
                conpassword = edtconfirmPassword.getText().toString();


                boolean isPasswordSame = false;



         /*       boolean isEmail(EditText text){
                    CharSequence email=text.gettext.toString ();
                    return(!TextUtils.isEmpty(email)&&Patterns.EMAIL_ADDRESS.matcher(email).matches());

                }*/


                /*final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                edtGmail.addTextChangedListener(new TextWatcher() {
                    public void afterTextChanged(Editable s) {

                        if (eMail.matches(emailPattern) && s.length() > 0) {
                            Toast.makeText(getApplicationContext(), "valid email address", Toast.LENGTH_SHORT).show();
                            // or
                            //     textView.setText("valid email");
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                            //or
                            edtGmail.setError("invalid email");
                        }
                    }

                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // other stuffs
                    }

                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // other stuffs
                    }


                });
*/


                eMail = edtGmail.getText().toString().trim();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                // onClick of button perform this simplest code.
                if (eMail.matches(emailPattern)) {
                    //   Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
                } else {
                    edtGmail.setError("Invalid email address");
                    // Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                }


                if (TextUtils.isEmpty(firstName)) {

                    edtFname.setError("Enter First Name");
                }

                if (TextUtils.isEmpty(lastName)) {

                    edtLname.setError("Enter last Name");
                }


                if (TextUtils.isEmpty(eMail) && isValidEmail(eMail)) {
                    edtGmail.setError("Enter Email Address");

                }


                if (TextUtils.isEmpty(mobile)) {
                    edtmobile.setError("enter Mobile No ");
                }


                if (TextUtils.isEmpty(password)) {
                    edtpassword.setError("Enter Password");
                }

                if (TextUtils.isEmpty(conpassword)) {
                    edtconfirmPassword.setError("Enter Confirm Password");
                }

                if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(conpassword)) {
                    if (!password.equals(conpassword)) {
                        //edtpassword.setError("Enter same password");
                        edtconfirmPassword.setError("Enter same password");

                        isPasswordSame = false;
                    } else {


                        isPasswordSame = true;
                    }
                }

                if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(eMail) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(conpassword) && isPasswordSame) {

                    registerUser();
                }

/*

                SharedPreferences prefs = getSharedPreferences("MyPref", MODE_PRIVATE);
                restoredText = prefs.getString("mo", "");
*/


            }
        });


    }

    private void registerUser() {


        dialog.setMessage("Data is Updating...");
        dialog.show();
      /*  Intent intent = getIntent();
        final String hi = intent.getStringExtra("asd");
*/

        final ApiInterface api = ApiClient.getApiService();

        Call<RegistesonResponse> call = api.getRagisteson(edtFname.getText().toString(),
                edtLname.getText().toString(), mobileNoPass, edtGmail.getText().toString(), edtpassword.getText().toString());


        call.enqueue(new Callback<RegistesonResponse>() {
            @Override
            public void onResponse(Call<RegistesonResponse> call, Response<RegistesonResponse> response) {

                dialog.dismiss();

                if (response != null && response.isSuccessful()) {
                    if (!response.body().getRegister()) {

                        Toast.makeText(RegistrationActivity.this, response.body().getError(), Toast.LENGTH_SHORT).show();


                    } else {

                        fisrt = getIntent().getStringExtra("dipak");


                        Toast.makeText(RegistrationActivity.this, restoredText, Toast.LENGTH_SHORT).show();

                        userID = response.body().getUserResponse().getUsersId();
                        shFname = response.body().getUserResponse().getUsersFirstName();
                        shLname = response.body().getUserResponse().getUsersLastName();
                        shGmail = response.body().getUserResponse().getUsersEmail();

                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                        // ----------Set Data SharedPreferences------------------------------

                        Log.d(TAG, "onResponse() returned: " + response.body().getUserResponse().getUsersFirstName());

                        SharedPreferences.Editor editor = getSharedPreferences("MyPref", MODE_PRIVATE).edit();
                        editor.putString("firstName", shFname);
                        editor.putString("lastName", shLname);
                        editor.putString("email", shGmail);
                        editor.putString("mobileNumber", mobileNoPass);
                        editor.putInt("userID", userID);
                        editor.apply();

                    }


                }
            }

            @Override
            public void onFailure(Call<RegistesonResponse> call, Throwable t) {
                dialog.dismiss();
                Log.d(TAG, "onFailure() returned: " + t.getMessage());
                t.getMessage();
                Toast.makeText(RegistrationActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


}
