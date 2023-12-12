package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class CheckEmailResponse implements Serializable {
    @SerializedName("error")
    String error;
    @SerializedName("valid")
    boolean valid;

    public boolean isValid() {
        return this.valid;
    }

    public String getError() {
        return this.error;
    }
}
