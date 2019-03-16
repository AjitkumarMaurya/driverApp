package com.docuser.driveroncall.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateTripResponse {

    @SerializedName("createTrip")
    @Expose
    private Boolean createTrip;

    public Boolean getCreateTrip() {
        return createTrip;
    }

    public void setCreateTrip(Boolean createTrip) {
        this.createTrip = createTrip;
    }
}
