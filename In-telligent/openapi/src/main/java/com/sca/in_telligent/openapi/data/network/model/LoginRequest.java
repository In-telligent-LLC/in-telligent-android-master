package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginRequest implements Serializable {

  @SerializedName("deviceId")
  private String deviceId;

  @SerializedName("email")
  private String email;

  @SerializedName("password")
  private String password;

  @SerializedName("adId")
  private String adId;

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setAdId(String adId) {
    this.adId = adId;
  }

}
