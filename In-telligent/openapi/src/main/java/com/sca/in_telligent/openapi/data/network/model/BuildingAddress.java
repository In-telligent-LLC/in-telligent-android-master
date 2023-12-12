package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class BuildingAddress implements Serializable {
    @SerializedName("City")
    private City city;
    @SerializedName("Country")
    private Country country;
    @SerializedName("formattedAddress")
    private String formattedAddress;
    @SerializedName("isVirtual")
    @JsonAdapter(BooleanTypeAdapter.class)
    private boolean isVirtual;
    @SerializedName("lat")
    private double latitude;
    @SerializedName("lng")
    private double longitude;
    @SerializedName("postalCode")
    private String postalCode;
    @SerializedName("radius")
    private int radius;
    @SerializedName("route")
    private String route;
    @SerializedName("State")
    private State state;
    @SerializedName("streetNumber")
    private String streetNumber;

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public String getFormattedAddress() {
        return this.formattedAddress;
    }

    public String getStreetNumber() {
        return this.streetNumber;
    }

    public String getRoute() {
        return this.route;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public int getRadius() {
        return this.radius;
    }

    public boolean isVirtual() {
        return this.isVirtual;
    }

    public City getCity() {
        return this.city;
    }

    public State getState() {
        return this.state;
    }

    public Country getCountry() {
        return this.country;
    }
}
