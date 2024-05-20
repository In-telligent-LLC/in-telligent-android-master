package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Marcos Ambrosi on 1/24/19.
 */
public class EditCommunityInviteeRequest {

    public EditCommunityInviteeRequest(int inviteeId, String name, String email, String phone) {
        this.inviteeId = inviteeId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    @SerializedName("inviteeId")
    private int inviteeId;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("phone")
    private String phone;

    public int getInviteeId() {
        return inviteeId;
    }

    public void setInviteeId(int inviteeId) {
        this.inviteeId = inviteeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
