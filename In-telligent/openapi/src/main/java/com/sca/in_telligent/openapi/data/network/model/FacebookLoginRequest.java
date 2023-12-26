package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import com.sca.in_telligent.openapi.util.CommonUtils;
import java.io.Serializable;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class FacebookLoginRequest implements Serializable {
    @SerializedName("adId")
    private String adId;
    @SerializedName("deviceId")
    private String deviceId;
    @SerializedName("facebookAccessToken")
    private String facebookAccessToken;
    @SerializedName("deviceModel")
    private String deviceModel = CommonUtils.getDeviceInfo();
    @SerializedName("osVersion")
    private String osVersion = CommonUtils.getDeviceOs();

    public void setFacebookAccessToken(String str) {
        this.facebookAccessToken = str;
    }

    public void setAdId(String str) {
        this.adId = str;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }
}
