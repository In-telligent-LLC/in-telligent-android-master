package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class VoipCallRequest implements Serializable {
    @SerializedName("buildingId")
    private String buildingId;
    @SerializedName("conferenceId")
    private String conferenceId;
    @SerializedName("senderId")
    private int senderId;

    public void setConferenceId(String str) {
        this.conferenceId = str;
    }

    public void setSenderId(int i) {
        this.senderId = i;
    }

    public void setBuildingId(String str) {
        this.buildingId = str;
    }
}
