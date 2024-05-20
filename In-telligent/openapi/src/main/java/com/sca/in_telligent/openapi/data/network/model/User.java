package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

  @SerializedName("id")
  private int id;

  @SerializedName("canSendLSA")
  private boolean canSendLSA;

  @SerializedName("Buildings")
  ArrayList<BuildingIdItem> buildingIds;

  public int getId() {
    return id;
  }

  public boolean isCanSendLSA() {
    return canSendLSA;
  }

  public ArrayList<BuildingIdItem> getBuildingIds() {
    return buildingIds;
  }

}
