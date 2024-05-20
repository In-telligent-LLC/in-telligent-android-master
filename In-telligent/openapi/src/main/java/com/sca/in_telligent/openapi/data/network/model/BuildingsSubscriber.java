package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BuildingsSubscriber implements Serializable {

  @SerializedName("alertsSubscription")
  private String alertsSubscription;

  @SerializedName("offersSubscription")
  private String offersSubscription;

  @JsonAdapter(BooleanTypeAdapter.class)
  @SerializedName("automatic")
  private boolean automatic;

  public String getAlertsSubscription() {
    return alertsSubscription;
  }

  public String getOffersSubscription() {
    return offersSubscription;
  }

  public boolean isAutomatic() {
    return automatic;
  }

  public void setAlertsSubscription(String alertsSubscription) {
    this.alertsSubscription = alertsSubscription;
  }
}
