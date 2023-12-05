package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class CreateNotificationRequest implements Serializable {

  @SerializedName("buildingId")
  private String buildingId;

  @SerializedName("title")
  private String title;

  @SerializedName("body")
  private String body;

  @SerializedName("type")
  private String type;

  @SerializedName("subscribers")
  private ArrayList<String> subscribers;

  @SerializedName("send_to_email")
  private String send_to_email;

  public void setBuildingId(String buildingId) {
    this.buildingId = buildingId;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setSubscribers(ArrayList<String> subscribers) {
    this.subscribers = subscribers;
  }

  public void setSend_to_email(String send_to_email) {
    this.send_to_email = send_to_email;
  }


}
