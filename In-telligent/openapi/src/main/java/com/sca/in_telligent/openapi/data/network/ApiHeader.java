package com.sca.in_telligent.openapi.data.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class ApiHeader {
    private PublicApiHeader mPublicApiHeader;

    public ApiHeader(PublicApiHeader publicApiHeader) {
        this.mPublicApiHeader = publicApiHeader;
    }

    public PublicApiHeader getPublicApiHeader() {
        return this.mPublicApiHeader;
    }

    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
    public static final class PublicApiHeader {
        @SerializedName("api_key")
        @Expose
        private String mApiKey;

        public PublicApiHeader(String str) {
            this.mApiKey = str;
        }

        public String getApiKey() {
            return this.mApiKey;
        }

        public void setApiKey(String str) {
            this.mApiKey = str;
        }
    }
}
