package com.docuser.driveroncall.retrofit;

import com.docuser.driveroncall.response.CancelResponce;
import com.docuser.driveroncall.response.CompleteTripResponce;
import com.docuser.driveroncall.response.ContactUsResponse;
import com.docuser.driveroncall.response.CreateTripResponse;
import com.docuser.driveroncall.response.ForgotChengPassResponse;
import com.docuser.driveroncall.response.ForgotOtpPassResponse;
import com.docuser.driveroncall.response.ForgotOtpResponse;
import com.docuser.driveroncall.response.GetTripRateResponse;
import com.docuser.driveroncall.response.LoginResponse;
import com.docuser.driveroncall.response.MyBookResponce;
import com.docuser.driveroncall.response.MyTripsResponse;
import com.docuser.driveroncall.response.OtpResponse;
import com.docuser.driveroncall.response.OtpVerification;
import com.docuser.driveroncall.response.PasswordForgetResponse;
import com.docuser.driveroncall.response.PaymentInfoResponce;
import com.docuser.driveroncall.response.ProfileUsResponse;
import com.docuser.driveroncall.response.RatingResponce;
import com.docuser.driveroncall.response.RegistesonResponse;
import com.docuser.driveroncall.response.UserPrivacyPolicyResponse;
import com.docuser.driveroncall.response.UserProfileResponse;
import com.docuser.driveroncall.response.UserProfileUpdateResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2/26/2018.
 */

public interface ApiInterface {

    @GET("users/register")
    Call<RegistesonResponse> getRagisteson(@Query("fname") String fName,
                                           @Query("lname") String lMame,
                                           @Query("mobile_no") String mobileNo,
                                           @Query("email") String email,
                                           @Query("password") String password);


    @GET("users/updateProfile")
    Call<UserProfileResponse> getUserId(@Query("userId") String UserId,
                                        @Query("first_name") String fName,
                                        @Query("last_name") String lMame,
                                        @Query("birth_date") String BirthDate,
                                        @Query("email") String email,
                                        @Query("address") String Address, @Query("pincode") String Pincode);

    @GET("users/userLogin")
    Call<LoginResponse> GetLogin(@Query("mobile_no") String mobileNo, @Query("password") String password);

    @GET("users/requestOtp")
    Call<OtpResponse> getOtp(@Query("mobile_no") String mobileNo);

    @GET("users/matchOtp")
    Call<OtpVerification> getOtpVarify(@Query("mobile_no") String mobileNo, @Query("otp") String otp);

    @GET("users/updatePassword")
    Call<PasswordForgetResponse> getForegetePassword(@Query("userId") String UserId, @Query("old_password") String Old_Password, @Query("new_password") String new_password);

    @GET("users/userAboutUs")
    Call<ProfileUsResponse> getAboutAs();  // (@Url String About);

    @GET("users/userContactUs")
    Call<ContactUsResponse> getContactUs();

    @GET("users/userPrivacyPolicy")
    Call<UserPrivacyPolicyResponse> getUserPrivacyPolicy();

    @GET("users/myTrips")
    Call<MyTripsResponse> getMyTrips(@Query("userId") String UserId);

    @GET("users/forgotPasswordOtp")
    Call<ForgotOtpPassResponse> getForPassOtp(@Query("mobile_no") String mobileNo);

    @GET("users/forgotPasswordMatchOtp")
    Call<ForgotOtpResponse> getOtpForPassVarify(@Query("mobile_no") String mobileNo, @Query("otp") String otp);

    @GET("users/updateNewPasswod")
    Call<ForgotChengPassResponse> getChengePassword(@Query("mobile_no") String mobileNo, @Query("password") String password);

    @GET("users/userProfile")
    Call<UserProfileUpdateResponse> getUpDateProfile(@Query("userId") String id);

    @GET("users/getTripRate")
    Call<GetTripRateResponse> getTripRate();

    @GET("users/myBookings")
    Call<MyBookResponce> getMyBooking(@Query("userId") String userId);

    @GET("users/cancelTrip")
    Call<CancelResponce> cancelTrip(@Query("tripId") String tripId);

    @GET("users/getTripRate")
    Call<RatingResponce> getRartinTrip(@Query("tripId") String tripId);

    @GET("users/addTrip")
    Call<CreateTripResponse> AddTrip(@Query("userId") String userId,
                                     @Query("trip_start_date") String trip_start_date,
                                     @Query("trip_start_time") String trip_start_time,
                                     @Query("trip_type_id") String trip_type_id,
                                     @Query("trip_pickup_point_lat") Double trip_pickup_point_lat,
                                     @Query("trip_pickup_point_lang") Double trip_pickup_point_lang,
                                     @Query("trip_pickup_point_address") String trip_pickup_point_address,
                                     @Query("trip_pickup_point_city_name") String trip_pickup_point_city_name,
                                     @Query("trip_drop_point_lat") Double trip_drop_point_lat,
                                     @Query("trip_drop_point_lang") Double trip_drop_point_lang,
                                     @Query("trip_drop_point_address") String trip_drop_point_address,
                                     @Query("trip_drop_point_city_name") String trip_drop_point_city_name,
                                     @Query("trip_ast_usage") String trip_ast_usage,
                                     @Query("trip_amount") String trip_amount,
                                     @Query("trip_payment_type") String paytype);

    @GET("users/paymentDetails")
    Call<PaymentInfoResponce> getPayInfo(@Query("tripId") String tripId);

    @GET("users/completeTrip")
    Call<CompleteTripResponce> completTrip(@Query("tripId") String tripId);

}
