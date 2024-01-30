package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

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
