package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import com.sca.in_telligent.openapi.util.CommonUtils;
import java.io.Serializable;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class SignUpRequest implements Serializable {
    @SerializedName("adId")
    private String adId;
    @SerializedName("deviceId")
    private String deviceId;
    @SerializedName("email")
    private String email;
    @SerializedName("name")
    private String name;
    @SerializedName("password")
    private String password;
    @SerializedName("password2")
    private String password2;
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

    public void setPassword2(String str) {
        this.password2 = str;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setAdId(String str) {
        this.adId = str;
    }
}
