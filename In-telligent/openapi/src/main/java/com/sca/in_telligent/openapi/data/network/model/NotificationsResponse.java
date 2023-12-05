package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class NotificationsResponse implements Serializable {


  @SerializedName("Notifications")
  private ArrayList<Notification> notifications;


  public ArrayList<Notification> getNotifications() {
    return notifications;
  }
}
