package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NotificationSubscriber implements Serializable {

  @SerializedName("opened")
  private String opened;

  public String getOpened() {
    return opened;
  }
}
