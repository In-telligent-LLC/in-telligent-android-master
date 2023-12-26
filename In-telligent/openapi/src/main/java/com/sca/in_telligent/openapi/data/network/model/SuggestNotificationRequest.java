package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class SuggestNotificationRequest implements Serializable {
    @SerializedName("building_id")
    private String buildingId;
    @SerializedName("description")
    private String description;
    @SerializedName("title")
    private String title;

    public void setBuildingId(String str) {
        this.buildingId = str;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public void setDescription(String str) {
        this.description = str;
    }
}
