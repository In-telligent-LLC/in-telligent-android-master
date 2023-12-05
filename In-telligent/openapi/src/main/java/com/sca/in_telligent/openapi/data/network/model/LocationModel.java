package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LocationModel implements Serializable {
    @SerializedName("lat")
    Double lat;


    @SerializedName("lng")
    Double lng;

    @SerializedName("success")
    Boolean success;

    @SerializedName("accuracy")
    float accuracy;

    public LocationModel() {
    }

    public LocationModel(Double lat, Double lng, float accuracy) {
        this.lat = lat;
        this.lng = lng;
        this.accuracy = accuracy;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }


    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }


    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public Boolean getSuccess() {
        return success;
    }
}
