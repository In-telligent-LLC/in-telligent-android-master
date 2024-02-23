package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class PushTokenSuccessResponse implements Serializable {
    @SerializedName("success")
    @JsonAdapter(BooleanTypeAdapter.class)
    private boolean success;

    public boolean isSuccess() {
        return this.success;
    }
}
