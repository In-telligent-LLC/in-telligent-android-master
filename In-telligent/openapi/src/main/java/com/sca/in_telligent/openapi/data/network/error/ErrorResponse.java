package com.sca.in_telligent.openapi.data.network.error;

import java.util.ArrayList;


public class ErrorResponse {

  /**
   * This Error class to be used in the future.
   */

  public ArrayList<ErrorObject> errorObjects;

  public ArrayList<ErrorObject> getErrorObjects() {
    return errorObjects;
  }

  public void setErrorObjects(
      ArrayList<ErrorObject> errorObjects) {
    this.errorObjects = errorObjects;
  }

  public class ErrorObject {

    public String code;
    public String title;
    public String detail;
    public String statusCode;
    public Source source;

    public Meta meta;

    public String getCode() {
      return code;
    }

    public void setCode(String code) {
      this.code = code;
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getDetail() {
      return detail;
    }

    public void setDetail(String detail) {
      this.detail = detail;
    }

    public Source getSource() {
      return source;
    }

    public void setSource(Source source) {
      this.source = source;
    }


    public String getStatusCode() {
      return statusCode;
    }

    public Meta getMeta() {
      return meta;
    }

    public class Source {

      public String pointer;
    }

    public class Meta
    {

      public String getUserId() {
        return userId;
      }

      public String userId;
    }
  }

}
