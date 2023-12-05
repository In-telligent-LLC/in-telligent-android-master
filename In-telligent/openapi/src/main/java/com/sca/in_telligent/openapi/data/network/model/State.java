package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class State implements Serializable {

  @SerializedName("id")
  private int id;

  @SerializedName("countryId")
  private int countryId;

  @SerializedName("name")
  private String name;

  @SerializedName("shortName")
  private String shortName;

  public int getId() {
    return id;
  }

  public int getCountryId() {
    return countryId;
  }

  public String getName() {
    return name;
  }

  public String getShortName() {
    return shortName;
  }

}
