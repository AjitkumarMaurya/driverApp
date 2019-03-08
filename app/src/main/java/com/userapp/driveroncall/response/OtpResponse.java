package com.userapp.driveroncall.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpResponse {


    @SerializedName("requestOtp")
    @Expose
    private Boolean requestOtp;













    @SerializedName("errors")
    @Expose
    private String errors ;

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public Boolean getRequestOtp() {
        return requestOtp;
    }

    public void setRequestOtp(Boolean requestOtp) {
        this.requestOtp = requestOtp;
    }
}