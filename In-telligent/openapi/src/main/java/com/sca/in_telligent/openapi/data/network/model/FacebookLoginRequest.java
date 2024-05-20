package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FacebookLoginRequest implements Serializable {

  @SerializedName("deviceId")
  private String deviceId;

  @SerializedName("facebookAccessToken")
  private String facebookAccessToken;

  @SerializedName("adId")
  private String adId;

  public void setFacebookAccessToken(String facebookAccessToken) {
    this.facebookAccessToken = facebookAccessToken;
  }

  public void setAdId(String adId) {
    this.adId = adId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

}
