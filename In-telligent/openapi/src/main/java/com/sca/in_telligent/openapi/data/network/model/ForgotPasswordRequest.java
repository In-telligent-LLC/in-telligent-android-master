package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class ForgotPasswordRequest implements Serializable {
    @SerializedName("email")
    private String email;

    public void setEmail(String str) {
        this.email = str;
    }
}
