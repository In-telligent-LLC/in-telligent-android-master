package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class SubscriberOptOut implements Serializable {
    @SerializedName("buildingId")
    private int buildingId;
    @SerializedName("id")
    private int id;
    @SerializedName("subscriberId")
    private int subscriberId;

    public int getId() {
        return this.id;
    }

    public int getBuildingId() {
        return this.buildingId;
    }

    public int getSubscriberId() {
        return this.subscriberId;
    }
}
