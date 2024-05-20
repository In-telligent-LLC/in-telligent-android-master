package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Invitee implements Serializable {

  @SerializedName("id")
  private int id;

  @SerializedName("name")
  private String name;

  @SerializedName("status")
  private String status;

  @SerializedName("subscriberId")
  private Integer subscriberId;

  @SerializedName("buildingId")
  private Integer buildingId;

  @SerializedName("email")
  private String email;

  @SerializedName("phone")
  private String phone;

  public Integer getSubscriberId() {
    return subscriberId;
  }

  public void setSubscriberId(Integer subscriberId) {
    this.subscriberId = subscriberId;
  }

  public Integer getBuildingId() {
    return buildingId;
  }

  public void setBuildingId(Integer buildingId) {
    this.buildingId = buildingId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

}
