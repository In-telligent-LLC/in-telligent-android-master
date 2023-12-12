package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class TranslationResponse implements Serializable {
    @SerializedName("body")
    private String body;
    @SerializedName("title")
    private String title;

    public String getTitle() {
        return this.title;
    }

    public String getBody() {
        return this.body;
    }
}
