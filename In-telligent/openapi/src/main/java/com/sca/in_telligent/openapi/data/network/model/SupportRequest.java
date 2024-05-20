package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SupportRequest implements Serializable {

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("message")
    private String message;
}
