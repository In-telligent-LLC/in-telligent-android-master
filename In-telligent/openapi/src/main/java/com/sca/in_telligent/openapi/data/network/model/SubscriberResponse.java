package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class SubscriberResponse implements Serializable {
    @SerializedName("Subscriber")
    private Subscriber subscriber;
    @SerializedName("success")
    @JsonAdapter(BooleanTypeAdapter.class)
    private boolean success;

    public boolean isSuccess() {
        return this.success;
    }

    public Subscriber getSubscriber() {
        return this.subscriber;
    }
}
