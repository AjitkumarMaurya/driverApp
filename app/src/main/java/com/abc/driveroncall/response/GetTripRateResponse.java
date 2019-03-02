package com.abc.driveroncall.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetTripRateResponse {

    @SerializedName("tripRate")
    @Expose
    private TripRate tripRate;

    public TripRate getTripRate() {
        return tripRate;
    }

    public void setTripRate(TripRate tripRate) {
        this.tripRate = tripRate;
    }

    public class TripRate {

        @SerializedName("trip_hourly_rate")
        @Expose
        private String tripHourlyRate;
        @SerializedName("trip_gst_rate")
        @Expose
        private String tripGstRate;
        @SerializedName("trip_cgst_rate")
        @Expose
        private String tripCgstRate;
        @SerializedName("trip_food_charges")
        @Expose
        private String tripFoodCharges;
        @SerializedName("trip_accommodation_charges")
        @Expose
        private String tripAccommodationCharges;


        public String getTripHourlyRate() {
            return tripHourlyRate;
        }

        public void setTripHourlyRate(String tripHourlyRate) {
            this.tripHourlyRate = tripHourlyRate;
        }

        public String getTripGstRate() {
            return tripGstRate;
        }

        public void setTripGstRate(String tripGstRate) {
            this.tripGstRate = tripGstRate;
        }

        public String getTripCgstRate() {
            return tripCgstRate;
        }

        public void setTripCgstRate(String tripCgstRate) {
            this.tripCgstRate = tripCgstRate;
        }

        public String getTripFoodCharges() {
            return tripFoodCharges;
        }

        public void setTripFoodCharges(String tripFoodCharges) {
            this.tripFoodCharges = tripFoodCharges;
        }

        public String getTripAccommodationCharges() {
            return tripAccommodationCharges;
        }

        public void setTripAccommodationCharges(String tripAccommodationCharges) {
            this.tripAccommodationCharges = tripAccommodationCharges;
        }
    }
}



