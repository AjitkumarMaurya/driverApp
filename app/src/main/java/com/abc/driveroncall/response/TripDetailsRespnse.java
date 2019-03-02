package com.abc.driveroncall.response;

import com.abc.driveroncall.model.TripDeteilsModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TripDetailsRespnse {

    @SerializedName("trip")
    @Expose
    private List<TripDeteilsModel> trip = null;

    public List<TripDeteilsModel> getTrip() {
        return trip;
    }

    public void setTrip(List<TripDeteilsModel> trip) {
        this.trip = trip;
    }
}
