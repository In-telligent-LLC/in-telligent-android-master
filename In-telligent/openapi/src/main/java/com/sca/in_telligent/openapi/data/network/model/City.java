package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class City implements Serializable {

  @SerializedName("id")
  private int id;

  @SerializedName("stateId")
  private int stateId;

  @SerializedName("name")
  private String name;

}
