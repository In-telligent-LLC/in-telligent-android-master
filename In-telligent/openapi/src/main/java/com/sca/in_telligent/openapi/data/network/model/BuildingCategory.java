package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class BuildingCategory implements Serializable {

  @SerializedName("name")
  private String name;

  @SerializedName("BuildingCategoryImages")
  private ArrayList<BuildingCategoryImage> buildingCategoryImages;

  public String getName() {
    return name;
  }

  public ArrayList<BuildingCategoryImage> getBuildingCategoryImages() {
    return buildingCategoryImages;
  }
}
