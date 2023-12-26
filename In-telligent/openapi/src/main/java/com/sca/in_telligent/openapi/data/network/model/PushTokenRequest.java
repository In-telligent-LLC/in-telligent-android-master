package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class PushTokenRequest implements Serializable {
    @SerializedName("environment")
    private String environment;
    @SerializedName("pushToken")
    private String pushToken;

    public void setPushToken(String str) {
        this.pushToken = str;
    }

    public void setEnvironment(String str) {
        this.environment = str;
    }
}
