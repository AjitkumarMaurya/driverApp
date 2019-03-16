package com.docuser.driveroncall.response;

import com.docuser.driveroncall.model.TripDeteilsModel;
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
