package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class VoipCallRequest implements Serializable {
    @SerializedName("buildingId")
    private String buildingId;


    public void setBuildingId(String str) {
        this.buildingId = str;
    }
}
