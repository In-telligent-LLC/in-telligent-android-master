package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PushTokenRequest implements Serializable {

  @SerializedName("pushToken")
  private String pushToken;

  @SerializedName("environment")
  private String environment;

  public void setPushToken(String pushToken) {
    this.pushToken = pushToken;
  }

  public void setEnvironment(String environment) {
    this.environment = environment;
  }


}
