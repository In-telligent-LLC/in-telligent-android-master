package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PushTokenSuccessResponse implements Serializable {

  @JsonAdapter(BooleanTypeAdapter.class)
  @SerializedName("success")
  private boolean success;

  public boolean isSuccess() {
    return success;
  }
}
