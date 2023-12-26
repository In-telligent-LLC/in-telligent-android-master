package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class SearchCommunityResponse implements Serializable {
    @SerializedName("Buildings")
    ArrayList<Building> buildings;

    public ArrayList<Building> getBuildings() {
        return this.buildings;
    }
}
