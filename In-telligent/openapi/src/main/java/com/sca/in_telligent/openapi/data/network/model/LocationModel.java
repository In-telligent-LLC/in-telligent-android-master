package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class LocationModel implements Serializable {
    @SerializedName("accuracy")
    float accuracy;
    @SerializedName("lat")
    Double lat;
    @SerializedName("lng")
    Double lng;
    @SerializedName("success")
    Boolean success;

    public LocationModel() {
    }

    public LocationModel(Double d, Double d2, float f) {
        this.lat = d;
        this.lng = d2;
        this.accuracy = f;
    }

    public void setLat(Double d) {
        this.lat = d;
    }

    public void setLng(Double d) {
        this.lng = d;
    }

    public void setSuccess(Boolean bool) {
        this.success = bool;
    }

    public float getAccuracy() {
        return this.accuracy;
    }

    public void setAccuracy(float f) {
        this.accuracy = f;
    }

    public Double getLat() {
        return this.lat;
    }

    public Double getLng() {
        return this.lng;
    }

    public Boolean getSuccess() {
        return this.success;
    }
}
