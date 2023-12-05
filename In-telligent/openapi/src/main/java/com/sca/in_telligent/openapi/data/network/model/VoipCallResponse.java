package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VoipCallResponse implements Serializable {

  public boolean isAccepted() {
    return accepted;
  }

  @SerializedName("accepted")
  private boolean accepted;

}
