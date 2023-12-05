package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CreateEditGroupRequest implements Serializable {

  public void setName(String name) {
    this.name = name;
  }

  @SerializedName("name")
  private String name;

  public void setDescription(String description) {
    this.description = description;
  }

  @SerializedName("description")
  private String description;

}
