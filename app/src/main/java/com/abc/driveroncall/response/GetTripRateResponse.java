package com.abc.driveroncall.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetTripRateResponse {

    @SerializedName("tripHourlyRate")
    @Expose
    private String tripHourlyRate;

    public String getTripHourlyRate() {
        return tripHourlyRate;
    }

    public void setTripHourlyRate(String tripHourlyRate) {
        this.tripHourlyRate = tripHourlyRate;
    }
}
