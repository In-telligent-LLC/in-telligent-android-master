package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class LoginResponse implements Serializable {
    @SerializedName("error")
    private String error;
    @SerializedName("success")
    private boolean success;
    @SerializedName("token")
    private String token;

    public boolean isSuccess() {
        return this.success;
    }

    public String getToken() {
        return this.token;
    }

    public String getError() {
        return this.error;
    }
}
