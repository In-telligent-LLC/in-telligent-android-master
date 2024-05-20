package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class NotificationResponse implements Serializable {

  @SerializedName("Notifications")
  private ArrayList<Notification> notifications;

  @SerializedName("hasMoreData")
  private boolean hasMoreData;

  public ArrayList<Notification> getNotifications() {
    return notifications;
  }

  public boolean isHasMoreData() {
    return hasMoreData;
  }

}
