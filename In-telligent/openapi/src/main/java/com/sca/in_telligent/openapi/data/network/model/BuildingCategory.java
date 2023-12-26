package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class BuildingCategory implements Serializable {
    @SerializedName("BuildingCategoryImages")
    private ArrayList<BuildingCategoryImage> buildingCategoryImages;
    @SerializedName("name")
    private String name;

    public String getName() {
        return this.name;
    }

    public ArrayList<BuildingCategoryImage> getBuildingCategoryImages() {
        return this.buildingCategoryImages;
    }
}
