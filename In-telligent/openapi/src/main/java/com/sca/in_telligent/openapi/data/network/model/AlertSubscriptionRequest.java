package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AlertSubscriptionRequest implements Serializable {

  @SerializedName("buildingId")
  private String buildingId;

  @SerializedName("subscription")
  private String subscription;

  public void setBuildingId(String buildingId) {
    this.buildingId = buildingId;
  }

  public void setSubscription(String subscription) {
    this.subscription = subscription;
  }

  public String getBuildingId() {
    return buildingId;
  }

  public String getSubscription() {
    return subscription;
  }

}
