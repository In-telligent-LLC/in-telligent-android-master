package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class City implements Serializable {
    public City(
            int id,
            String name,
            int stateId
    ) {
        this.id = id;
        this.name = name;
        this.stateId = stateId;
    }
    @SerializedName("id")
    private final int id;
    @SerializedName("name")
    private final String name;
    @SerializedName("stateId")
    private final int stateId;
}
