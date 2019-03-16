package com.docuser.driveroncall.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotChengPassResponse {
    @SerializedName("updateNewPasswod")
    @Expose
    private Boolean updateNewPasswod;
    @SerializedName("errors")
    @Expose
    private String errors;


    public Boolean getUpdateNewPasswod() {
        return updateNewPasswod;
    }

    public void setUpdateNewPasswod(Boolean updateNewPasswod) {
        this.updateNewPasswod = updateNewPasswod;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }
}
