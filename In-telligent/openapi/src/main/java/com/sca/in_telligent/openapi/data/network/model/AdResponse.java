package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class AdResponse {
    @SerializedName("data")
    Data data;

    public BannerAd getBannerAd() {
        Data data = this.data;
        if (data == null) {
            return null;
        }
        return data.bannerAd;
    }

    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
    public static class Data {
        @SerializedName("bannerAd")
        private BannerAd bannerAd;

        public BannerAd getBannerAd() {
            return this.bannerAd;
        }
    }

    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
    public static class BannerAd {
        public static final String BASE_IMAGE_URL = "https://app.in-telligent.com/img/banner_ads/";
        @SerializedName("id")
        private int adId;
        @SerializedName("image")
        private String image;
        @SerializedName("url")
        private String url;

        public int getAdId() {
            return this.adId;
        }

        public String getUrl() {
            return this.url;
        }

        public String getImage() {
            return BASE_IMAGE_URL.concat(this.image);
        }
    }
}
