package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class BuildingMember implements Serializable {
    @SerializedName("Subscriber.email")
    private String subscriberEmail;
    @SerializedName("Subscriber.id")
    private String subscriberId;

    public String getSubscriberId() {
        return this.subscriberId;
    }

    public String getSubscriberEmail() {
        return this.subscriberEmail;
    }
}
