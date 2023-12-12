package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class EditCommunityInviteeRequest {
    @SerializedName("email")
    private String email;
    @SerializedName("inviteeId")
    private int inviteeId;
    @SerializedName("name")
    private String name;
    @SerializedName("phone")
    private String phone;

    public EditCommunityInviteeRequest(int i, String str, String str2, String str3) {
        this.inviteeId = i;
        this.name = str;
        this.email = str2;
        this.phone = str3;
    }

    public int getInviteeId() {
        return this.inviteeId;
    }

    public void setInviteeId(int i) {
        this.inviteeId = i;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String str) {
        this.phone = str;
    }
}
