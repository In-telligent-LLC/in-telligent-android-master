package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class UpdateSubscriberRequest implements Serializable {
    @SerializedName("subscriber")
    private SubscriberRequest subscriberRequest;

    public void setSubscriberRequest(SubscriberRequest subscriberRequest) {
        this.subscriberRequest = subscriberRequest;
    }
}
