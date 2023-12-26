package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class User implements Serializable {
    @SerializedName("Buildings")
    ArrayList<BuildingIdItem> buildingIds;
    @SerializedName("canSendLSA")
    private boolean canSendLSA;
    @SerializedName("id")
    private int id;

    public int getId() {
        return this.id;
    }

    public boolean isCanSendLSA() {
        return this.canSendLSA;
    }

    public ArrayList<BuildingIdItem> getBuildingIds() {
        return this.buildingIds;
    }
}
