package com.abc.driveroncall.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileUsResponse {


    @SerializedName("userAboutUs")
    @Expose
    private String userAboutUs;

    public String getUserAboutUs() {
        return userAboutUs;
    }

    public void setUserAboutUs(String userAboutUs) {
        this.userAboutUs = userAboutUs;
    }
}
