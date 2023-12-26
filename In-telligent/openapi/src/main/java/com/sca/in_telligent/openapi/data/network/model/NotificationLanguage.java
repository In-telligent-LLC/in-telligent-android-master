package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Locale;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class NotificationLanguage implements Serializable {
    @SerializedName("language")
    private String language;
    @SerializedName("name")
    private String name;

    public NotificationLanguage() {
    }

    private NotificationLanguage(String str, String str2) {
        this.language = str;
        this.name = str2;
    }

    public String getName() {
        return this.name;
    }

    public String getLanguage() {
        return this.language;
    }

    public static NotificationLanguage getFromDeviceLanguage(Locale locale) {
        return new NotificationLanguage(locale.getLanguage(), locale.getDisplayName());
    }
}
