package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import com.sca.in_telligent.openapi.util.CommonUtils;
import java.io.Serializable;

public class LoginRequest implements Serializable {
    @SerializedName("adId")
    private String adId;
    @SerializedName("deviceId")
    private String deviceId;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("deviceModel")
    private final String deviceModel = CommonUtils.getDeviceInfo();
    @SerializedName("osVersion")
    private final String osVersion = CommonUtils.getDeviceOs();

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public void setPassword(String str) {
        this.password = str;
    }

    public void setAdId(String str) {
        this.adId = str;
    }
}
