package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BuildingCategoryImage implements Serializable {

  @SerializedName("image")
  private String image;

  public String getImage() {
    return image;
  }

}
