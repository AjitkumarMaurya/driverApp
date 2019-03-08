package com.userapp.driveroncall.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfileResponse {

    @SerializedName("updateProfile")
    @Expose
    private Boolean updateProfile;
    @SerializedName("error")
    @Expose
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Boolean getUpdateProfile() {
        return updateProfile;
    }

    public void setUpdateProfile(Boolean updateProfile) {
        this.updateProfile = updateProfile;
    }
}
