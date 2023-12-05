package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class InviteeResponse implements Serializable {

  @SerializedName("success")
  private boolean success;

  @SerializedName("invites")
  private ArrayList<Invitee> invites;

  public boolean isSuccess() {
    return success;
  }

  public ArrayList<Invitee> getInvites() {
    return invites;
  }

}
