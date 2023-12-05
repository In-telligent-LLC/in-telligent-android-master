package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CheckEmailResponse implements Serializable {

  @SerializedName("valid")
  boolean valid;

  @SerializedName("error")
  String error;

  public boolean isValid() {
    return valid;
  }

  public String getError() {
    return error;
  }

}
