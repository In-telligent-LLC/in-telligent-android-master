package com.sca.in_telligent.openapi.data.network.model;

import com.facebook.internal.AnalyticsEvents;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class Invitee implements Serializable {
    @SerializedName("buildingId")
    private Integer buildingId;
    @SerializedName("email")
    private String email;
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("phone")
    private String phone;
    @SerializedName(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_STATUS)
    private String status;
    @SerializedName("subscriberId")
    private Integer subscriberId;

    public Integer getSubscriberId() {
        return this.subscriberId;
    }

    public void setSubscriberId(Integer num) {
        this.subscriberId = num;
    }

    public Integer getBuildingId() {
        return this.buildingId;
    }

    public void setBuildingId(Integer num) {
        this.buildingId = num;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String str) {
        this.phone = str;
    }
}
