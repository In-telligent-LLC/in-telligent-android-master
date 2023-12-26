package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class SubscriberRequest implements Serializable {
    @SerializedName("email")
    private String email;
    @SerializedName("language")
    private String language;
    @SerializedName("lightningAlertEnabled")
    private boolean lightningAlertEnabled;
    @SerializedName("name")
    private String name;
    @SerializedName("weatherAlertEnabled")
    private boolean weatherAlertEnabled;

    public void setName(String str) {
        this.name = str;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public void setLanguage(String str) {
        this.language = str;
    }

    public void setLightningAlertEnabled(boolean z) {
        this.lightningAlertEnabled = z;
    }

    public void setWeatherAlertEnabled(boolean z) {
        this.weatherAlertEnabled = z;
    }
}
