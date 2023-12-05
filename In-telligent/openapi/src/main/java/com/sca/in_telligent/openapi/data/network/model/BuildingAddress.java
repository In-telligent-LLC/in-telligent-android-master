package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BuildingAddress implements Serializable {

  @SerializedName("lat")
  private double latitude;

  @SerializedName("lng")
  private double longitude;

  @SerializedName("formattedAddress")
  private String formattedAddress;

  @SerializedName("streetNumber")
  private String streetNumber;

  @SerializedName("route")
  private String route;

  @SerializedName("postalCode")
  private String postalCode;

  @SerializedName("radius")
  private int radius;

  @JsonAdapter(BooleanTypeAdapter.class)
  @SerializedName("isVirtual")
  private boolean isVirtual;

  @SerializedName("City")
  private City city;

  @SerializedName("State")
  private State state;

  @SerializedName("Country")
  private Country country;

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public String getFormattedAddress() {
    return formattedAddress;
  }

  public String getStreetNumber() {
    return streetNumber;
  }

  public String getRoute() {
    return route;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public int getRadius() {
    return radius;
  }

  public boolean isVirtual() {
    return isVirtual;
  }

  public City getCity() {
    return city;
  }

  public State getState() {
    return state;
  }

  public Country getCountry() {
    return country;
  }
}
