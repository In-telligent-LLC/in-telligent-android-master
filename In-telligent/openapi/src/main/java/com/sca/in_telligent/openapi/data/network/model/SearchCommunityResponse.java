package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchCommunityResponse implements Serializable {

  public ArrayList<Building> getBuildings() {
    return buildings;
  }

  @SerializedName("Buildings")
  ArrayList<Building> buildings;

}
