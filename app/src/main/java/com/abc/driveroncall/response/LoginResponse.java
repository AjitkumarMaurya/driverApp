package com.abc.driveroncall.response;

import com.abc.driveroncall.model.UserResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {


    @SerializedName("login")
    @Expose
    private Boolean login;

    @SerializedName("user")
    @Expose
    private UserResponse userResponse;


    @SerializedName("errors")
    @Expose
    private String errors;


    public UserResponse getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(UserResponse userResponse) {
        this.userResponse = userResponse;
    }

    public Boolean getLogin() {
        return login;
    }

    public void setLogin(Boolean login) {
        this.login = login;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }
}
