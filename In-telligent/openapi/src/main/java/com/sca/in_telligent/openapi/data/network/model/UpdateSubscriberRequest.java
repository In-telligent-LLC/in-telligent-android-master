package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UpdateSubscriberRequest implements Serializable {

  public void setSubscriberRequest(
      SubscriberRequest subscriberRequest) {
    this.subscriberRequest = subscriberRequest;
  }

  @SerializedName("subscriber")
  private SubscriberRequest subscriberRequest;

}
