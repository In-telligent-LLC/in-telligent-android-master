package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

public class CommunityResponse {
    @SerializedName("Building")
    private Building building;

    public Building getBuilding() {
        return this.building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}
