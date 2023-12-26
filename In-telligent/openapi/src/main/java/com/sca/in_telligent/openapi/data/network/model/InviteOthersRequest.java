package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class InviteOthersRequest implements Serializable {
    @SerializedName("recipients")
    private ArrayList<Invitee> invitees;

    public void setInvitees(ArrayList<Invitee> arrayList) {
        this.invitees = arrayList;
    }
}
