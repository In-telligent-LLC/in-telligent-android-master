package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BuildingIdItem implements Serializable {

  @SerializedName("id")
  private int id;

  public int getId() {
    return id;
  }

}
