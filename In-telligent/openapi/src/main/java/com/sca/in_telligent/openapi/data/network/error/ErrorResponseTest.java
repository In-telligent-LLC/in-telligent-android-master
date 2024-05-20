package com.sca.in_telligent.openapi.data.network.error;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ErrorResponseTest {

  @SerializedName("Errors")
  public ArrayList<ErrorObjectTest> arrayList = new ArrayList<>();

  public class ErrorObjectTest {

    public String context;
    public String description;
    public String field;
    public String location;
    public String type;
  }
}
