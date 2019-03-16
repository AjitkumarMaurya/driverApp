package com.docuser.driveroncall.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PasswordForgetResponse {


    @SerializedName("updatePassword")
    @Expose
    private Boolean updatePassword;

    @SerializedName("errors")
    @Expose
    private String errors ;


    public Boolean getUpdatePassword() {
        return updatePassword;
    }

    public void setUpdatePassword(Boolean updatePassword) {
        this.updatePassword = updatePassword;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }
}
