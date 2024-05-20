package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Country implements Serializable {

  @SerializedName("id")
  private int id;

  @SerializedName("name")
  private String name;

  @SerializedName("shortName")
  private String shortName;

}
