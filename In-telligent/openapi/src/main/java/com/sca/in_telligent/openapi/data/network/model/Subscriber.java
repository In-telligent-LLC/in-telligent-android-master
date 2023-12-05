package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Subscriber implements Serializable {

  @SerializedName("id")
  private int id;

  @SerializedName("email")
  private String email;

  @SerializedName("name")
  private String name;

  @SerializedName("language")
  private String language;

  public void setLanguage(String language) {
    this.language = language;
  }

  public void setLanguageName(String languageName) {
    this.languageName = languageName;
  }

  @SerializedName("languageName")
  private String languageName;

  public void setWeatherAlertEnabled(boolean weatherAlertEnabled) {
    this.weatherAlertEnabled = weatherAlertEnabled;
  }

  public void setLightningAlertEnabled(boolean lightningAlertEnabled) {
    this.lightningAlertEnabled = lightningAlertEnabled;
  }

  @SerializedName("weatherAlertEnabled")
  private boolean weatherAlertEnabled;

  @SerializedName("lightningAlertEnabled")
  private boolean lightningAlertEnabled;

  @SerializedName("lightningAlertConfirmed")
  private boolean lightningAlertConfirmed;

  @SerializedName("betaEnabled")
  private boolean betaEnabled;

  @SerializedName("User")
  private User user;

  @SerializedName("PersonalCommunity")
  private ArrayList<Building> personalCommunities;

  public void setBuildings(ArrayList<Building> buildings) {
    this.buildings = buildings;
  }

  @SerializedName("Buildings")
  private ArrayList<Building> buildings;

  @SerializedName("SubscriberAutoSubscribeOptOut")
  private ArrayList<SubscriberOptOut> subscriberOptOuts;

  @SerializedName("Interests")
  private ArrayList<Interest> interests;

  public int getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getName() {
    return name;
  }

  public String getLanguage() {
    return language;
  }


  public String getLanguageName() {
    return languageName;
  }


  public boolean isWeatherAlertEnabled() {
    return weatherAlertEnabled;
  }

  public boolean isLightningAlertEnabled() {
    return lightningAlertEnabled;
  }

  public boolean isLightningAlertConfirmed() {
    return lightningAlertConfirmed;
  }

  public boolean isBetaEnabled() {
    return betaEnabled;
  }

  public User getUser() {
    return user;
  }

  public ArrayList<Building> getPersonalCommunities() {
    return personalCommunities;
  }

  public ArrayList<Building> getBuildings() {
    return buildings;
  }

  public ArrayList<SubscriberOptOut> getSubscriberOptOuts() {
    return subscriberOptOuts;
  }

  public ArrayList<Interest> getInterests() {
    return interests;
  }
}
