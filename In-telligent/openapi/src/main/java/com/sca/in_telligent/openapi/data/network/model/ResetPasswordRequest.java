package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResetPasswordRequest implements Serializable {

  @SerializedName("deviceId")
  private String deviceId;

  @SerializedName("resetCode")
  private String resetCode;

  @SerializedName("password")
  private String password;


  @SerializedName("password2")
  private String password2;

  @SerializedName("adId")
  private String adId;

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public void setResetCode(String resetCode) {
    this.resetCode = resetCode;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setPassword2(String password2) {
    this.password2 = password2;
  }

  public void setAdId(String adId) {
    this.adId = adId;
  }

}
