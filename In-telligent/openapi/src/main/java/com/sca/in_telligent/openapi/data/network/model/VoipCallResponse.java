package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class VoipCallResponse implements Serializable {
    @SerializedName("accepted")
    private boolean accepted;

    public boolean isAccepted() {
        return this.accepted;
    }
}
