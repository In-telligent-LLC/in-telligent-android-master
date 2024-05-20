package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubscriberRequest implements Serializable {

  @SerializedName("name")
  private String name;

  @SerializedName("email")
  private String email;

  @SerializedName("language")
  private String language;

  @SerializedName("weatherAlertEnabled")
  private boolean weatherAlertEnabled;

  @SerializedName("lightningAlertEnabled")
  private boolean lightningAlertEnabled;

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public void setLightningAlertEnabled(boolean lightningAlertEnabled) {
    this.lightningAlertEnabled = lightningAlertEnabled;
  }

  public void setWeatherAlertEnabled(boolean weatherAlertEnabled) {
    this.weatherAlertEnabled = weatherAlertEnabled;
  }

}