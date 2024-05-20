package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PushNotification implements Serializable {

  @SerializedName("id")
  private String id;

  @SerializedName("uuid")
  private String uuid;

  @SerializedName("alertType")
  private String alertType;

  @SerializedName("type")
  private String type;

  @SerializedName("action")
  private String action;

  @SerializedName("building_id")
  private String buildingId;

  @SerializedName("notification_id")
  private String notificationId;

  @SerializedName("pc_emergency_alert")
  private String pc_emergency_alert;

  @SerializedName("body")
  private String body;

  @SerializedName("title")
  private String title;

  @SerializedName("pc_urgent_alert")
  private String pc_urgent_alert;

  @SerializedName("personal_safety")
  private String personal_safety;

  @SerializedName("increment_badge")
  private String increment_badge;

  @SerializedName("ping_alert")
  private String ping_alert;

  @SerializedName("weather_alert")
  private String weather_alert;

  @SerializedName("lightning_alert")
  private String lightning_alert;

  @SerializedName("life_safety")
  private String life_safety;

  @SerializedName("offer_id")
  private String offerId;

  @SerializedName("social_type")
  private String socialType;

  @SerializedName("critical_safety")
  private String criticalSafety;

  @SerializedName("feedAlertId")
  private String feedAlertId;

  @SerializedName("conferenceId")
  private String conferenceId;

  @SerializedName("remoteUserName")
  private String remoteUserName;

  public String getRemoteUserName() {
    return remoteUserName;
  }

  public String getConferenceId() {
    return conferenceId;
  }
  public String getFeedAlertId() {
    return feedAlertId;
  }
  public String getCriticalSafety() {
    return criticalSafety;
  }

  public String getId() {
    return id;
  }

  public String getOfferId() {
    return offerId;
  }

  public String getBody() {
    return body;
  }

  public String getTitle() {
    return title;
  }

  public String getNotificationId() {
    return notificationId;
  }

  public String getBuildingId() {
    return buildingId;
  }

  public String getAlertType() {
    return alertType;
  }

  public String getType() {
    return type;
  }

  public String getPc_emergency_alert() {
    return pc_emergency_alert;
  }

  public String getUuid() {
    return uuid;
  }

  public String getPc_urgent_alert() {
    return pc_urgent_alert;
  }

  public String getPersonal_safety() {
    return personal_safety;
  }

  public String getIncrement_badge() {
    return increment_badge;
  }

  public String getPing_alert() {
    return ping_alert;
  }

  public String getWeather_alert() {
    return weather_alert;
  }

  public String getLightning_alert() {
    return lightning_alert;
  }

  public String getLife_safety() {
    return life_safety;
  }

  public String getSocialType() {
    return socialType;
  }

  public String getAction() {
    return action;
  }

}
