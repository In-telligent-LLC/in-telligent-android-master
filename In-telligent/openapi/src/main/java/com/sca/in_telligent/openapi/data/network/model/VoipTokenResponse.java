package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class VoipTokenResponse implements Serializable {
  @SerializedName("token")
  private String token;

  public String getToken() {
    return this.token;
  }
}
