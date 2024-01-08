package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class LoginResponse implements Serializable {
    @SerializedName("error")
    private String error;
    @SerializedName("success")
    private boolean success;
    @SerializedName("token")
    private String token;

    public boolean isSuccess() {
        return this.success;
    }

    public String getToken() {
        return this.token;
    }

    public String getError() {
        return this.error;
    }

    public void setToken(String mockToken) {
    }
}
