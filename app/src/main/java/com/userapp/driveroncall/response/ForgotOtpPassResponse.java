package com.userapp.driveroncall.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotOtpPassResponse {


    @SerializedName("otpRequest")
    @Expose
    private Boolean otpRequest;
    @SerializedName("errors")
    @Expose
    private String errors;

    public Boolean getOtpRequest() {
        return otpRequest;
    }

    public void setOtpRequest(Boolean otpRequest) {
        this.otpRequest = otpRequest;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }
}
