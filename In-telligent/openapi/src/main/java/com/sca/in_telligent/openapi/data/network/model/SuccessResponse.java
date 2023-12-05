package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class SuccessResponse implements Serializable {

  public boolean isSuccess() {
    return success;
  }

  @JsonAdapter(BooleanTypeAdapter.class)
  @SerializedName("success")
  private boolean success;

  public ErrorObject getErrors() {
    return errors;
  }

  @SerializedName("errors")
  private ErrorObject errors;

  public class ErrorObject{

    public ArrayList<String> getName() {
      return name;
    }

    public ArrayList<String> getOther() {
      return other;
    }

    @SerializedName("name")
    private ArrayList<String> name;

    @SerializedName("other")
    private ArrayList<String> other;
  }
}
