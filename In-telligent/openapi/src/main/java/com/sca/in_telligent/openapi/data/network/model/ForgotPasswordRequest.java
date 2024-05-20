package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ForgotPasswordRequest implements Serializable {

  @SerializedName("email")
  private String email;

  public void setEmail(String email) {
    this.email = email;
  }
}
