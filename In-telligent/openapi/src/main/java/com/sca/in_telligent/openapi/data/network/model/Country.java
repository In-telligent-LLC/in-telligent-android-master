package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Country implements Serializable {
    public Country(
            int id,
            String name,
            String shortName
    ) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
    }

    @SerializedName("id")
    private final int id;
    @SerializedName("name")
    private final String name;
    @SerializedName("shortName")
    private final String shortName;
}
