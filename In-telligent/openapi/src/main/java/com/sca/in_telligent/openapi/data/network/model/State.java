package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class State implements Serializable {
    public State(
            int countryId,
            int id,
            String name,
            String shortName
    ) {
        this.countryId = countryId;
        this.id = id;
        this.name = name;
        this.shortName = shortName;
    }

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
