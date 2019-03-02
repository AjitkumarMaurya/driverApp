package com.abc.driveroncall.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserPrivacyPolicyResponse {

    @SerializedName("userPrivacyPolicy")
    @Expose
    private String userPrivacyPolicy;

    public String getUserPrivacyPolicy() {
        return userPrivacyPolicy;
    }

    public void setUserPrivacyPolicy(String userPrivacyPolicy) {
        this.userPrivacyPolicy = userPrivacyPolicy;
    }
}
