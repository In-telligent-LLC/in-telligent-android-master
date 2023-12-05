package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SignUpRequest implements Serializable {

  @SerializedName("deviceId")
  private String deviceId;

  @SerializedName("email")
  private String email;

  @SerializedName("password")
  private String password;

  @SerializedName("password2")
  private String password2;

  @SerializedName("name")
  private String name;

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

  public void setPassword2(String password2) {
    this.password2 = password2;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAdId(String adId) {
    this.adId = adId;
  }
}
