package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class NotificationLanguageResponse implements Serializable {
    @SerializedName("languages")
    ArrayList<NotificationLanguage> languages;
    @SerializedName("success")
    boolean success;

    public boolean isSuccess() {
        return this.success;
    }

    public ArrayList<NotificationLanguage> getLanguages() {
        return this.languages;
    }
}
