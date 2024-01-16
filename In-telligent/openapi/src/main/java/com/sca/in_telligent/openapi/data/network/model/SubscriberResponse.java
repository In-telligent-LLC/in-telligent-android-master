package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class SubscriberResponse implements Serializable {
    @SerializedName("Subscriber")
    private Subscriber subscriber;
    @SerializedName("success")
    @JsonAdapter(BooleanTypeAdapter.class)
    private boolean success;


    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Subscriber getSubscriber() {
        return this.subscriber;
    }

    public Subscriber setSubscriber(Subscriber mockSubscriber) {
        return mockSubscriber;
    }
}
