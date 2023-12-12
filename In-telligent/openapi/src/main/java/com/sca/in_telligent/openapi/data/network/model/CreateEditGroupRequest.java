package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class CreateEditGroupRequest implements Serializable {
    @SerializedName("description")
    private String description;
    @SerializedName("name")
    private String name;

    public void setName(String str) {
        this.name = str;
    }

    public void setDescription(String str) {
        this.description = str;
    }
}
