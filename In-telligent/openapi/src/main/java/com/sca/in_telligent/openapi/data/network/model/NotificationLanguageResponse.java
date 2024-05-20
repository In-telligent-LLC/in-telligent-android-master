package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class NotificationLanguageResponse implements Serializable {

    @SerializedName("success")
    boolean success;

    @SerializedName("languages")
    ArrayList<NotificationLanguage> languages;

    public boolean isSuccess() {
        return success;
    }

    public ArrayList<NotificationLanguage> getLanguages() {
        return languages;
    }
}
