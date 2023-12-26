package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class InviteeResponse implements Serializable {
    @SerializedName("invites")
    private ArrayList<Invitee> invites;
    @SerializedName("success")
    private boolean success;

    public boolean isSuccess() {
        return this.success;
    }

    public ArrayList<Invitee> getInvites() {
        return this.invites;
    }
}
