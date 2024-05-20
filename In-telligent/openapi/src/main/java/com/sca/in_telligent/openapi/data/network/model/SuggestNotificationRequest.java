package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SuggestNotificationRequest implements Serializable {

  @SerializedName("building_id")
  private String buildingId;

  @SerializedName("title")
  private String title;

  @SerializedName("description")
  private String description;

  public void setBuildingId(String buildingId) {
    this.buildingId = buildingId;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
  }


}
