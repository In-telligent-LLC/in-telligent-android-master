package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class CreateNotificationRequest implements Serializable {
    @SerializedName("body")
    private String body;
    @SerializedName("buildingId")
    private String buildingId;
    @SerializedName("send_to_email")
    private String send_to_email;
    @SerializedName("subscribers")
    private ArrayList<String> subscribers;
    @SerializedName("title")
    private String title;
    @SerializedName("type")
    private String type;

    public void setBuildingId(String str) {
        this.buildingId = str;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public void setBody(String str) {
        this.body = str;
    }

    public void setType(String str) {
        this.type = str;
    }

    public void setSubscribers(ArrayList<String> arrayList) {
        this.subscribers = arrayList;
    }

    public void setSend_to_email(String str) {
        this.send_to_email = str;
    }
}
