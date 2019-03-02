package com.abc.driveroncall.response;

import com.abc.driveroncall.model.UserProfileModel;
import com.abc.driveroncall.model.UserProfileUpDateModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfileUpdateResponse {


    @SerializedName("userProfile")
    @Expose
    private UserProfileUpDateModel userProfileUpDateModel;


    public UserProfileUpDateModel getUserProfileUpDateModel() {
        return userProfileUpDateModel;
    }

    public void setUserProfileUpDateModel(UserProfileUpDateModel userProfileUpDateModel) {
        this.userProfileUpDateModel = userProfileUpDateModel;
    }
}
