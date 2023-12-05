package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Locale;

public class NotificationLanguage implements Serializable {

    @SerializedName("name")
    private String name;

    @SerializedName("language")
    private String language;

    public NotificationLanguage() {
    }

    private NotificationLanguage(String language, String displayName) {
        this.language = language;
        this.name = displayName;
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }


    public static NotificationLanguage getFromDeviceLanguage(Locale locale) {
        return new NotificationLanguage(locale.getLanguage(), locale.getDisplayName());
    }

}
