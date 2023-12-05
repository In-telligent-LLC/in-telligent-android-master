package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubscriberResponse implements Serializable {

  @JsonAdapter(BooleanTypeAdapter.class)
  @SerializedName("success")
  private boolean success;

  @SerializedName("Subscriber")
  private Subscriber subscriber;

  public boolean isSuccess() {
    return success;
  }

  public Subscriber getSubscriber() {
    return subscriber;
  }
}
