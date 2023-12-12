package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class AlertSubscriptionRequest implements Serializable {
    @SerializedName("buildingId")
    private String buildingId;
    @SerializedName("subscription")
    private String subscription;

    public void setBuildingId(String str) {
        this.buildingId = str;
    }

    public void setSubscription(String str) {
        this.subscription = str;
    }

    public String getBuildingId() {
        return this.buildingId;
    }

    public String getSubscription() {
        return this.subscription;
    }
}
