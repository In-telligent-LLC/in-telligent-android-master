package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Deprecated
/***
 * Use {@link SubscribeToCommunityRequest} instead
 */
public class UpdateSubscriptionRequest implements Serializable {

  @SerializedName("action")
  private String action;

  @SerializedName("buildingId")
  private String buildingId;

  @SerializedName("automatic")
  private boolean automatic;

  @SerializedName("inviteId")
  private String inviteId;

  public void setAction(String action) {
    this.action = action;
  }

  public void setBuildingId(String buildingId) {
    this.buildingId = buildingId;
  }

  public void setAutomatic(boolean automatic) {
    this.automatic = automatic;
  }

  public void setInviteId(String inviteId) {
    this.inviteId = inviteId;
  }

  public String getBuildingId() {
    return buildingId;
  }

}
