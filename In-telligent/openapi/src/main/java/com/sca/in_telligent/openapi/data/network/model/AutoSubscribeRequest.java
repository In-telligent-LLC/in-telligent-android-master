package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class AutoSubscribeRequest {
    @SerializedName("buildingId")
    private final int communityId;
    @SerializedName("value")
    private boolean optOut;

    public boolean isOptOut() {
        return this.optOut;
    }

    public void setOptOut(boolean z) {
        this.optOut = z;
    }

    public AutoSubscribeRequest(int i, boolean z) {
        this.communityId = i;
        this.optOut = z;
    }
}
