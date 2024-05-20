package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginResponse implements Serializable {

  @SerializedName("success")
  private boolean success;

  @SerializedName("token")
  private String token;

  @SerializedName("error")
  private String error;

  public boolean isSuccess() {
    return success;
  }

  public String getToken() {
    return token;
  }

  public String getError() {
    return error;
  }

}
