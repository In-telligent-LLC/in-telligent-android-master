package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class State implements Serializable {
    @SerializedName("countryId")
    private int countryId;
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("shortName")
    private String shortName;

    public int getId() {
        return this.id;
    }

    public int getCountryId() {
        return this.countryId;
    }

    public String getName() {
        return this.name;
    }

    public String getShortName() {
        return this.shortName;
    }
}
