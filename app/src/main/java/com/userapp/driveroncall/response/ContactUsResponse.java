package com.userapp.driveroncall.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactUsResponse {
    @SerializedName("userContactUsNumber")
    @Expose
    private String userContactUsNumber;
    @SerializedName("userContactUs")
    @Expose
    private String userContactUs;

    public String getUserContactUsNumber() {
        return userContactUsNumber;
    }

    public void setUserContactUsNumber(String userContactUsNumber) {
        this.userContactUsNumber = userContactUsNumber;
    }

    public String getUserContactUs() {
        return userContactUs;
    }

    public void setUserContactUs(String userContactUs) {
        this.userContactUs = userContactUs;
    }
}
