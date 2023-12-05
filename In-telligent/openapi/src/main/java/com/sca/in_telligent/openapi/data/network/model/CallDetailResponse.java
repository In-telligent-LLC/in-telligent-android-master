package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CallDetailResponse implements Serializable {

  public boolean isCompleted() {
    return completed;
  }

  @JsonAdapter(BooleanTypeAdapter.class)
  @SerializedName("completed")
  private boolean completed;

}
