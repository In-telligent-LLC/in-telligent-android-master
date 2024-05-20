package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BuildingMember implements Serializable {

  @SerializedName("Subscriber.id")
  private String subscriberId;

  @SerializedName("Subscriber.email")
  private String subscriberEmail;

  public String getSubscriberId() {
    return subscriberId;
  }

  public String getSubscriberEmail() {
    return subscriberEmail;
  }
}
