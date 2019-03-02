package com.abc.driveroncall.response;

import com.abc.driveroncall.model.UserMyTripModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MyTripsResponse {

    @SerializedName("userMyTrips")
    @Expose
    private ArrayList<UserMyTripModel> userMyTripModelList = null;

    public ArrayList<UserMyTripModel> getUserMyTripModelList() {
        return userMyTripModelList;
    }

    public void setUserMyTripModelList(ArrayList<UserMyTripModel> userMyTripModelList) {
        this.userMyTripModelList = userMyTripModelList;
    }
}
