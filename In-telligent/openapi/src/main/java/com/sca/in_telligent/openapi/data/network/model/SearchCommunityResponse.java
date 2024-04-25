package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

public class SearchCommunityResponse implements Serializable {
    @SerializedName("buildings")
    ArrayList<Building> buildings;

    public ArrayList<Building> getBuildings() {
        return this.buildings;
    }

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }

    public void setSuccess(boolean b) {
    }
}
