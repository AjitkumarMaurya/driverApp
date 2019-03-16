package com.docuser.driveroncall.response;

import com.docuser.driveroncall.model.UserMyTripModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

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
