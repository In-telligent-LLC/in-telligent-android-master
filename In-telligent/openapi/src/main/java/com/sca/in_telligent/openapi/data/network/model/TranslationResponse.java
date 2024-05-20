package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TranslationResponse implements Serializable {

  @SerializedName("title")
  private String title;

  @SerializedName("body")
  private String body;

  public String getTitle() {
    return title;
  }

  public String getBody() {
    return body;
  }
}
