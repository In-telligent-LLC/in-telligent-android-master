package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Marcos Ambrosi on 1/21/19.
 */
public class CommunityResponse {

    @SerializedName("Building")
    private Building building;

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}
