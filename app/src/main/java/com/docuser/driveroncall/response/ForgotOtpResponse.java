package com.docuser.driveroncall.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotOtpResponse {


    @SerializedName("matchOtp")
    @Expose
    private Boolean matchOtp;
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
