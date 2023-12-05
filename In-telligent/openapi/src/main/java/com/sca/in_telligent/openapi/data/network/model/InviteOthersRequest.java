package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class InviteOthersRequest implements Serializable {

  public void setInvitees(ArrayList<Invitee> invitees) {
    this.invitees = invitees;
  }

  @SerializedName("recipients")
  private ArrayList<Invitee> invitees;

}
