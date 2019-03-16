package com.docuser.driveroncall.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompleteTripResponce {

    @SerializedName("completeTrip")
    @Expose
    private boolean completeTrip;

    public boolean isCompleteTrip() {
        return completeTrip;
    }

    public void setCompleteTrip(boolean completeTrip) {
        this.completeTrip = completeTrip;
    }
}
