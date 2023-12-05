package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GoogleLoginRequest implements Serializable {

  @SerializedName("deviceId")
  private String deviceId;

  @SerializedName("googleAccessToken")
  private String googleAccessToken;

  @SerializedName("adId")
  private String adId;

  public void setGoogleAccessToken(String googleAccessToken) {
    this.googleAccessToken = googleAccessToken;
  }

  public void setAdId(String adId) {
    this.adId = adId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

}
