package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubscriberOptOut implements Serializable {

  @SerializedName("id")
  private int id;

  @SerializedName("buildingId")
  private int buildingId;

  public int getId() {
    return id;
  }

  public int getBuildingId() {
    return buildingId;
  }

  public int getSubscriberId() {
    return subscriberId;
  }

  @SerializedName("subscriberId")
  private int subscriberId;
}
