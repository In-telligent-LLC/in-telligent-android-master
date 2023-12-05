package com.sca.in_telligent.openapi.data.network.model;

import android.location.Location;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class IntelligentGeofence implements Serializable {

  @SerializedName("id")
  private String id;

  private String idString;

  @SerializedName("name")
  private String name;

  @SerializedName("radius")
  private int radius;

  @SerializedName("lat")
  private double lat;

  @SerializedName("lng")
  private double lng;

  @SerializedName("priority")
  private int priority;

  @SerializedName("secure")
  private boolean secure;

  @SerializedName("type")
  private String type;

  private Location location;

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getRadius() {
    return radius;
  }

  public double getLat() {
    return lat;
  }

  public double getLng() {
    return lng;
  }

  public int getPriority() {
    return priority;
  }

  public boolean isSecure() {
    return secure;
  }

  public String getType() {
    return type;
  }

  public String getIdString() {
    return idString = type + "-" + id;
  }

  public Location getLocation() {
    location = new Location("Geofence");
    location.setLatitude(lat);
    location.setLongitude(lng);
    return location;
  }
}
