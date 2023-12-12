package com.sca.in_telligent.openapi.data.network.model;

import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.NativeProtocol;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

@Deprecated
/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class UpdateSubscriptionRequest implements Serializable {
    @SerializedName(NativeProtocol.WEB_DIALOG_ACTION)
    private String action;
    @SerializedName(AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_AUTOMATIC)
    private boolean automatic;
    @SerializedName("buildingId")
    private String buildingId;
    @SerializedName("inviteId")
    private String inviteId;

    public void setAction(String str) {
        this.action = str;
    }

    public void setBuildingId(String str) {
        this.buildingId = str;
    }

    public void setAutomatic(boolean z) {
        this.automatic = z;
    }

    public void setInviteId(String str) {
        this.inviteId = str;
    }

    public String getBuildingId() {
        return this.buildingId;
    }
}
