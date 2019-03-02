package com.abc.driveroncall.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpVerification {
    @SerializedName("matchOtp")
    @Expose
    private Boolean matchOtp;
   @SerializedName("mobile_no")
    @Expose
    private String mobileNo;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    @SerializedName("errors")
    @Expose
    private String errors;

    public Boolean getMatchOtp() {
        return matchOtp;
    }

    public void setMatchOtp(Boolean matchOtp) {
        this.matchOtp = matchOtp;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }
}
