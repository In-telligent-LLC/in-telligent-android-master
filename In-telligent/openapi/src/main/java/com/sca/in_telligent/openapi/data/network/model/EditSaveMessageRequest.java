package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EditSaveMessageRequest implements Serializable {


  @SerializedName("action")
  private String action;

  @SerializedName("notificationId")
  private String notificationId;

  public void setAction(String action) {
    this.action = action;
  }
  public String getAction() {
    return action;
  }

  public void setNotificationId(String notificationId) {
    this.notificationId = notificationId;
  }
}
